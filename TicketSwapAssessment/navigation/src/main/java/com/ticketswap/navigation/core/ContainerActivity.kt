package com.ticketswap.navigation.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ticketswap.navigation.R
import com.ticketswap.navigation.databinding.ActivityLayoutBinding
import com.ticketswap.navigation.extensions.inTransaction

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

    abstract fun fragment(): BaseFragment
}
