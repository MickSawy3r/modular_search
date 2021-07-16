package com.ticketswap.navigation.extensions

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ticketswap.navigation.core.BaseFragment
import com.ticketswap.navigation.core.ContainerActivity

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun BaseFragment.close() = parentFragmentManager.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as ContainerActivity).uiBinding.fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!
