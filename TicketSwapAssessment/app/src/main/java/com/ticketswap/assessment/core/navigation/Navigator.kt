package com.ticketswap.assessment.core.navigation

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.ticketswap.assessment.feature.search.presentation.SearchActivity
import com.ticketswap.assessment.feature.auth.presenter.LoginActivity
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import com.ticketswap.assessment.feature.search.presentation.DetailsActivity
import com.ticketswap.authenticator.AuthGuard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(
    private val authenticator: AuthGuard
) {
    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showSearch(context)
            false -> showLogin(context)
        }
    }

    private fun showLogin(context: Context) =
        context.startActivity(LoginActivity.callingIntent(context))

    private fun showSearch(context: Context) =
        context.startActivity(SearchActivity.callingIntent(context))

    fun showSearchItemDetails(
        activity: FragmentActivity,
        searchItem: SearchListItemDataModel,
        navigationExtras: Extras
    ) {
        val intent = DetailsActivity.callingIntent(activity, searchItem)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    class Extras(val transitionSharedElement: View)
}
