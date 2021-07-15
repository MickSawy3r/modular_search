package com.ticketswap.assessment.core.extension

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ticketswap.assessment.core.platform.BaseActivity
import com.ticketswap.assessment.core.platform.BaseFragment

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun BaseFragment.close() = parentFragmentManager.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).uiBinding.fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!
