package com.ticketswap.assessment.feature.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ticketswap.assessment.R
import com.ticketswap.assessment.core.navigation.Navigator
import com.ticketswap.assessment.databinding.FragmentSearchBinding
import com.ticketswap.assessment.feature.search.domain.failures.SessionExpiredFailure
import com.ticketswap.platform.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var searchAdapter: SearchAdapter

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var uiBinding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        uiBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_search,
            container,
            false
        )

        setupUI()
        setupListeners()

        searchViewModel.start()

        return uiBinding.root
    }

    private fun setupUI() {
        uiBinding.recycler.adapter = searchAdapter
    }

    private fun setupListeners() {
        searchViewModel.searchLiveData.observe(viewLifecycleOwner, {
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

        searchAdapter.clickListener = { item, navigationExtras ->
            navigator.showSearchItemDetails(requireActivity(), item, navigationExtras)
        }

        uiBinding.buttonSearch.setOnClickListener {
            searchViewModel.search(uiBinding.searchEditText.text.toString())
        }
    }

    companion object {
        private const val TAG = "SearchFragment"
    }
}
