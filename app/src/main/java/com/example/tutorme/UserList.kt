package com.example.tutorme

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class UserList(
    val data: MutableList<User>
)
