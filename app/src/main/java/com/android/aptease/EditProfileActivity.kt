package com.android.aptease

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.ImageView

class EditProfileActivity : AppCompatActivity() {
    private lateinit var profileImage: ImageView
    private lateinit var fullNameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var addressInput: TextInputEditText
    private lateinit var cityInput: TextInputEditText
    private lateinit var changePhotoButton: MaterialButton
    private lateinit var saveButton: MaterialButton

    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
                profileImage.setImageURI(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initializeViews()
        setupClickListeners()
        loadUserData()
    }

    private fun initializeViews() {
        profileImage = findViewById(R.id.profile_image)
        fullNameInput = findViewById(R.id.full_name_input)
        emailInput = findViewById(R.id.email_input)
        phoneInput = findViewById(R.id.phone_input)
        addressInput = findViewById(R.id.address_input)
        cityInput = findViewById(R.id.city_input)
        changePhotoButton = findViewById(R.id.change_photo_button)
        saveButton = findViewById(R.id.save_button)

        // Set up back button
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupClickListeners() {
        changePhotoButton.setOnClickListener {
            openImagePicker()
        }

        saveButton.setOnClickListener {
            if (validateForm()) {
                saveProfileChanges()
            }
        }
    }

    private fun loadUserData() {
        // TODO: Load user data from SharedPreferences or your data source
        // This is where you would populate the fields with existing user data
        fullNameInput.setText("John Doe")
        emailInput.setText("john.doe@example.com")
        phoneInput.setText("+1 234 567 8900")
        addressInput.setText("123 Main Street")
        cityInput.setText("New York")
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (fullNameInput.text.toString().trim().isEmpty()) {
            fullNameInput.error = "Full name is required"
            isValid = false
        }

        if (emailInput.text.toString().trim().isEmpty()) {
            emailInput.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.text.toString()).matches()) {
            emailInput.error = "Invalid email format"
            isValid = false
        }

        if (phoneInput.text.toString().trim().isEmpty()) {
            phoneInput.error = "Phone number is required"
            isValid = false
        }

        if (addressInput.text.toString().trim().isEmpty()) {
            addressInput.error = "Address is required"
            isValid = false
        }

        if (cityInput.text.toString().trim().isEmpty()) {
            cityInput.error = "City is required"
            isValid = false
        }

        return isValid
    }

    private fun saveProfileChanges() {
        // TODO: Implement your API call or data storage logic here
        // This is where you would save the changes to your backend or local storage

        // For now, just show a success message
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}