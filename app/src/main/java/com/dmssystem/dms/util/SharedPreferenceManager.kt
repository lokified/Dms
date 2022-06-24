package com.dmssystem.dms.util

import android.content.Context

object SharedPreferenceManager {

    fun setInitialPin(context: Context?, pin: String) {

        val sharedPreferences = context?.getSharedPreferences("PinSharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        editor?.putString("initial_pin", pin)
        editor?.apply()

    }
}