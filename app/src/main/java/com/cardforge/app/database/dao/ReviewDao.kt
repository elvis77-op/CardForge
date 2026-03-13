package com.cardforge.app.database.dao

import androidx.room.*
import com.cardforge.app.database.entity.ReviewEntity

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews WHERE cardId = :cardId")
    suspend fun getReviewsForCard(cardId: Long): List<ReviewEntity>

    @Insert
    suspend fun insertReview(review: ReviewEntity)

}