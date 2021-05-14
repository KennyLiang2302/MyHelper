package com.example.tutorme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


private val client = OkHttpClient()
private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
private val postJsonAdapter = moshi.adapter(Invites::class.java)


class InviteActivity : AppCompatActivity() {
    private lateinit var backButton: ImageButton
    private lateinit var inviteButton: Button
    private lateinit var tutorName: TextView
    private lateinit var netid: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite)


        tutorName = findViewById(R.id.tutorName)
        tutorName.text = intent.extras!!.getString("name")

        netid = findViewById(R.id.netid)
        netid.text = intent.extras!!.getString("netID")


        val userType = intent.extras!!.getString("user")



        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", userType)
            startActivity(intent)
        }

        val receiverId = intent.extras!!.getInt("receiverID")
        val session = intent.extras!!.getString("session_token")
        val subject = intent.extras!!.getString("subject")
        val subjectID = subject?.let { findSubjectID(it) }

        inviteButton = findViewById(R.id.inviteButton)
        inviteButton.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO) {
                    if (session != null) {
                        if (userType != null) {
                            run(receiverId, session, subjectID, userType)
                        }
                    }
                }
            }

        }


    }

    private fun run(receiverID: Int, session: String, subjectID: Int?, userType: String) {
        val post = subjectID?.let { Invites(receiverID, subjectID, session) }
        Log.d("post", post.toString())
        val requestPost =
            Request.Builder().url("https://my-helper-appdev.herokuapp.com/api/invite/")
                .post(postJsonAdapter.toJson(post).toRequestBody()).build()

        client.newCall(requestPost).execute().use {
            if (!it.isSuccessful) throw IOException("Unexpected code $it")

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", userType)
            intent.putExtra("session", session)
            startActivity(intent)



        }
    }


    private fun findSubjectID(subject: String): Int {
        if (subject == "History"){return 1}
        if (subject == "Math"){return 2}
        if (subject == "Computer Science"){return 3}
        if (subject == "Physics"){return 5}
        return 5
    }
}
