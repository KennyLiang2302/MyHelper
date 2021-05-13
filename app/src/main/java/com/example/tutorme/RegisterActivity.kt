package com.example.tutorme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.internal.ParcelableSparseArray
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.xml.sax.Parser
import java.io.IOException
import java.net.PasswordAuthentication


private val client = OkHttpClient()
private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
private val postJsonAdapter = moshi.adapter(UserPost::class.java)

class RegisterActivity : AppCompatActivity() {

    lateinit var registerButton: Button
    lateinit var email: EditText
    lateinit var netID: EditText
    lateinit var location: EditText
    lateinit var password: EditText
    lateinit var message: TextView
    lateinit var name: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        message = findViewById(R.id.message)
        message.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        email = findViewById(R.id.registerEmail)
        netID = findViewById(R.id.registerNetID)
        location = findViewById(R.id.registerLocation)
        password = findViewById(R.id.registerPassWord)
        name = findViewById(R.id.registerName)


        registerButton = findViewById(R.id.register)
        registerButton.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO){postData()}
            }

        }


    }

    fun postData() {
        val post = UserPost(email.text.toString(), netID.text.toString(), name.text.toString() ,
            password.text.toString(), location.text.toString())
        val requestPost = Request.Builder().url("https://my-helper-appdev.herokuapp.com/api/register/")
            .post(postJsonAdapter.toJson(post).toRequestBody()).build()

        client.newCall(requestPost).execute().use {
            if (!it.isSuccessful) throw IOException("Unexpected code $it")

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val userAdapter = moshi.adapter(Json::class.java)


            val user = userAdapter.fromJson(it.body!!.string())
            val id = user!!.data.id

            val intent = Intent(this@RegisterActivity, ChoiceActivity::class.java)
            intent.putExtra("id", id)

            startActivity(intent)
        }

//            Toast.makeText(this@RegisterActivity, "Register Successful", Toast.LENGTH_SHORT).show()

        }







}