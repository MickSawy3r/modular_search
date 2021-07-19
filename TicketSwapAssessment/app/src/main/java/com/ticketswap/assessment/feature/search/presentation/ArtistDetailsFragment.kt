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
import com.ticketswap.assessment.databinding.FragmentArtistDetailsBinding
import com.ticketswap.assessment.feature.search.domain.datamodel.ArtistDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.extention.*
import com.ticketswap.platform.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtistDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_SEARCH_ITEM = "SEARCH_ITEM"
        private const val TAG = "DetailsFragment"

        fun forItem(item: SpotifyDataModel) = ArtistDetailsFragment().apply {
            arguments = bundleOf(PARAM_SEARCH_ITEM to item)
        }
    }

    @Inject
    lateinit var navigator: Navigator

    private lateinit var artistDetailsViewModel: ArtistDetailsViewModel
    private lateinit var uiBinding: FragmentArtistDetailsBinding
    private lateinit var detailsItem: SpotifyDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistDetailsViewModel = ViewModelProvider(this).get(ArtistDetailsViewModel::class.java)

        with(artistDetailsViewModel) {
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
        uiBinding = FragmentArtistDetailsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        setupUI()
        setupListeners()

        return uiBinding.root
    }

    private fun setupUI() {
        detailsItem = arguments?.get(PARAM_SEARCH_ITEM) as SpotifyDataModel
        artistDetailsViewModel.loadDetails(detailsItem.id)
    }

    private fun setupListeners() {
        artistDetailsViewModel.details.observe(viewLifecycleOwner, {
            renderDetails(it)
        })
        artistDetailsViewModel.failure.observe(viewLifecycleOwner, {
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

    private fun renderDetails(item: ArtistDetailsDataModel?) {
        Log.d(TAG, "renderDetails: ")
        item?.let {
            uiBinding.tvName.text = item.name
            uiBinding.tvGenre.text = item.genres.toString()
            uiBinding.tvFollowers.text = item.followers.toString()
            uiBinding.ivHeaderImage.loadFromUrl(item.image)
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                notifyWithAction(R.string.failure_network_connection, R.string.retry) {
                    artistDetailsViewModel.loadDetails(detailsItem.id)
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
