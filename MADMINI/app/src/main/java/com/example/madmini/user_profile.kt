package com.example.madmini
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class user_profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)








        val btn1: Button = findViewById<Button>(R.id.button2)
        btn1.setOnClickListener {
            val intent = Intent(this@user_profile, delete_successfull::class.java)
            startActivity(intent)
            finish()
        }
    }
}
