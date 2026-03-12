package com.cardforge.app.model

data class Deck(
    val id: Long = 0,
    val name: String,
    val description: String?,
    val createdAt: Long = System.currentTimeMillis()
)