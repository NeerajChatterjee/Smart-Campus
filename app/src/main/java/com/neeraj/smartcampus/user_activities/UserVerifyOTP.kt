package com.neeraj.smartcampus.user_activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.neeraj.smartcampus.R
import kotlinx.android.synthetic.main.activity_user_verify_otp.*
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class UserVerifyOTP : AppCompatActivity() {

    companion object {
        const val EXTRA_PHONE = "phone_extra"
        const val EXTRA_VERIFICATIONID = "verify_extra"
    }

    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var verificationId: String
    lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_verify_otp)

        var phone = intent.getStringExtra(UserVerifyOTP.EXTRA_PHONE)
        verificationId = intent.getStringExtra(UserVerifyOTP.EXTRA_VERIFICATIONID)!!
        phoneNumberText.text = "+91$phone"
        dialog = ProgressDialog(this)

        // Dialog

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setTitle("Verifying OTP")
        dialog.setMessage("Please Wait")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        // callback
        val auth = FirebaseAuth.getInstance()

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
                Toast.makeText(this@UserVerifyOTP, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
            override fun onCodeSent(
                newVerificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later

                verifyOtpButton.visibility = View.VISIBLE
                verifyOtpProgressBar.visibility = View.GONE

                verificationId = newVerificationId
                Toast.makeText(this@UserVerifyOTP, "New OTP sent", Toast.LENGTH_SHORT).show()

            }
        }

        setUpOtpInputs()

        resendOtpText.setOnClickListener {

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91" + phone!!.toString())       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }

        verifyOtpButton.setOnClickListener {

            if (inputCode1.text.toString().trim().isEmpty() ||
                inputCode2.text.toString().trim().isEmpty() ||
                inputCode3.text.toString().trim().isEmpty() ||
                inputCode4.text.toString().trim().isEmpty() ||
                inputCode5.text.toString().trim().isEmpty() ||
                inputCode6.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(this, "Please enter a valid code", Toast.LENGTH_SHORT).show()
            } else {
                dialog.show()

                val codeEntered =
                    inputCode1.text.toString() + inputCode2.text.toString() + inputCode3.text.toString() +
                            inputCode4.text.toString() + inputCode5.text.toString() + inputCode6.text.toString()

                if (verificationId != null) {
                    verifyOtpProgressBar.visibility = View.VISIBLE
                    verifyOtpButton.visibility = View.INVISIBLE

                    val phoneAuthCredential: PhoneAuthCredential =
                        PhoneAuthProvider.getCredential(verificationId, codeEntered)

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                dialog.dismiss()
                                val user = task.result?.user

                                // New Activity starts
                                Toast.makeText(this, "Success: $user", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, UserLoginActivity::class.java))
                                finish()
                            } else {
                                dialog.dismiss()
                                verifyOtpButton.visibility = View.VISIBLE
                                verifyOtpProgressBar.visibility = View.GONE

                                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }

    private fun setUpOtpInputs() {
        inputCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    inputCode2.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        inputCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    inputCode3.requestFocus()
                } else {
                    inputCode1Layout.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        inputCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    inputCode4.requestFocus()
                } else {
                    inputCode2.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        inputCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    inputCode5.requestFocus()
                } else {
                    inputCode3.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        inputCode5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    inputCode6.requestFocus()
                } else {
                    inputCode4.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        inputCode6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.toString().trim().isEmpty()) {
                    inputCode5.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

}