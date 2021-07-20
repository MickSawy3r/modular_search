package com.ticketswap.assessment

import androidx.appcompat.app.AppCompatActivity
import org.amshove.kluent.shouldBeEqualTo
import org.robolectric.Robolectric
import org.robolectric.Shadows
import kotlin.reflect.KClass

infix fun KClass<out AppCompatActivity>.shouldNavigateTo(nextActivity: KClass<out AppCompatActivity>): () -> Unit = {
    val originActivity = Robolectric.buildActivity(this.java).get()
    val shadowActivity = Shadows.shadowOf(originActivity)
    val nextIntent = shadowActivity.peekNextStartedActivity()

    nextIntent.component?.className shouldBeEqualTo nextActivity.java.canonicalName
}
