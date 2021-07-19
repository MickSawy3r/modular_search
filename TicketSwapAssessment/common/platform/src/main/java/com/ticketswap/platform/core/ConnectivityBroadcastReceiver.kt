package com.ticketswap.platform.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ConnectivityBroadcastReceiver constructor(private val connectivityCallback: ConnectivityCallback) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) {
            return
        }
        if (NetworkHandler(context).isNetworkAvailable()) {
            connectivityCallback.onConnected()
        } else {
            connectivityCallback.onDisconnected()
        }
    }

    companion object {
        private const val TAG = "ConnectivityBroadcastRe"
    }
}
