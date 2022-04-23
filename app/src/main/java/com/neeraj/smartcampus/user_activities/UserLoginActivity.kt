package com.neeraj.smartcampus.user_activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neeraj.smartcampus.R
import kotlinx.android.synthetic.main.activity_user_login.*

class UserLoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        var compare: String
        var j: Int
        var check = false
        auth = FirebaseAuth.getInstance()

        userLoginEmail.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var n = s!!.length - 1
                compare = "@mnnit.ac.in"
                j = compare.length-1

                check = false

                while (n >=0 && j >= 0){

                    if(s[n] != compare[j]){
                        break
                    }

                    n--; j--;
                }
                if(j == -1 && compare.length < s.length){
                    check = true;
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        userLoginButton.setOnClickListener {

            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

            if(!check){
                Toast.makeText(this, "Please enter a valid E-Mail Id (MNNIT G-Suite)", Toast.LENGTH_SHORT).show()
            }
            else if(userLoginPassword.text!!.isEmpty()){
                Toast.makeText(this, "Please enter the password!", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(userLoginEmail.text.toString(), userLoginPassword.text.toString()).addOnCompleteListener { task->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        if(user!!.isEmailVerified) {
                            Toast.makeText(this, "Login Successful! ${user.displayName} ${user.email}", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this, "Please verify your E-Mail and then try login.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

//        val credential = GoogleAuthProvider.getCredential(userLoginEmail.text.toString(), null)


        forgotPasswordField.setOnClickListener {

            if(!check){
                Toast.makeText(this, "Please enter a valid e-mail to reset your password!", Toast.LENGTH_SHORT).show()
            }
            else{
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                auth.sendPasswordResetEmail(userLoginEmail.text.toString()).addOnCompleteListener(this) { task ->

                    if(task.isSuccessful){
                        userLoginEmail.setText("")
                        userLoginPassword.setText("")
                        val user = auth.currentUser
                        Toast.makeText(this, "Reset password link sent!", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "Please try again!", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        getStartedSignUp.setOnClickListener {

            startActivity(Intent(this, UserSignUpActivity::class.java))
            finish()

        }

    }
}