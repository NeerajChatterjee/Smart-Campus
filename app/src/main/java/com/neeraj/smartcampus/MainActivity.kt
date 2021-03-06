package com.neeraj.smartcampus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.neeraj.smartcampus.user_activities.UserDashboardActivity
import com.neeraj.smartcampus.user_activities.UserLoginActivity
import com.neeraj.smartcampus.user_activities.UserSignUpActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.activity_user_login.*

class MainActivity : AppCompatActivity() {
    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
                "Welcome to Smart Campus",
                "Everything at your palm!!",
                R.drawable.campus
            ),
            IntroSlide(
                "Go Without Cash",
                "Use Smart Campus Wallet at Local Canteen",
                R.drawable.cashless
            ),
            IntroSlide(
                "Daily Entries, Register Complaints and Many More",
                "Everything at your palm!!",
                R.drawable.dailyentries
            )
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide()

        introSliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        }
        )
        next_btn.setOnClickListener{
            if(introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                introSliderViewPager.currentItem += 1
            }
            else {
                startActivity(Intent(this, UserSignUpActivity::class.java))
                finish()
            }
        }
        text2.setOnClickListener{

            startActivity(Intent(this, UserLoginActivity::class.java))
            finish()

//            val intent = Intent(this, UserDashboardActivity::class.java)
//            startActivity(intent)
//            finish()
        }
    }

    private fun setupIndicators(){

        if(FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(this, UserDashboardActivity::class.java))
            finish()
            return
        }

        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
        }
    }

    private fun setCurrentIndicator(index: Int){
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer[i] as ImageView
            if(i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            }
            else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}