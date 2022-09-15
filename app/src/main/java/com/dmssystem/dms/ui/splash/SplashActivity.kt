package com.dmssystem.dms.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.ActivitySplashBinding
import com.dmssystem.dms.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        CoroutineScope(Dispatchers.Main).launch {

            delay(1000L)
            setAnimations()
            delay(3000L)
            navigateToMainActivity()
        }

    }


    private fun navigateToMainActivity() {

        val transitionIntent = MainActivity.newIntent(this)

        val logoIcon: ImageView = findViewById(R.id.app_logo)
        val logoPair = Pair.create(logoIcon as View, "tLogoIcon")

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, logoPair)

        ActivityCompat.startActivity(this, transitionIntent, options.toBundle())
        finish()
    }


    private fun setAnimations() {

        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_logo_anim)
        val logoNameAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_logo_name_anim)

        binding.apply {

            appLogo .visibility = View.VISIBLE
            logoName.visibility = View.VISIBLE

            appLogo.startAnimation(logoAnimation)
            logoName.startAnimation(logoNameAnimation)
        }
    }
}