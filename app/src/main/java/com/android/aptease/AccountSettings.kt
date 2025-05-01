package com.android.aptease

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class AccountSettings : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var userName: TextView
    private lateinit var userRole: TextView
    private lateinit var displayFullName: TextView
    private lateinit var displayEmail: TextView
    private lateinit var displayPhone: TextView
    private lateinit var displayAddress: TextView
    private lateinit var displayCity: TextView
    private lateinit var editProfileButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        initializeViews()
        setupClickListeners()
        loadUserData()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.back_button)
        userName = findViewById(R.id.user_name)
        userRole = findViewById(R.id.user_role)
        displayFullName = findViewById(R.id.display_full_name)
        displayEmail = findViewById(R.id.display_email)
        displayPhone = findViewById(R.id.display_phone)
        displayAddress = findViewById(R.id.display_address)
        displayCity = findViewById(R.id.display_city)
        editProfileButton = findViewById(R.id.edit_profile_button)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        editProfileButton.setOnClickListener {
            navigateToEditProfile()
        }
    }

    private fun loadUserData() {
        // TODO: Load user data from SharedPreferences or your data source
        // This is where you would populate the fields with existing user data
        userName.text = "John Doe"
        userRole.text = "Tenant"
        displayFullName.text = "John Doe"
        displayEmail.text = "john.doe@example.com"
        displayPhone.text = "+1 234 567 8900"
        displayAddress.text = "123 Main Street"
        displayCity.text = "New York"
    }

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditProfileActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        // Refresh user data when returning from EditProfileActivity
        loadUserData()
    }
}