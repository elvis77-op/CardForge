package com.cardforge.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardforge.app.database.entity.DeckEntity
import com.cardforge.app.repository.DeckRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeckViewModel(
    private val repository: DeckRepository
) : ViewModel() {

    private val _decks = MutableStateFlow<List<DeckEntity>>(emptyList())
    val decks: StateFlow<List<DeckEntity>> = _decks

    fun loadDecks() {
        viewModelScope.launch {
            _decks.value = repository.getAllDecks()
        }
    }

    fun addDeck(name: String, description: String?) {
        viewModelScope.launch {
            val deck = DeckEntity(
                name = name,
                description = description
            )
            repository.insertDeck(deck)
            loadDecks()
        }
    }

    fun deleteDeck(deck: DeckEntity) {
        viewModelScope.launch {
            repository.deleteDeck(deck)
            loadDecks()
        }
    }
}