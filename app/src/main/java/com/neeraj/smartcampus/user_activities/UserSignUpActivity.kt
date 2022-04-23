package com.neeraj.smartcampus.user_activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.neeraj.smartcampus.R
import kotlinx.android.synthetic.main.activity_user_sign_up.*
import java.util.concurrent.TimeUnit

class UserSignUpActivity : AppCompatActivity() {

    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_up)

        var compare = "@mnnit.ac.in"
        var j = compare.length-1
        var check = false
        var checkMailSent = false
        auth = FirebaseAuth.getInstance()

        emailText.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var n = s!!.length - 1
                compare = "@mnnit.ac.in"
                j = compare.length-1

                check = false

                helperTextForEmail.text = "Please enter the valid e-mail address"

                while (n >=0 && j >= 0){

                    if(s[n] != compare[j]){
                        break
                    }

                    n--; j--;
                }
                if(j == -1 && compare.length < s.length){
                    check = true;
                    helperTextForEmail.text = "Valid Email entered!"
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })




        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
//                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
                Toast.makeText(this@UserSignUpActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
                getOtpProgressBar.visibility = View.GONE
                getOtpButton.visibility = View.VISIBLE
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later

                if(checkMailSent) {
                    getOtpProgressBar.visibility = View.GONE
                    getOtpButton.visibility = View.VISIBLE

                    val intent = Intent(this@UserSignUpActivity, UserVerifyOTP::class.java)
                    intent.putExtra(UserVerifyOTP.EXTRA_PHONE, phoneNumberField!!.text.toString())
                    intent.putExtra(UserVerifyOTP.EXTRA_VERIFICATIONID, verificationId)
                    startActivity(intent)
                    finish()
                }

            }
        }

        getOtpButton.setOnClickListener {
            if(phoneNumberField!!.text.toString().trim().isEmpty() || phoneNumberField!!.length() < 10 || phoneNumberField!!.length() > 10){
                Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_SHORT).show()
            }
            else if(emailPassword.toString().length < 6){
                Toast.makeText(this, "Password too small! (Minimum 6 characters)", Toast.LENGTH_SHORT).show()
            }
            else if(check === false || emailText.toString().length <= compare.length){
                Toast.makeText(this, "Please enter a valid E-Mail Id (MNNIT G-Suite)", Toast.LENGTH_SHORT).show()
            }
            else{
//                val intent =  Intent(this, VerifyOtp::class.java)
//                intent.putExtra(VerifyOtp.EXTRA_PHONE, phoneNumberField!!.text.toString())
//                startActivity(intent)
                getOtpProgressBar.visibility = View.VISIBLE
                getOtpButton.visibility = View.INVISIBLE

                // Phone number verify through OTP

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber("+91" + phoneNumberField!!.text.toString())       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)

                // New user created with E Mail and password, also Email verification through Link
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                auth.createUserWithEmailAndPassword(
                    emailText.text.toString(),
                    emailPassword.text.toString()
                ).addOnCompleteListener(this) { task->
                    if(task.isSuccessful){

                        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task->

                            if(task.isSuccessful){
                                checkMailSent = true
                                Toast.makeText(this, "Verification link has been sent on your E Mail", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(this, "Sign Up failed! Please try again.", Toast.LENGTH_SHORT).show()
                            }

                        }

//                        Toast.makeText(this, "User created successfully!", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

        loginTextField.setOnClickListener {
            startActivity(Intent(this, UserLoginActivity::class.java))
            finish()
        }


    }
}