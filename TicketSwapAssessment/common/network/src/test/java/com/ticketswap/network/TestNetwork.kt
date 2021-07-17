package com.ticketswap.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ticketswap.network.util.WeatherResponse
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


class TestNetwork {
    private lateinit var webServer: MockWebServer

    @Before
    fun setUp() {
        // Mocking the server
        webServer = MockWebServer()
        webServer.start()
    }

    @After
    fun cleanUp() {
        webServer.shutdown()
    }

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

    @Test
    fun testUnauthorizedFail() {
        val serverUrl = "http://localhost/"
        val response = MockResponse()
            .setBody("{\"code\":403}")
            .setResponseCode(403)

        webServer.url(serverUrl)

        webServer.enqueue(response)
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val request = Request.Builder()
            .url(webServer.url("/"))
            .get()
            .build()

        val adapter = moshi.adapter(WeatherResponse::class.java)

        request.enqueue(responseAdapter = adapter)
            .test()
            .assertError {
                it is UnsuccessfulRequest
            }
    }
}
