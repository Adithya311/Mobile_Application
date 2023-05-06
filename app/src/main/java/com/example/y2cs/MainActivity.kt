package com.example.y2cs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.System.err

class MainActivity : AppCompatActivity() {


    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var heading: EditText
    private lateinit var desc: EditText
    private lateinit var expprice: EditText
    private lateinit var btnSubmit:Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name= findViewById(R.id.name)

//        val name: TextInputLayout = findViewById(R.id.name)
//        val editTextName = name.editText // Access the EditText inside the TextInputLayout
////        val editTextName = findViewById<EditText>(R.id.name)

        phone = findViewById<View>(R.id.phone) as EditText
        heading= findViewById(R.id.heading)
        desc= findViewById(R.id.desc)
        expprice= findViewById(R.id.expprice)
        btnSubmit = findViewById(R.id.btnSubmit)
        dbRef = FirebaseDatabase.getInstance().getReference("Inquiries")

        btnSubmit.setOnClickListener {
            saveDetails()
        }


    }

    private fun saveDetails(){
        //get values
        val Ename=name.text.toString()
        val Ephone=phone.text.toString()
        val Eheading=heading.text.toString()
        val Edesc=desc.text.toString()
        val Eexpprice=expprice.text.toString()

        if (Ename.isEmpty()){
            name.error="please enter name"
        }
        if (Ephone.isEmpty()){
            phone.error="please enter phone number"
        }
        if (Eheading.isEmpty()){
            heading.error="please enter heading"
        }
        if (Edesc.isEmpty()){
            desc.error="please enter description"
        }
        if (Eexpprice.isEmpty()){
            expprice.error="please enter expected price"
        }

        val InquId = dbRef.push().key!!
        val inquiryy = InquiriyModel(InquId,Ename, Ephone, Eheading, Edesc, Eexpprice)
        dbRef.child(InquId).setValue(inquiryy)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted success", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}