package com.example.stocktracker24

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpVerificationActivity : AppCompatActivity() {

    private lateinit var phoneEditText: EditText
    private lateinit var otpEditText: EditText
    private lateinit var getOtpButton: Button
    private lateinit var verifyOtpButton: Button
    private var verificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)

        phoneEditText = findViewById(R.id.phoneEditText)
        otpEditText = findViewById(R.id.otpEditText)
        getOtpButton = findViewById(R.id.getOtpButton)
        verifyOtpButton = findViewById(R.id.verifyOtpButton)

        getOtpButton.setOnClickListener {
            val phoneNumber = phoneEditText.text.toString().trim()
            if (phoneNumber.isNotEmpty()) {
                sendOtp(phoneNumber)
            } else {
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
            }
        }

        verifyOtpButton.setOnClickListener {
            val otp = otpEditText.text.toString().trim()
            if (otp.isNotEmpty()) {
                verifyOtp(otp)
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendOtp(phoneNumber: String) {
        val formattedNumber = "+91$phoneNumber" // Add +91 prefix for Indian phone numbers
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            formattedNumber, // phone number to verify
            60, // timeout duration
            TimeUnit.SECONDS,
            this, // activity for callback
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(this@OtpVerificationActivity, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                    verificationId = id
                    otpEditText.visibility = View.VISIBLE
                    verifyOtpButton.visibility = View.VISIBLE
                    Toast.makeText(this@OtpVerificationActivity, "OTP sent", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun verifyOtp(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "OTP not correct", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
