package com.example.tutorme

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

private val client = OkHttpClient()
private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
private val postJsonAdapter = moshi.adapter(Subject::class.java)
class SubjectsActivity : AppCompatActivity() {
    lateinit var mathButton: Button
    lateinit var nextButton:Button
    lateinit var physicsButton: Button
    lateinit var CSButton: Button
    lateinit var historyButton: Button
    lateinit var chemistryButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects)

        var subject: String? = null;
        mathButton = findViewById(R.id.mathButton)
        mathButton.setOnClickListener {
            subject = "Math"

            mathButton.setTextColor(Color.parseColor("#278CDD"))


            historyButton.setTextColor(Color.parseColor("#FF000000"))
            physicsButton.setTextColor(Color.parseColor("#FF000000"))
            chemistryButton.setTextColor(Color.parseColor("#FF000000"))
            CSButton.setTextColor(Color.parseColor("#FF000000"))
        }
        physicsButton = findViewById(R.id.physicsButton)
        physicsButton.setOnClickListener {
            subject = "Physics"
            physicsButton.setTextColor(Color.parseColor("#278CDD"))

            historyButton.setTextColor(Color.parseColor("#FF000000"))
            mathButton.setTextColor(Color.parseColor("#FF000000"))
            chemistryButton.setTextColor(Color.parseColor("#FF000000"))
            CSButton.setTextColor(Color.parseColor("#FF000000"))

        }
        CSButton = findViewById(R.id.CSButton)
        CSButton.setOnClickListener {
            subject = "Computer Science"
            CSButton.setTextColor(Color.parseColor("#278CDD"))

            historyButton.setTextColor(Color.parseColor("#FF000000"))
            mathButton.setTextColor(Color.parseColor("#FF000000"))
            physicsButton.setTextColor(Color.parseColor("#FF000000"))
            chemistryButton.setTextColor(Color.parseColor("#FF000000"))

        }

        historyButton = findViewById(R.id.historyButton)
        historyButton.setOnClickListener {
            subject = "History"
            historyButton.setTextColor(Color.parseColor("#278CDD"))


            mathButton.setTextColor(Color.parseColor("#FF000000"))
            physicsButton.setTextColor(Color.parseColor("#FF000000"))
            chemistryButton.setTextColor(Color.parseColor("#FF000000"))
            CSButton.setTextColor(Color.parseColor("#FF000000"))
        }
        chemistryButton = findViewById(R.id.chemistryButton)
        chemistryButton.setOnClickListener {
            subject = "Chemistry"
            historyButton.setTextColor(Color.parseColor("#278CDD"))

            mathButton.setTextColor(Color.parseColor("#FF000000"))
            physicsButton.setTextColor(Color.parseColor("#FF000000"))
            chemistryButton.setTextColor(Color.parseColor("#FF000000"))
            CSButton.setTextColor(Color.parseColor("#FF000000"))

        }

        val user:Int = intent.extras!!.getInt("id")
        nextButton = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO) {
                    subject?.let { it1 ->
                        if (user != null) {
                            postData(it1, user)
                        }
                    }

                }


            }
        }

    }

    fun postData(subject: String, id: Int) {
        val post = Subject(subject)
//        val id = getID(subject)
        val requestPost =
            Request.Builder().url("https://my-helper-appdev.herokuapp.com/api/user/$id/subject/")
                .post(postJsonAdapter.toJson(post).toRequestBody()).build()

        client.newCall(requestPost).execute().use {
            if (!it.isSuccessful) throw IOException("Unexpected code $it")


            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("user", id)
            startActivity(intent)
        }


    }

}

