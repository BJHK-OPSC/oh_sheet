package com.example.oh_sheet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.app.Activity
import android.content.SharedPreferences
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    private lateinit var registerUsernameEditText: TextInputEditText
    private lateinit var registerPasswordEditText: EditText
    private lateinit var registerConfirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        registerUsernameEditText = findViewById(R.id.usernameEditText)
        registerPasswordEditText = findViewById(R.id.editTextTextPassword)
        registerConfirmPasswordEditText = findViewById(R.id.editTextConfirmPassword)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = registerUsernameEditText.text.toString()
            val password = registerPasswordEditText.text.toString()
            val confirmPassword = registerConfirmPasswordEditText.text.toString()

            when (isValidRegistration(username, password, confirmPassword)) {
                RegistrationValidationResult.SUCCESS -> {
                    // Pass the registered credentials back to MainActivity
                    val intent = Intent()
                    intent.putExtra("username", username)
                    intent.putExtra("password", password)
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                    val signInIntent = Intent(this, SignInActivity::class.java)
                    startActivity(signInIntent)
                }
                RegistrationValidationResult.PASSWORDS_DONT_MATCH -> {
                    registerConfirmPasswordEditText.error = "Passwords do not match"
                }
                RegistrationValidationResult.MISSING_FIELDS -> {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun isValidRegistration(username: String, password: String, confirmPassword: String): RegistrationValidationResult {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return RegistrationValidationResult.MISSING_FIELDS
        }

        if (password != confirmPassword) {
            return RegistrationValidationResult.PASSWORDS_DONT_MATCH
        }

        return RegistrationValidationResult.SUCCESS
    }

    enum class RegistrationValidationResult {
        SUCCESS,
        PASSWORDS_DONT_MATCH,
        MISSING_FIELDS
    }

}
