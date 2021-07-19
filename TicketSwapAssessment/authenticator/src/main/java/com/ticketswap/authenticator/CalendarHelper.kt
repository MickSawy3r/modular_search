package com.ticketswap.authenticator

import java.util.Calendar
import kotlin.math.abs

internal object CalendarHelper {
    fun nowInSeconds(): Int {
        val now = Calendar.getInstance().time.time
        return abs(now.toInt() / MILLISECONDS_IN_SECOND)
    }

    private const val MILLISECONDS_IN_SECOND = 1000
}
