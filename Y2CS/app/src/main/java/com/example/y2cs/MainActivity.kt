package com.example.y2cs
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.y2cs.models.Inquiry
import com.google.firebase.firestore.FirebaseFirestore
import android.app.Activity

import android.util.Base64
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var headingEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var expectedPriceEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var imageButton: ImageButton
    private lateinit var selectedImageUri:Uri
    private lateinit var imageBase64:String
    private val firestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*nameEditText = findViewById(R.id.name)
        phoneEditText = findViewById(R.id.phone)
        headingEditText = findViewById(R.id.heading)
        descriptionEditText = findViewById(R.id.desc)
        expectedPriceEditText = findViewById(R.id.expprice)
        submitButton = findViewById(R.id.btnSubmit)
        imageButton = findViewById(R.id.imageButton)

        imageButton.setOnClickListener {
            selectImageFromGallery()
        }

        submitButton.setOnClickListener {
            saveInquiry()
        }*/
    }

    private fun saveInquiry() {
        val name = nameEditText.text.toString()
        val phone = phoneEditText.text.toString()
        val heading = headingEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val expectedPrice = expectedPriceEditText.text.toString()

        if (name.isBlank() || phone.isBlank() || heading.isBlank() || description.isBlank() || expectedPrice.isBlank()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Upload image to Firebase Storage, then save inquiry to Firestore


        val inquiry = Inquiry(name, phone, heading, description, expectedPrice, imageBase64)

        firestore.collection("Inquiries")
            .add(inquiry)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Inquiry submitted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding inquiry: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data!!

            val inputStream = contentResolver.openInputStream(selectedImageUri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(8192)
            var bytesRead: Int

            while (inputStream?.read(buffer).also { bytesRead = it ?: 0 } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }

            val byteArray = byteArrayOutputStream.toByteArray()
            val base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)

            // Save the base64 string to the Inquiry object instead of the image URL
            imageBase64 = base64Image
        }
    }



}
