package com.ticketswap.assessment.feature.search

import android.content.Context
import android.content.Intent
import com.ticketswap.assessment.core.platform.BaseActivity
import com.ticketswap.assessment.core.platform.BaseFragment

class SearchActivity : BaseActivity() {
    override fun fragment(): BaseFragment = SearchFragment()

    companion object {
        fun callingIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }
}
