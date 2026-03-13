package com.cardforge.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardforge.app.repository.CardRepository
import com.cardforge.app.repository.ReviewRepository

class ReviewViewModelFactory(
    private val cardRepository: CardRepository,
    private val reviewRepository: ReviewRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {

            return ReviewViewModel(
                cardRepository,
                reviewRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}