package com.example.oh_sheet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import java.security.MessageDigest


class SignInActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        database = Firebase.database.reference

        emailEditText = findViewById(R.id.emailEditTextIn)
        passwordEditText = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validate user credentials
            validateCredentials(email, password)
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private data class User(val email: String? = null, val password: String? = null)


    private fun validateCredentials(email: String, password: String) {
        val usersQuery = database.child("users").orderByChild("email").equalTo(email)

        usersQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (user != null && user.password == password) {
                            // Valid credentials, start the main activity
                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            return
                        }
                    }
                }

                // Invalid credentials, show an error message
                Toast.makeText(this@SignInActivity, "Invalid email or password", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                Toast.makeText(
                    this@SignInActivity,
                    "Failed to validate credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
