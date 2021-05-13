package com.example.tutorme


data class Tutor(
    val rating: Int,
    val description: String,
    val subjects: List<Subject>,
    val invites: List<Invites>
)

