package com.example.y2cs.models

data class Inquiry(
    val name: String,
    val phone: String,
    val heading: String,
    val description: String,
    val expectedPrice: String,
    val imageUrl: String? = null
)
