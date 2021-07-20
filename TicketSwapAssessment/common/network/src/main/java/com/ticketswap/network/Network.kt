package com.ticketswap.network

import com.squareup.moshi.JsonAdapter
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.net.HttpURLConnection

/**
 * An extension for the Request object to perform a network call.
 * @throws UnsuccessfulRequest Response Code: 200 > response code > 300
 * @throws UnauthorizedException Response Code: 401, 403
 * @throws BadRequestException Response Code: 500 > response code >= 400
 * @throws ServerException Response Code: >= 500
 *
 * @return Single<T>
 */
fun <T> Request.enqueue(
    interceptors: List<Interceptor> = listOf(),
    responseAdapter: JsonAdapter<T>,
    debug: Boolean = false
): Single<T> {
    val okHttp = httpClient(debug = debug, interceptors = interceptors)

    return Single.fromCallable {
        okHttp.newCall(request = this).execute()
    }.map {
        if (!it.isSuccessful) {
            if (it.code == HttpURLConnection.HTTP_UNAUTHORIZED ||
                it.code == HttpURLConnection.HTTP_FORBIDDEN
            ) {
                throw UnauthorizedException(it.request.url.toString())
            }
            if (it.code in
                HttpURLConnection.HTTP_MULT_CHOICE until HttpURLConnection.HTTP_INTERNAL_ERROR
            ) {
                throw BadRequestException(it.request.url.toString(), it.request.body.toString())
            }
            if (it.code >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
                throw ServerException(it.request.url.toString())
            }
            throw UnsuccessfulRequest(it.request.url.toString(), it.body?.string())
        }
        val body = it.body
        if (body == null) {
            throw EmptyResponseException(it.request.url.toString())
        } else {
            return@map responseAdapter.fromJson(body.source())
        }
    }
}

private fun httpClient(debug: Boolean, interceptors: List<Interceptor> = listOf()): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()

    interceptors.forEach {
        clientBuilder.addInterceptor(it)
    }

    if (debug) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    return clientBuilder.build()
}
