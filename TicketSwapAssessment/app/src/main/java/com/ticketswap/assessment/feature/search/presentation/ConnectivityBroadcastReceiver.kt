package com.ticketswap.assessment.feature.search.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import javax.inject.Inject

class ConnectivityBroadcastReceiver @Inject constructor() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: ")
    }

    companion object {
        private const val TAG = "ConnectivityBroadcastRe"
    }
}
