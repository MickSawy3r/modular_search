package com.ticketswap.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ticketswap.network.util.WeatherResponse
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class TestNetworkClient {
    private lateinit var webServer: MockWebServer

    @Before
    fun setUp() {
        // Mocking the server
        webServer = MockWebServer()
        webServer.start(8800)
    }

    @After
    fun cleanUp() {
        webServer.shutdown()
    }

    @Test
    fun testEnqueueSuccess() {
        val request = NetworkClient("https://5eee5c0999b2440016bc060d.mockapi.io/")

        request.enqueue<List<WeatherResponse>>(
            responseAdapter = createObjectAdapter(),
            url = "weather"
        ).test().assertValue { it.size == 4 }
    }

    @Test
    fun testEnqueueFail() {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val listMyData = Types.newParameterizedType(List::class.java, WeatherResponse::class.java)
        val adapter = moshi.adapter<List<WeatherResponse>>(listMyData)

        val client = NetworkClient("https://5eee5c0999b2440016bc060d.mockapi.io/")
        client.enqueue(url = "", responseAdapter = adapter)
            .test()

        client.enqueue<List<WeatherResponse>>(responseAdapter = adapter, url = "").test()
            .assertError {
                println(it.message)
                println(it)
                return@assertError it.message != null
            }
    }

    @Test
    fun testUnauthorizedFail() {
        val serverUrl = "http://localhost:8800/"
        val response = MockResponse()
            .setBody("{\"code\":403}")
            .setResponseCode(403)

        webServer.enqueue(response)

        val client = NetworkClient(serverUrl)

        client.enqueue(
            url = "",
            responseAdapter = createObjectAdapter<Any>()
        ).test()
            .assertError {
                it is UnsuccessfulRequest
            }
    }
}
