package com.android.aptease

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class AccountActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        // Hide the action bar
        supportActionBar?.hide()

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AptEasePrefs", MODE_PRIVATE)

        setupBottomNavigation()
        setupLogoutButton()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, TenantDashboardActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_chat -> {
                    // Navigate to chat
                    // startActivity(Intent(this, ChatActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    // Already on profile
                    true
                }
                else -> false
            }
        }
        // Set profile as selected
        bottomNav.selectedItemId = R.id.navigation_profile
    }

    private fun setupLogoutButton() {
        findViewById<MaterialButton>(R.id.btnLogout).setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                performLogout()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun performLogout() {
        // Clear login status
        sharedPreferences.edit().apply {
            putBoolean("isLoggedIn", false)
            apply()
        }

        // Navigate to login screen
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}