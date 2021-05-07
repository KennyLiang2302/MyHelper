package com.example.tutorme

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var tutorButton: ImageButton
    lateinit var messageButton: ImageButton
    lateinit var calendarButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }



        var list1: MutableList<String> = mutableListOf("math", "Chem")

        val tutor1 = Tutors(
                "Paul Suh", list1, "Cornell", 3.5, 5.0,
                "Cornell University"
        )


        var tutorList:MutableList<Tutors> = mutableListOf()
        tutorList.add(tutor1)




        val fragmentManager = supportFragmentManager;
        fragmentManager.beginTransaction().apply {
            replace(R.id.Fragment, TutorsList())
            addToBackStack(null)
            commit()
        }

        tutorButton = findViewById(R.id.tutorButton)
        tutorButton.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                replace(R.id.Fragment, TutorsList())
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

        messageButton = findViewById(R.id.messageButton)
        messageButton.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                replace(R.id.Fragment, MessageFragment())
                addToBackStack(null)
                commit()
            }
        }


    }
}