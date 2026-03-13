package com.cardforge.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardforge.app.database.entity.CardEntity
import com.cardforge.app.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardViewModel(
    private val repository: CardRepository
) : ViewModel() {

    private val _cards = MutableStateFlow<List<CardEntity>>(emptyList())
    val cards: StateFlow<List<CardEntity>> = _cards

    fun loadCards(deckId: Long) {
        viewModelScope.launch {
            _cards.value = repository.getCardsForDeck(deckId)
        }
    }

    fun addCard(deckId: Long, front: String, back: String) {
        viewModelScope.launch {

            val card = CardEntity(
                deckId = deckId,
                front = front,
                back = back,
                cardType = "basic"
            )

            repository.insertCard(card)
            loadCards(deckId)
        }
    }

    fun deleteCard(card: CardEntity) {
        viewModelScope.launch {
            repository.deleteCard(card)
        }
    }
}