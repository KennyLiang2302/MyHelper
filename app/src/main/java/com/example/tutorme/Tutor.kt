package com.example.tutorme

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Tutor(
    val rating: Int,
    val description: String,
    val subjects: List<Subject>,
    val invites: List<Invites>
)

