package com.ticketswap.assessment.feature.auth.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.ticketswap.assessment.core.platform.PrefStore
import com.ticketswap.assessment.R
import com.ticketswap.assessment.core.navigation.Navigator
import com.ticketswap.assessment.databinding.FragmentLoginBinding
import com.ticketswap.assessment.feature.search.SearchActivity
import com.ticketswap.navigation.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "LoginFragment"

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var prefs: PrefStore

    private lateinit var uiBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        uiBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)
        setupUI()

        return uiBinding.root
    }

    private fun setupUI() {
        uiBinding.buttonLogin.setOnClickListener {
            startSpotifyLoginActivity()
        }
    }

    private fun startSpotifyLoginActivity() {
        val spotifyClientId =
            "84ea753e599142b8bace9b63d153227b" // Feel free to use this spotify app

        val authRequest = AuthorizationRequest.Builder(
            spotifyClientId,
            AuthorizationResponse.Type.TOKEN, Uri.Builder()
                .scheme(getString(R.string.com_spotify_sdk_redirect_scheme))
                .authority(getString(R.string.com_spotify_sdk_redirect_host))
                .build().toString()
        )
            .setShowDialog(true)
            .setScopes(arrayOf("user-read-email"))
            .setCampaign("your-campaign-token")
            .build()

        val intent = AuthorizationClient.createLoginActivityIntent(requireActivity(), authRequest)
        getAuthToken.launch(intent)
    }

    private val getAuthToken =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val response = AuthorizationClient.getResponse(it.resultCode, it.data)

            when (response.type) {
                AuthorizationResponse.Type.ERROR -> {
                    Log.d(TAG, "Error: ${response.error}")
                    Snackbar.make(
                        uiBinding.root,
                        "Error: ${response.error}",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
                AuthorizationResponse.Type.EMPTY -> {
                    Log.d(TAG, "Error: Empty Response")
                }
                else -> {
                    prefs.setAuthToken(response.accessToken)
                    startActivity(SearchActivity.callingIntent(requireContext()))
                }
            }
        }
}
