package com.ticketswap.extention

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.squareup.picasso.Picasso

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

fun ImageView.loadFromUrl(url: String) {
    if (url.isEmpty()) {
        return
    }
    return Picasso.get()
        .load(url)
        .error(R.drawable.ic_image_load_error)
        .into(this)
}

fun ImageView.loadCircularFromUrl(url: String) {
    if (url.isEmpty()) {
        return
    }
    return Picasso.get()
        .load(url)
        .transform(CircleTransform())
        .error(R.drawable.ic_image_load_error)
        .into(this)
}
