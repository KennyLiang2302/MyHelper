package com.example.tutorme

data class Tutors(
    val name: String,
    var subjects: List<String>,
    val education: String,
    val GPA: Double,
    val rating: Double,
    var location: String,
)
