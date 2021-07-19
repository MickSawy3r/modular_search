package com.ticketswap.assessment.feature.routing.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ticketswap.assessment.core.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RouterActivity : AppCompatActivity() {
    @Inject
    internal lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.showMain(this)
    }
}
