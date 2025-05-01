package com.android.aptease

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent

class MessageActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var messageAdapter: ArrayAdapter<String>
    private val messageList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        // Hide the action bar
        supportActionBar?.hide()

        setupMessageList()
        setupBottomNavigation()
    }

    private fun setupMessageList() {
        // Initialize ListView
        listView = findViewById(R.id.messageListView)

        // Add sample messages (these would come from your data source in a real app)
        messageList.add("Fritz Dolly Lorejas\nThe Maintenance Guy is working now on..")
        messageList.add("Fritz Dolly Lorejas\nThe Maintenance Guy is working now on..")
        messageList.add("Fritz Dolly Lorejas\nThe Maintenance Guy is working now on..")

        // Create the adapter for the ListView
        messageAdapter = ArrayAdapter(
            this,
            R.layout.message_item,
            R.id.messageText,
            messageList
        )

        listView.adapter = messageAdapter
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Navigate to home
                    startActivity(Intent(this, TenantDashboardActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_chat -> {
                    // Already on messages
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
        // Set chat as selected
        bottomNav.selectedItemId = R.id.navigation_chat
    }
}
