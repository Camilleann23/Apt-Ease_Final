package com.android.aptease

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import android.view.View
import com.android.aptease.R
import com.google.android.material.card.MaterialCardView

class TenantDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_dashboard)

        // Hide the action bar
        supportActionBar?.hide()

        setupBottomNavigation()
        setupClickListeners()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Already on home
                    true
                }
                R.id.navigation_chat -> {
                    // Navigate to chat
                    // startActivity(Intent(this, ChatActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    // Navigate to account/profile
                    startActivity(Intent(this, AccountActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
        // Set home as selected
        bottomNav.selectedItemId = R.id.navigation_home
    }

    private fun setupClickListeners() {
        // Setup maintenance request card click
        findViewById<MaterialCardView>(R.id.maintenanceCard).setOnClickListener {
            // Navigate to maintenance request screen
            // startActivity(Intent(this, MaintenanceRequestActivity::class.java))
        }

        // Setup rent payment card click
        findViewById<MaterialCardView>(R.id.rentPaymentCard).setOnClickListener {
            // Navigate to rent payment screen
            // startActivity(Intent(this, RentPaymentActivity::class.java))
        }

        // Setup notification click
        findViewById<View>(R.id.notificationIcon).setOnClickListener {
            // Navigate to notifications
            // startActivity(Intent(this, NotificationsActivity::class.java))
        }

        // Setup profile image click
        findViewById<View>(R.id.profileImage).setOnClickListener {
            // Navigate to profile
            startActivity(Intent(this, AccountActivity::class.java))
        }
    }
}