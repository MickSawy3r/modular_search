package com.ticketswap.assessment.feature.auth

import android.content.Context
import android.content.Intent
import com.ticketswap.assessment.core.platform.BaseActivity
import com.ticketswap.assessment.core.platform.BaseFragment
import com.ticketswap.assessment.feature.auth.fragment.LoginFragment

class LoginActivity : BaseActivity() {
    override fun fragment(): BaseFragment = LoginFragment()

    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
