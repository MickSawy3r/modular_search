package com.ticketswap.assessment.feature.search.presentation

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ticketswap.assessment.R
import com.ticketswap.assessment.core.navigation.Navigator
import com.ticketswap.assessment.databinding.FragmentSearchBinding
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.extention.Failure
import com.ticketswap.extention.failure
import com.ticketswap.extention.loading
import com.ticketswap.extention.observe
import com.ticketswap.platform.core.BaseFragment
import com.ticketswap.platform.core.ConnectivityBroadcastReceiver
import com.ticketswap.platform.core.ConnectivityCallback
import com.ticketswap.platform.extensions.attachConnectivityBroadcastReceiver
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

        uiBinding.searchEditText.setOnEditorActionListener { _, _, _ ->
            searchViewModel.search(uiBinding.searchEditText.text.toString())
            true
        }
    }

    override fun onConnectionChange(connected: Boolean) {
        if (!connected) {
            uiBinding.llNoInternet.visibility = View.VISIBLE
            searchViewModel.setNetworkAvailable(false)
        } else {
            uiBinding.llNoInternet.visibility = View.GONE
            searchViewModel.setNetworkAvailable(true)
        }
    }

    private fun handleLoading(loading: Boolean?) {
        Log.d(TAG, "handleLoading: $loading")
        if (loading == true) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun renderSearchResult(data: List<SpotifyDataModel>?) {
        searchAdapter.collection = data.orEmpty()

        val result = data.orEmpty()
        Log.d(TAG, "renderSearchResult: ${result.size}")
        if (result.isEmpty()) {
            uiBinding.llEmptyList.visibility = View.VISIBLE
            uiBinding.recycler.visibility = View.GONE
        } else {
            uiBinding.llEmptyList.visibility = View.GONE
            uiBinding.recycler.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val TAG = "SearchFragment"
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                notifyWithAction(R.string.failure_network_connection, R.string.retry) {
                    searchViewModel.retry()
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
