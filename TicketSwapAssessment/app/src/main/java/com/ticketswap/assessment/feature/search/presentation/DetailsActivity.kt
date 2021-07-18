package com.ticketswap.assessment.feature.search.presentation

import com.ticketswap.platform.core.BaseFragment
import com.ticketswap.platform.core.ContainerActivity

class DetailsActivity : ContainerActivity() {
    override fun fragment(): BaseFragment = DetailsFragment()
}
