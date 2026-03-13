package com.cardforge.app.repository

import com.cardforge.app.database.dao.ReviewDao
import com.cardforge.app.database.entity.ReviewEntity

class ReviewRepository(
    private val reviewDao: ReviewDao
) {

    suspend fun getReviewsForCard(cardId: Long): List<ReviewEntity> {
        return reviewDao.getReviewsForCard(cardId)
    }

    suspend fun insertReview(review: ReviewEntity) {
        reviewDao.insertReview(review)
    }
}