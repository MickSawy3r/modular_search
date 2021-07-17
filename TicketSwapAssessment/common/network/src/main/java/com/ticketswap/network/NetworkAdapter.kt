package com.ticketswap.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

inline fun <reified T> createListAdapter(): JsonAdapter<List<T>>? {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    val listMyData = Types.newParameterizedType(List::class.java, T::class.java)
    return moshi.adapter<List<T>>(listMyData)
}

inline fun <reified T> createObjectAdapter(): JsonAdapter<T> {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    return moshi.adapter(T::class.java)
}
