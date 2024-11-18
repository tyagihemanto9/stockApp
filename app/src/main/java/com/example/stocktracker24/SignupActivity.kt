package com.example.stocktracker24
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.signupEmailEditText)
        val passwordEditText: EditText = findViewById(R.id.signupPasswordEditText)
        val signupButton: Button = findViewById(R.id.signupButton)

        // Sign up button click listener
        signupButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                            // Redirect to LoginActivity
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Check if the error is due to an existing user
                            if (task.exception is FirebaseAuthUserCollisionException) {
                                Toast.makeText(this, "This email is already registered. Please log in.", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

