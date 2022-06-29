package com.dmssystem.dms.util

import android.view.WindowManager
import androidx.fragment.app.Fragment

fun Fragment.setStatusBarColor(color: Int) {

    requireActivity().window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
    }
}

fun Fragment.darkStatusBar() {
    requireActivity().window.apply {
        darkStatusBar()
    }
}

fun Fragment.lightStatusBar() {
    requireActivity().window.apply {
        lightStatusBar()
    }

}