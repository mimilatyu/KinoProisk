package com.example.kinoproisk.utils

import android.app.Activity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import java.util.concurrent.Executors
import kotlin.math.hypot
import kotlin.math.roundToInt

object AnimationHelper {
    private const val menuItems = 5
    fun performFragmentCircularRevealAnimation(rootView: View, activity: Activity, position: Int) {
        Executors.newSingleThreadExecutor().execute{
            while(true){
                if(rootView.isAttachedToWindow){
                    activity.runOnUiThread{
                        val itemCenter = rootView.width/(menuItems *2)
                        val step = (itemCenter*2)*(position-1)+itemCenter

                        val x: Int = step
                        val y: Int = rootView.y.roundToInt()+rootView.height

                        val startRadius = 0
                        val endRadius = hypot(rootView.width.toDouble(), rootView.height.toDouble())

                        ViewAnimationUtils.createCircularReveal(rootView, x, y, startRadius.toFloat(), endRadius.toFloat()).apply {
                            duration = 300
                            interpolator = AccelerateDecelerateInterpolator()
                            start()
                        }
                        rootView.visibility = View.VISIBLE
                    }
                    return@execute
                }
            }
        }
    }
}