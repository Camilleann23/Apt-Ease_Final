package com.android.aptease

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var tvRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Hide the action bar
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("AptEasePrefs", MODE_PRIVATE)

        // Check if user is already logged in
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            navigateToDashboard()
            return
        }

        initViews()
        setupListeners()
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, TenantDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            if (validateForm()) {
                performLogin()
            }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Reset errors
        tilEmail.error = null
        tilPassword.error = null

        // Validate email
        if (etEmail.text.toString().isEmpty()) {
            tilEmail.error = "Email is required"
            isValid = false
        } else if (!isValidEmail(etEmail.text.toString())) {
            tilEmail.error = "Invalid email format"
            isValid = false
        }

        // Validate password
        if (etPassword.text.toString().isEmpty()) {
            tilPassword.error = "Password is required"
            isValid = false
        }

        return isValid
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun performLogin() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        // Get stored credentials
        val storedEmail = sharedPreferences.getString("email", "")
        val storedPassword = sharedPreferences.getString("password", "")

        if (email == storedEmail && password == storedPassword) {
            // Update login status
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

            // Navigate to tenant dashboard
            val intent = Intent(this, TenantDashboardActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }
}