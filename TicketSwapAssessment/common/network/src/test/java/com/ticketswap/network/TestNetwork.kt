package com.ticketswap.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ticketswap.network.util.WeatherResponse
import okhttp3.Request
import org.junit.Test

class TestNetwork {

    @Test
    fun testEnqueueSuccess() {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val request = Request.Builder()
            .url("https://5eee5c0999b2440016bc060d.mockapi.io/weather")
            .get()
            .build()

        val listMyData = Types.newParameterizedType(List::class.java, WeatherResponse::class.java)
        val adapter = moshi.adapter<List<WeatherResponse>>(listMyData)

        request.enqueue<List<WeatherResponse>>(responseAdapter = adapter).test()
            .assertValue { it.size == 4 }
    }

    @Test
    fun testEnqueueFail() {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val request = Request.Builder()
            .url("https://5eee5c0999b2440016bc060d.mockapi.io")
            .get()
            .build()

        val listMyData = Types.newParameterizedType(List::class.java, WeatherResponse::class.java)
        val adapter = moshi.adapter<List<WeatherResponse>>(listMyData)

        request.enqueue<List<WeatherResponse>>(responseAdapter = adapter).test()
            .assertError {
                println(it.message)
                println(it)
                return@assertError it.message != null
            }
    }
}
