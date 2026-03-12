package com.cardforge.app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val deckId: Long,

    val front: String,
    val back: String,

    val cardType: String,

    val imagePath: String?,

    val sourceRef: String?,

    val createdAt: Long = System.currentTimeMillis()

)