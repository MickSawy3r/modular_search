package com.ticketswap.extention

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.transition.Transition

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun ImageView.loadUrlAndPostponeEnterTransition(url: String, activity: FragmentActivity) {
    Glide.with(context.applicationContext)
        .load(url)
        .into(this)
}


