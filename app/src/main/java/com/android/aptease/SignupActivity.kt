package com.android.aptease

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.ImageView

class SignupActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilFirstName: TextInputLayout
    private lateinit var tilLastName: TextInputLayout
    private lateinit var tilPhone: TextInputLayout
    private lateinit var tilPassword: TextInputLayout

    private lateinit var etEmail: TextInputEditText
    private lateinit var etFirstName: TextInputEditText
    private lateinit var etLastName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    private lateinit var rgUserType: RadioGroup
    private lateinit var rbTenant: MaterialRadioButton
    private lateinit var rbLandlord: MaterialRadioButton

    private lateinit var btnRegister: MaterialButton
    private lateinit var tvLogin: TextView
    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_signup)
        sharedPreferences = getSharedPreferences("AptEasePrefs", MODE_PRIVATE)
        initViews()
        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tilEmail = findViewById(R.id.tilEmail)
        tilFirstName = findViewById(R.id.tilFirstName)
        tilLastName = findViewById(R.id.tilLastName)
        tilPhone = findViewById(R.id.tilPhone)
        tilPassword = findViewById(R.id.tilPassword)

        etEmail = findViewById(R.id.etEmail)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etPhone = findViewById(R.id.etPhone)
        etPassword = findViewById(R.id.etPassword)

        rgUserType = findViewById(R.id.rgUserType)
        rbTenant = findViewById(R.id.rbTenant)
        rbLandlord = findViewById(R.id.rbLandlord)

        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            navigateToLogin()
        }

        btnRegister.setOnClickListener {
            if (validateForm()) {
                performRegistration()
            }
        }

        tvLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        finish()
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Reset errors
        tilEmail.error = null
        tilFirstName.error = null
        tilLastName.error = null
        tilPhone.error = null
        tilPassword.error = null

        // Validate email
        if (etEmail.text.toString().isEmpty()) {
            tilEmail.error = "Email is required"
            isValid = false
        } else if (!isValidEmail(etEmail.text.toString())) {
            tilEmail.error = "Invalid email format"
            isValid = false
        }

        // Validate first name
        if (etFirstName.text.toString().isEmpty()) {
            tilFirstName.error = "First name is required"
            isValid = false
        }

        // Validate last name
        if (etLastName.text.toString().isEmpty()) {
            tilLastName.error = "Last name is required"
            isValid = false
        }

        // Validate phone
        if (etPhone.text.toString().isEmpty()) {
            tilPhone.error = "Phone number is required"
            isValid = false
        }

        // Validate password
        if (etPassword.text.toString().isEmpty()) {
            tilPassword.error = "Password is required"
            isValid = false
        } else if (etPassword.text.toString().length < 6) {
            tilPassword.error = "Password must be at least 6 characters"
            isValid = false
        }

        // Validate user type selection
        if (rgUserType.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun performRegistration() {
        val userType = when (rgUserType.checkedRadioButtonId) {
            R.id.rbTenant -> "TENANT"
            R.id.rbLandlord -> "LANDLORD"
            else -> ""
        }

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val phone = etPhone.text.toString()

        // Store user data in SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("firstName", firstName)
        editor.putString("lastName", lastName)
        editor.putString("phone", phone)
        editor.putString("userType", userType)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()

        // Navigate to tenant dashboard
        val intent = Intent(this, TenantDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}