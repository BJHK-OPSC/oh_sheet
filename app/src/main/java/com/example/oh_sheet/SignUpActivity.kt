package com.example.oh_sheet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import java.security.MessageDigest

class SignUpActivity : AppCompatActivity() {
    private lateinit var emailEditText : EditText
    private lateinit var passwordEditText: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth
        database = Firebase.database.reference



        val registerButton: Button = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            emailEditText = findViewById(R.id.emailEditTextIn)
            passwordEditText = findViewById(R.id.passwordEditText)
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validate user credentials
            createAccount(email, password)
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @IgnoreExtraProperties
    data class User(val password: String? = null, val email: String? = null){
        // Null default values create a no-argument default constructor, which is needed
        // for deserialization from a DataSnapshot.
    }

    fun writeNewUser(userId: String, email: String, password: String){
        val user = User(email,password)
        database.child("users").child(userId).setValue(user)
        Toast.makeText(
            baseContext,
            "User logged.",
            Toast.LENGTH_SHORT,
        ).show()
    }


    /*public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        } else {
            // Redirect to the sign-in activity
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }*/




    fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val passwordBytes = password.toByteArray()
        val hashedBytes = messageDigest.digest(passwordBytes)
        return bytesToHex(hashedBytes)
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = "0123456789ABCDEF"[v ushr 4]
            hexChars[i * 2 + 1] = "0123456789ABCDEF"[v and 0x0F]
        }
        return String(hexChars)
    }



    private fun isValidRegistration(email: String, password: String): RegistrationValidationResult {
        if (email.isEmpty() || password.isEmpty()) {
            return RegistrationValidationResult.MISSING_FIELDS
        }

        if (password.length < 6) {
            return RegistrationValidationResult.PASSWORD_TOO_SHORT
        }

        // Add more validation checks as needed

        return RegistrationValidationResult.SUCCESS
    }


    enum class RegistrationValidationResult {
        SUCCESS,
        MISSING_FIELDS,
        PASSWORD_TOO_SHORT,
        // Add more validation result options as needed
    }



    private fun createAccount(email: String, password: String) {
        val hashedPassword = hashPassword(password)

        if (isValidRegistration(email, hashedPassword) == RegistrationValidationResult.SUCCESS) {
            // [START create_user_with_email]
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val userId = user?.uid
                        if (userId != null) {
                            writeNewUser(userId, email, hashedPassword)
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            // [END create_user_with_email]
        } else if (isValidRegistration(email, password) == RegistrationValidationResult.MISSING_FIELDS) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

}

