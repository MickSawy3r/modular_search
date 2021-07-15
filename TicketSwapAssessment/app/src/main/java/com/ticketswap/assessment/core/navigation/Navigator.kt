package com.ticketswap.assessment.core.navigation

import android.content.Context
import android.view.View
import com.ticketswap.assessment.feature.search.SearchActivity
import com.ticketswap.assessment.feature.auth.Authenticator
import com.ticketswap.assessment.feature.auth.LoginActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(
    private val authenticator: Authenticator
) {

    private fun showLogin(context: Context) =
        context.startActivity(LoginActivity.callingIntent(context))

    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showSearch(context)
            false -> showLogin(context)
        }
    }

    private fun showSearch(context: Context) =
        context.startActivity(SearchActivity.callingIntent(context))

    class Extras(val transitionSharedElement: View)
}
