package com.dmssystem.dms.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.dmssystem.dms.R

class Popup {

    private lateinit var dialogBuilder: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    lateinit var view: View


    fun createPopup(context: Context, layout: Int) {

        dialogBuilder = AlertDialog.Builder(context, R.style.Theme_App_VerifyPopup)
        view = LayoutInflater.from(context).inflate(layout, null)
        dialogBuilder.setView(view)
        dialog = dialogBuilder.create()
        dialog.show()
    }
}