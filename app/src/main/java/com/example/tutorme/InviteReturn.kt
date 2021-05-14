package com.example.tutorme

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class InviteReturn(
    val id: Int,
    val subject_id: Int,
    val sender: List<Sender>,
)
