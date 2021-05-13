package com.example.tutorme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


private val client = OkHttpClient()
private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
private val postJsonAdapter = moshi.adapter(LoginPost::class.java)
class LoginActivity : AppCompatActivity() {
    lateinit var loginButton: Button
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var signUp: Button
    lateinit var studentOrTutor: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }




        email = findViewById(R.id.email)
        password = findViewById(R.id.password)

        loginButton = findViewById(R.id.SignInButton)
        loginButton.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO) { postData() }
            }
        }


        signUp = findViewById(R.id.SignUpButton)
        signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun postData() {
        val post = LoginPost(email.text.toString(), password.text.toString())

        val requestPost =
            Request.Builder().url("https://my-helper-appdev.herokuapp.com/api/login/")
                .post(postJsonAdapter.toJson(post).toRequestBody()).build()



        client.newCall(requestPost).execute().use {
            if (!it.isSuccessful) {
                //Toast.makeText(this, "Wrong Password or Email", Toast.LENGTH_SHORT)
            }
            if (it.isSuccessful) {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

                val userAdapter = moshi.adapter(Json::class.java)


                val user = userAdapter.fromJson(it.body!!.string())
                Log.d("json", user.toString())
                val id = user!!.data.id
                val session = user!!.data.session_token

                if (user.data.tutor.size == 1 ){
                    studentOrTutor = "tutor"
                }else if (user.data.student.size == 1){
                    studentOrTutor = "student"
                }




                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("SenderID", id)
                intent.putExtra("session" , session)
                intent.putExtra("user", studentOrTutor)
                startActivity(intent)


            }
            }
        }
    }




