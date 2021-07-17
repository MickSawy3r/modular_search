package com.ticketswap.assessment.feature.search.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ticketswap.assessment.R
import com.ticketswap.assessment.core.navigation.Navigator
import com.ticketswap.assessment.databinding.FragmentSearchBinding
import com.ticketswap.platform.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var searchAdapter: SearchAdapter

    lateinit var searchViewModel: SearchViewModel

    lateinit var uiBinding: FragmentSearchBinding

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

    private fun setupListeners() {
        searchViewModel.searchLiveData.observe(viewLifecycleOwner, {
            searchAdapter.collection = it.orEmpty()
        })

        searchViewModel.loadingLiveData.observe(viewLifecycleOwner, {
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
        })

        searchAdapter.clickListener = { _, _ ->
            // TODO: Navigate to Details
        }

        uiBinding.buttonSearch.setOnClickListener {
            Log.d(TAG, "setupListeners: ${uiBinding.searchEditText.text}")
            searchViewModel.search(uiBinding.searchEditText.text.toString())
        }
    }

    private fun setupUI() {
        uiBinding.recycler.adapter = searchAdapter
    }

    companion object {
        private const val TAG = "SearchFragment"
    }
}
