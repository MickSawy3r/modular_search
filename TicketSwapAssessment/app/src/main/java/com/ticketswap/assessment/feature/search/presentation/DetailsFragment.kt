package com.ticketswap.assessment.feature.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.FragmentDetailsBinding
import com.ticketswap.platform.core.BaseFragment
import javax.inject.Inject

class DetailsFragment : BaseFragment() {

    @Inject
    lateinit var itemDetailsAnimator: ItemDetailsAnimator

    lateinit var uiBinding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        uiBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_details,
            container,
            false
        )

        activity?.let { itemDetailsAnimator.postponeEnterTransition(it) }
        return uiBinding.root
    }
}
