package com.android.aptease

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class DevelopersActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.back_button)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // This will automatically navigate back to AccountSettings
        // since it's the previous activity in the stack
    }
}