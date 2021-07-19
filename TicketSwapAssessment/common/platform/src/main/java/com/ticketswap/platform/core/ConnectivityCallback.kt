package com.ticketswap.platform.core

interface ConnectivityCallback {
    fun onConnectionChange(connected: Boolean)
}
