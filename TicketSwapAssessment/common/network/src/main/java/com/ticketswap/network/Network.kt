package com.ticketswap.network

import com.squareup.moshi.JsonAdapter
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

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
            throw UnsuccessfulRequest(it.body?.string() ?: it.request.url.toString())
        }
        if (it.body?.source() == null) {
            return@map null
        }
        return@map responseAdapter.fromJson(it.body!!.source())
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
