package com.ticketswap.assessment.feature.search

import android.content.Context
import android.content.Intent
import com.ticketswap.navigation.core.BaseFragment
import com.ticketswap.navigation.core.ContainerActivity

class SearchActivity : ContainerActivity() {
    override fun fragment(): BaseFragment = SearchFragment()

    companion object {
        fun callingIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }
}
