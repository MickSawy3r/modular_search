package com.ticketswap.navigation.core

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ticketswap.navigation.extensions.viewContainer

abstract class BaseFragment : Fragment() {
    open fun onBackPressed() {}

    fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    fun showProgress() = progressStatus(View.VISIBLE)

    fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) { if (this is ContainerActivity) this.uiBinding.progress.visibility = viewStatus }

    fun notify(@StringRes message: Int) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()
}
