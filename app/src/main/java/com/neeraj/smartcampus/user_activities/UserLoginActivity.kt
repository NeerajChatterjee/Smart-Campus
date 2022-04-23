package com.neeraj.smartcampus.user_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neeraj.smartcampus.R
import kotlinx.android.synthetic.main.activity_user_login.*

class UserLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        getStartedSignUp.setOnClickListener {
            startActivity(Intent(this, UserSignUpActivity::class.java))
            finish()
        }
    }
}