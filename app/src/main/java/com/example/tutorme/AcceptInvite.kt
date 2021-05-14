package com.example.tutorme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.ImageButton
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


private val client = OkHttpClient()
private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
private val postJsonAdapter = moshi.adapter(invitePost::class.java)
class AcceptInvite : AppCompatActivity() {
    private lateinit var acceptButton: Button
    private lateinit var declineButton: Button
    private lateinit var backButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept_invite)

        val inviteID = intent.extras!!.getInt("inviteID")
        val session = intent.extras!!.getString("session")
        acceptButton = findViewById(R.id.acceptButton)
        acceptButton.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO) {
                    if (session != null) {
                        run(inviteID, session)
                    }
                }
            }
        }

        declineButton = findViewById((R.id.declineButton))
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

    private fun run(inviteID: Int, session: String) {

        val post = invitePost(inviteID, session)
        val requestPost =
            Request.Builder().url("https://my-helper-appdev.herokuapp.com/api/invite/$inviteID/")
                .post(postJsonAdapter.toJson(post).toRequestBody()).build()

        client.newCall(requestPost).execute().use {
            if (!it.isSuccessful) throw IOException("Unexpected code $it")


            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val userAdapter = moshi.adapter(Session::class.java)


            val user = userAdapter.fromJson(it.body!!.string())
            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("session", user)
//


    }
}}