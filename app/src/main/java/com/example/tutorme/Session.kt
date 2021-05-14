package com.example.tutorme

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Session(
    val student_id: String ,
    val tutor_id:String ,
    val timestamp: String,
    val subjects: List<Subject>
)
