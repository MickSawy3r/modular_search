package com.ticketswap.platform.core

interface ConnectivityCallback {
    fun onConnected()

    fun onDisconnected()
}
