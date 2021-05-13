package com.example.tutorme

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ChoiceActivity : AppCompatActivity() {
    lateinit var nextButton: Button
    lateinit var studentButton: Button
    lateinit var tutorButton: Button
    lateinit var description: EditText


    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val postJsonAdapter = moshi.adapter(Tutor::class.java)
    private val post2JsonAdapter = moshi.adapter(Student::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        var user: String? = null
        studentButton = findViewById(R.id.studentButton)
        studentButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", "Student")
            studentButton.setTextColor(Color.parseColor("#278CDD"))

            tutorButton.setTextColor(Color.parseColor("#FF000000"))
            user = "student"

        }

        tutorButton = findViewById(R.id.tutorButton)
        tutorButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", "Tutor")
            tutorButton.setTextColor(Color.parseColor("#278CDD"))

            studentButton.setTextColor(Color.parseColor("#FF000000"))
            user = "tutor"

        }
        val id = intent.extras?.getInt("id")
        nextButton = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO) {
                    user?.let { it1 ->
                        if (id != null) {
                                postData(id, it1)
                        }
                    }
                }
            }
        }


        description = findViewById(R.id.description)


    }

    private fun postData(id: Int, user: String) {
        if (user == "tutor") {
            Log.d("user", user)
            val post = Tutor(0, description.text.toString(), mutableListOf(), listOf())
            val requestPost =
                Request.Builder().url("https://my-helper-appdev.herokuapp.com/api/user/$id/tutor/")
                    .post(postJsonAdapter.toJson(post).toRequestBody()).build()

            client.newCall(requestPost).execute().use {
                if (!it.isSuccessful) throw IOException("Unexpected code $it")

                val intent = Intent(this@ChoiceActivity, SubjectsActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        }
        if (user == "student"){
        val post = Student(description.text.toString())
        val requestPost =
            Request.Builder().url("https://my-helper-appdev.herokuapp.com/api/user/$id/student/")
                .post(post2JsonAdapter.toJson(post).toRequestBody()).build()

        client.newCall(requestPost).execute().use {
            if (!it.isSuccessful) throw IOException("Unexpected code $it")

            val intent = Intent(this@ChoiceActivity, SubjectsActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        }

    }
}
