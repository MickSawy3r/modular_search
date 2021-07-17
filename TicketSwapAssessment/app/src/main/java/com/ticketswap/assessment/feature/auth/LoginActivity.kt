package com.ticketswap.assessment.feature.auth

import android.content.Context
import android.content.Intent
import com.ticketswap.platform.core.BaseFragment
import com.ticketswap.platform.core.ContainerActivity

class LoginActivity : ContainerActivity() {
    override fun fragment(): BaseFragment = LoginFragment()

    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
