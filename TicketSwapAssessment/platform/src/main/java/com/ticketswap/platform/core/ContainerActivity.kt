package com.ticketswap.platform.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ticketswap.platform.R
import com.ticketswap.platform.databinding.ActivityLayoutBinding
import com.ticketswap.platform.extensions.inTransaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class ContainerActivity : AppCompatActivity() {
    lateinit var uiBinding: ActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = DataBindingUtil.setContentView(this, R.layout.activity_layout)
        setSupportActionBar(uiBinding.toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(
                uiBinding.fragmentContainer.id,
                fragment()
            )
        }

    private fun replaceFragment(savedInstanceState: Bundle?, fragment: BaseFragment) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            replace(
                uiBinding.fragmentContainer.id,
                fragment
            )
        }

    abstract fun fragment(): BaseFragment
}
