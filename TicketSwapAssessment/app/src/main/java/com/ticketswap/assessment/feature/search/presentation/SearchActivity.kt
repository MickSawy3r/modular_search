package com.ticketswap.assessment.feature.search.presentation

import android.content.Context
import android.content.Intent
import com.ticketswap.platform.core.BaseFragment
import com.ticketswap.platform.core.ContainerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ContainerActivity() {
    override fun fragment(): BaseFragment = SearchFragment()

    companion object {
        fun callingIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }
}
