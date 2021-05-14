package com.example.tutorme
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class InviteList(
    val data : MutableList<InviteReturn>,
)
