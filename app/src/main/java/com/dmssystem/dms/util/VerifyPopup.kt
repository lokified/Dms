package com.dmssystem.dms.util

import android.app.AlertDialog
import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.dmssystem.dms.R

class VerifyPopup {

    private lateinit var dialogBuilder: AlertDialog.Builder
     lateinit var dialog: AlertDialog

    val start = 30_000L
    var timer = start
    lateinit var timeCountdown: CountDownTimer

    fun createVerifyPopup(context: Context?): AlertDialog {

        dialogBuilder = AlertDialog.Builder(context, R.style.Theme_App_VerifyPopup)
        val view = LayoutInflater.from(context).inflate(R.layout.account_verify_layout, null)

        val cancelButton: ImageView = view.findViewById(R.id.popup_cancel)
        val verifyText: TextView = view.findViewById(R.id.resend_verify_txt)

        dialogBuilder.setView(view)
        dialog = dialogBuilder.create()


        timeCountdown = object: CountDownTimer(timer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer = millisUntilFinished
                setTextTimer()
            }

            override fun onFinish() {}
        }

        verifyText.text = "Relax we will automatically verify the code in ${setTextTimer()}"

        cancelButton.setOnClickListener {

            dialog.dismiss()
        }
        dialog.show()

        return dialog
    }


    //timer format
    private fun setTextTimer(): String {

        val m = (timer / 1000) / 60
        val s = (timer / 1000) % 60

        val format = String.format("%02d:%02d", m, s)

        return format
    }
}