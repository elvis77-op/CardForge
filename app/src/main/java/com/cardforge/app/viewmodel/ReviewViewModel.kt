package com.cardforge.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardforge.app.database.entity.CardEntity
import com.cardforge.app.database.entity.ReviewEntity
import com.cardforge.app.repository.CardRepository
import com.cardforge.app.repository.ReviewRepository
import com.cardforge.app.spacedrepetition.SM2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val cardRepository: CardRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _cards = MutableStateFlow<List<CardEntity>>(emptyList())
    val cards: StateFlow<List<CardEntity>> = _cards


    fun loadCards(deckId: Long) {

        viewModelScope.launch {

            _cards.value = cardRepository.getCardsForDeck(deckId)
        }
    }

    fun loadDueCards(deckId: Long) {

        viewModelScope.launch {

            _cards.value =
                cardRepository.getDueCards(deckId)

        }

    }

    fun reviewCard(card: CardEntity, quality: Int) {

        viewModelScope.launch {

            val (interval, ease) =
                SM2.review(1, 2.5f, quality)

            val nextReview =
                System.currentTimeMillis() + interval * 86400000L

            val review = ReviewEntity(
                cardId = card.id,
                reviewTime = System.currentTimeMillis(),
                quality = quality,
                interval = interval,
                easeFactor = ease,
                nextReview = nextReview
            )

            reviewRepository.insertReview(review)

        }
    }
}