package com.numq.template.core.extension

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

fun View.fadeOutHide() {
    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.duration = 500L
    this.animation = fadeOut
    this.animate()
    this.visibility = View.GONE
}

fun View.fadeInShow() {
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.duration = 500L
    this.animation = fadeIn
    this.animate()
    this.visibility = View.VISIBLE
}

fun View.scaleAnimation(
    view: View,
    x: Pair<Float, Float>,
    y: Pair<Float, Float>,
    pivot: Pair<Float, Float>,
    duration: Long
) {
    val anim = ScaleAnimation(
        x.first,
        x.second,
        y.first,
        y.second,
        pivot.first,
        pivot.second
    )
    anim.duration = duration
    anim.fillAfter = false
    view.startAnimation(anim)
}

fun View.fadeInAnimation() {
    val fadeIn: Animation = AlphaAnimation(0f, 1f)
    fadeIn.startOffset = 500
    fadeIn.duration = 500
    animation = fadeIn
    animate()
    visibility = View.VISIBLE
}