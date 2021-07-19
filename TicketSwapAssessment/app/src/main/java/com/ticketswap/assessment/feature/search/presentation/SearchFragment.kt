package com.ticketswap.assessment.feature.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ticketswap.assessment.R
import com.ticketswap.assessment.core.navigation.Navigator
import com.ticketswap.assessment.databinding.FragmentSearchBinding
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.assessment.feature.search.domain.failures.SessionExpiredFailure
import com.ticketswap.extention.exception.Failure
import com.ticketswap.extention.failure
import com.ticketswap.extention.loading
import com.ticketswap.extention.observe
import com.ticketswap.platform.core.BaseFragment
import com.ticketswap.platform.core.ConnectivityBroadcastReceiver
import com.ticketswap.platform.core.ConnectivityCallback
import com.ticketswap.platform.extensions.attachConnectivityBroadcastReceiver
import com.ticketswap.platform.extensions.close
import com.ticketswap.platform.extensions.deAttachConnectivityBroadcastReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment(), ConnectivityCallback {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var searchAdapter: SearchAdapter

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var uiBinding: FragmentSearchBinding
    private lateinit var connectivityBroadcastReceiver: ConnectivityBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        connectivityBroadcastReceiver = ConnectivityBroadcastReceiver(this)

        with(searchViewModel) {
            observe(search, ::renderSearchResult)
            failure(failure, ::handleFailure)
            loading(loading, ::handleLoading)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        uiBinding = FragmentSearchBinding.inflate(
            layoutInflater,
            container,
            false
        )

        setupUI()
        setupListeners()

        searchViewModel.start()

        return uiBinding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.attachConnectivityBroadcastReceiver(connectivityBroadcastReceiver)
    }

    override fun onPause() {
        super.onPause()
        activity?.deAttachConnectivityBroadcastReceiver(connectivityBroadcastReceiver)
    }

    private fun setupUI() {
        uiBinding.recycler.adapter = searchAdapter

        searchAdapter.clickListener = { item, navigationExtras ->
            navigator.showSearchItemDetails(requireActivity(), item, navigationExtras)
        }

        uiBinding.buttonSearch.setOnClickListener {
            searchViewModel.search(uiBinding.searchEditText.text.toString())
        }
    }

    private fun handleLoading(loading: Boolean?) {
        TODO("Handle Loading")
    }

    private fun renderSearchResult(data: List<SpotifyDataModel>?) {
        searchAdapter.collection = data.orEmpty()
    }

    private fun setupListeners() {
        searchViewModel.search.observe(viewLifecycleOwner, {
            searchAdapter.collection = it.orEmpty()
        })

        searchViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
        })

        searchViewModel.failure.observe(viewLifecycleOwner, {
            if (it is SessionExpiredFailure) {
                navigator.showLogin(requireContext())
            } else {
                notify(it.message ?: "Unknown Error")
            }
        })
    }

    companion object {
        private const val TAG = "SearchFragment"
    }

    override fun onConnected() {
        searchViewModel.setNetworkAvailable(true)
    }

    override fun onDisconnected() {
        searchViewModel.setNetworkAvailable(false)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                notifyWithStringRes(R.string.failure_network_connection); close()
            }
            is Failure.ServerError -> {
                notifyWithStringRes(R.string.failure_server_error); close()
            }
            else -> {
                notifyWithStringRes(R.string.failure_server_error); close()
            }
        }
    }
}
