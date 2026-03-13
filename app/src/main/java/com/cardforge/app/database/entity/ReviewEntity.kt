package com.cardforge.app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val cardId: Long,

    val reviewTime: Long,

    val quality: Int,

    val interval: Int,

    val easeFactor: Float,

    val nextReview: Long

)