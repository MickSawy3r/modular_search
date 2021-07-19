package com.ticketswap.platform.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ticketswap.platform.extensions.viewContainer

abstract class BaseFragment : Fragment() {
    open fun onBackPressed() {}

    fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    fun showProgress() = progressStatus(View.VISIBLE)

    fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) { if (this is ContainerActivity) this.uiBinding.progress.visibility = viewStatus }

    fun notify(message: String) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()
}
