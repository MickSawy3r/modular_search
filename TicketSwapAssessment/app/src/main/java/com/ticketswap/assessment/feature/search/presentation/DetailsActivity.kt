package com.ticketswap.assessment.feature.search.presentation

import android.content.Context
import android.content.Intent
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import com.ticketswap.platform.core.BaseFragment
import com.ticketswap.platform.core.ContainerActivity

class DetailsActivity : ContainerActivity() {
    override fun fragment(): BaseFragment = DetailsFragment()

    companion object {
        private const val INTENT_EXTRA_PARAM_MOVIE = "INTENT_PARAM_DETAILS"

        fun callingIntent(context: Context, searchItem: SearchListItemDataModel) =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(INTENT_EXTRA_PARAM_MOVIE, searchItem)
            }
    }
}
