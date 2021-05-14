package com.example.tutorme

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class TutorSession(
    val data: MutableList<Session>

)
