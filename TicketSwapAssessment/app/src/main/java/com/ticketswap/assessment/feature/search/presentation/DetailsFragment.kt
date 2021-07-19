package com.ticketswap.assessment.feature.search.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.ticketswap.assessment.core.navigation.Navigator
import com.ticketswap.assessment.databinding.FragmentDetailsBinding
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.extention.exception.Failure
import com.ticketswap.platform.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_SEARCH_ITEM = "SEARCH_ITEM"
        private const val TAG = "DetailsFragment"

        fun forItem(item: SpotifyDataModel) = DetailsFragment().apply {
            arguments = bundleOf(PARAM_SEARCH_ITEM to item)
        }
    }

    @Inject
    lateinit var itemDetailsAnimator: ItemDetailsAnimator

    @Inject
    lateinit var detailsAdapter: DetailsAdapter

    @Inject
    lateinit var navigator: Navigator

    lateinit var detailsViewMode: DetailsViewModel

    lateinit var uiBinding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsViewMode = ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        uiBinding = FragmentDetailsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        setupUI()
        setupListeners()

        return uiBinding.root
    }

    private fun setupUI() {
        uiBinding.rvImages.adapter = detailsAdapter

        val item = arguments?.get(PARAM_SEARCH_ITEM) as SpotifyDataModel
        detailsViewMode.loadDetails(item.id, item.type)
    }

    private fun setupListeners() {
        detailsViewMode.detailsLiveData.observe(viewLifecycleOwner, {
            renderDetails(it)
        })
        detailsViewMode.failure.observe(viewLifecycleOwner, {
            if (it is Failure.UnauthorizedError) {
                navigator.showLogin(requireContext())
            }
        })
    }

    private fun renderDetails(item: SpotifyDataModel) {
        Log.d(TAG, "renderDetails: ")
        detailsAdapter.collection = item.images
        uiBinding.tvTitle.text = item.name
    }
}
