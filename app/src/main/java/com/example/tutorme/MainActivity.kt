package com.example.tutorme

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var tutorButton: ImageButton
    lateinit var calendarButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        val userID = intent.extras?.getInt("SenderID")
        val session = intent.extras?.getString("session")
        val user = intent.extras?.getString("user")
        if (user != null) {
            Log.d("user", user)
        }

        if (user == "student") {
            Log.d("studnet", "student")
            val fragmentManager = supportFragmentManager;
            fragmentManager.beginTransaction().apply {
                TutorsList.newInstance(session)?.let { replace(R.id.Fragment, it) }
                addToBackStack(null)
                commit()
            }


            tutorButton = findViewById(R.id.tutorButton)
            tutorButton.setOnClickListener {
                fragmentManager.beginTransaction().apply {
                    TutorsList.newInstance(session)?.let { it1 -> replace(R.id.Fragment, it1) }
                    addToBackStack(null)
                    commit()
                }
            }

            calendarButton = findViewById(R.id.calenderButton)
            calendarButton.setOnClickListener {
                fragmentManager.beginTransaction().apply {
                    replace(R.id.Fragment, CalenderFragment())
                    addToBackStack(null)
                    commit()
                }
            }
        }

        if (user == "tutor") {

            val inviteID = intent.extras?.getInt("inviteID")

            val fragmentManager = supportFragmentManager;
            fragmentManager.beginTransaction().apply {
                if (session != null) {
                    if (inviteID != null) {
                        InvitesList.newInstance(userID, session, inviteID)?.let { replace(R.id.Fragment, it) }
                    }
                }
                addToBackStack(null)
                commit()
            }


            tutorButton = findViewById(R.id.tutorButton)
            tutorButton.setOnClickListener {
                fragmentManager.beginTransaction().apply {
                    if (session != null) {
                        if (inviteID != null) {
                            InvitesList.newInstance(userID, session, inviteID)?.let { it1 -> replace(R.id.Fragment, it1) }
                        }
                    }
                    addToBackStack(null)
                    commit()
                }
            }

            calendarButton = findViewById(R.id.calenderButton)
            calendarButton.setOnClickListener {
                fragmentManager.beginTransaction().apply {
                    replace(R.id.Fragment, CalenderFragment())
                    addToBackStack(null)
                    commit()
                }
            }

        }


    }
}

