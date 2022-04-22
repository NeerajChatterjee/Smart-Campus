package com.neeraj.smartcampus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        getSupportActionBar()?.hide()
        val text = findViewById<TextView>(R.id.text)
        val image = findViewById<ImageView>(R.id.image)
        val buildanim = findViewById<LottieAnimationView>(R.id.buildanim)

        image.animate().translationY(-1600F).setDuration(1000).setStartDelay(3000)
        text.animate().translationY(1400F).setDuration(1000).setStartDelay(3000)
        buildanim.animate().translationY(1400F).setDuration(1000).setStartDelay(3000)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        },4000)
    }
}