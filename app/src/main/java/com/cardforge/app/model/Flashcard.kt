package com.cardforge.app.model

data class Flashcard(
    val id: Long = 0,
    val deckId: Long,

    val front: String,
    val back: String,

    val cardType: CardType,

    val imagePath: String? = null,
    val sourceRef: String? = null,

    val createdAt: Long = System.currentTimeMillis()
)