package com.ticketswap.platform.extensions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ticketswap.platform.core.BaseFragment
import com.ticketswap.platform.core.ContainerActivity

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun BaseFragment.close() = parentFragmentManager.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as ContainerActivity).uiBinding.fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

fun FragmentActivity.attachConnectivityBroadcastReceiver(
    connectivityBroadcastReceiver: BroadcastReceiver
) {
    this.registerReceiver(
        connectivityBroadcastReceiver,
        IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
    )
    this.registerReceiver(
        connectivityBroadcastReceiver,
        IntentFilter("android.net.wifi.WIFI_STATE_CHANGED")
    )
}

fun FragmentActivity.deAttachConnectivityBroadcastReceiver(
    connectivityBroadcastReceiver: BroadcastReceiver
) {
    this.unregisterReceiver(
        connectivityBroadcastReceiver
    )
}
