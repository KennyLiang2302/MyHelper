package com.example.tutorme

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val netid: String,
    val name: String,
    val location : String,
    val tutor: MutableList<Tutor>,
    val student: MutableList<Student>,
    val session_token: String,
    val update_token: String




)
