package com.android.aptease

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class Maintenance_Request : AppCompatActivity() {
    private lateinit var issueDescriptionInput: TextInputEditText
    private lateinit var urgencyLevelGroup: RadioGroup
    private lateinit var image1: ImageView
    private lateinit var image2: ImageView
    private lateinit var addImageButton: Button
    private lateinit var submitButton: Button

    private var selectedImage1: Uri? = null
    private var selectedImage2: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                if (selectedImage1 == null) {
                    selectedImage1 = uri
                    image1.setImageURI(uri)
                } else if (selectedImage2 == null) {
                    selectedImage2 = uri
                    image2.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance_request)

        // Initialize views
        issueDescriptionInput = findViewById(R.id.issueDescriptionInput)
        urgencyLevelGroup = findViewById(R.id.urgencyLevelGroup)
        image1 = findViewById(R.id.image1)
        image2 = findViewById(R.id.image2)
        addImageButton = findViewById(R.id.addImageButton)
        submitButton = findViewById(R.id.submitButton)

        // Setup back button
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }

        // Setup image picker
        addImageButton.setOnClickListener {
            if (selectedImage1 != null && selectedImage2 != null) {
                Toast.makeText(this, "Maximum 2 images allowed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        // Setup submit button
        submitButton.setOnClickListener {
            if (validateForm()) {
                submitMaintenanceRequest()
            }
        }
    }

    private fun validateForm(): Boolean {
        val description = issueDescriptionInput.text.toString().trim()
        val urgencyLevel = when (urgencyLevelGroup.checkedRadioButtonId) {
            R.id.lowUrgency -> "Low"
            R.id.mediumUrgency -> "Medium"
            R.id.highUrgency -> "High"
            else -> null
        }

        if (description.isEmpty()) {
            issueDescriptionInput.error = "Please describe the issue"
            return false
        }

        if (urgencyLevel == null) {
            Toast.makeText(this, "Please select urgency level", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun submitMaintenanceRequest() {
        // TODO: Implement API call to submit maintenance request
        val description = issueDescriptionInput.text.toString().trim()
        val urgencyLevel = when (urgencyLevelGroup.checkedRadioButtonId) {
            R.id.lowUrgency -> "Low"
            R.id.mediumUrgency -> "Medium"
            R.id.highUrgency -> "High"
            else -> "Medium"
        }

        // For now, just show a success message
        Toast.makeText(this, "Maintenance request submitted successfully", Toast.LENGTH_LONG).show()
        finish()
    }
}