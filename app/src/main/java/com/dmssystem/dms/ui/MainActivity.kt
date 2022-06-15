package com.dmssystem.dms.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmssystem.dms.R

class MainActivity : AppCompatActivity() {

    companion object {

        fun newIntent(context: Context): Intent {

            val intent = Intent(context, MainActivity::class.java)

            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}