package com.example.tutorme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AcceptInvite : AppCompatActivity() {
    private lateinit var acceptButton: Button
    private lateinit var declineButton: Button
    private lateinit var backButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept_invite)

        acceptButton = findViewById(R.id.acceptButton)
        acceptButton.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO){run()}
            }
        }

        declineButton= findViewById((R.id.declineButton))
        declineButton.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", "tutor")
            startActivity(intent)
        }

        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", "tutor")
            startActivity(intent)
        }







    }

    private fun run(){

    }


}