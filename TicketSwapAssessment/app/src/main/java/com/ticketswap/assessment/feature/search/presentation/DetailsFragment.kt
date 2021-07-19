package com.ticketswap.assessment.feature.search.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.ticketswap.assessment.R
import com.ticketswap.assessment.core.navigation.Navigator
import com.ticketswap.assessment.databinding.FragmentDetailsBinding
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.extention.Failure
import com.ticketswap.extention.failure
import com.ticketswap.extention.loading
import com.ticketswap.extention.observe
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

    private lateinit var detailsViewMode: DetailsViewModel
    private lateinit var uiBinding: FragmentDetailsBinding
    private lateinit var detailsItem: SpotifyDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsViewMode = ViewModelProvider(this).get(DetailsViewModel::class.java)

        with(detailsViewMode) {
            observe(details, ::renderDetails)
            loading(loading, ::handleLoading)
            failure(failure, ::handleFailure)
        }
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

        detailsItem = arguments?.get(PARAM_SEARCH_ITEM) as SpotifyDataModel
        detailsViewMode.loadDetails(detailsItem.id, detailsItem.type)
    }

    private fun setupListeners() {
        detailsViewMode.details.observe(viewLifecycleOwner, {
            renderDetails(it)
        })
        detailsViewMode.failure.observe(viewLifecycleOwner, {
            if (it is Failure.UnauthorizedError) {
                navigator.showLogin(requireContext())
            }
        })
    }

    private fun handleLoading(loading: Boolean?) {
        if (loading == true) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun renderDetails(item: SpotifyDataModel?) {
        Log.d(TAG, "renderDetails: ")
        item?.let {
            detailsAdapter.collection = item.images
            uiBinding.tvTitle.text = item.name
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                notifyWithAction(R.string.failure_network_connection, R.string.retry) {
                    detailsViewMode.loadDetails(detailsItem.id, detailsItem.type)
                }
            }
            is EmptySearchQueryFailure -> {
                notify(R.string.null_search_query)
            }
            is Failure.UnauthorizedError -> {
                navigator.showLogin(requireContext())
            }
            is Failure.ServerError -> {
                notify(R.string.failure_server_error)
            }
            else -> {
                notify(R.string.failure_server_error)
            }
        }
    }
}
