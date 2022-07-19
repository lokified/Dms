package com.dmssystem.dms.util.dialogs

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
    lateinit var numberText: TextView

    lateinit var timeCountdown: CountDownTimer

    fun createVerifyPopup(context: Context?): AlertDialog {

        dialogBuilder = AlertDialog.Builder(context, R.style.Theme_App_VerifyPopup)
        val view = LayoutInflater.from(context).inflate(R.layout.account_verify_layout, null)

        val cancelButton: ImageView = view.findViewById(R.id.popup_cancel)
        val verifyText: TextView = view.findViewById(R.id.resend_verify_txt)
        numberText = view.findViewById(R.id.number_verify_txt)

        dialogBuilder.setView(view)
        dialog = dialogBuilder.create()


        setCountDownTimer(verifyText)


        cancelButton.setOnClickListener {

            dialog.dismiss()
        }
        dialog.show()

        return dialog
    }


    private fun setCountDownTimer(verifyText: TextView) {

        timeCountdown = object :CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val min: Long = millisUntilFinished / 60000 % 60
                val sec: Long = millisUntilFinished / 1000 % 60

                verifyText.text = "Relax we will automatically verify the code in ${makeTimeString(min, sec)}"
            }

            override fun onFinish() {

                verifyText.text= "Relax we will automatically verify the code in 00:00"
            }
        }
    }


    //timer format
    private fun makeTimeString(minutes: Long, seconds: Long): String  =
        String.format("%02d:%02d",  minutes, seconds)
}