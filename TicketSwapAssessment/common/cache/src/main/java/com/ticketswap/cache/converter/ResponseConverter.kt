package com.ticketswap.cache.converter

import androidx.room.TypeConverter
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

class ResponseConverter {
    @TypeConverter
    fun stringToResponse(value: String?): Response? {
        val responseBody = ResponseBody.create(
            MediaType.get("application/json; charset=utf-8"), value ?: ""
        )
        return Response.Builder()
            .code(200)
            .body(responseBody)
            .build()
    }

    @TypeConverter
    fun responseToString(response: Response): String? {
        return response.body().toString()
    }
}
