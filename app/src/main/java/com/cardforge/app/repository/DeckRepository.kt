package com.cardforge.app.repository

import com.cardforge.app.database.dao.DeckDao
import com.cardforge.app.database.entity.DeckEntity

class DeckRepository(
    private val deckDao: DeckDao
) {

    suspend fun getAllDecks(): List<DeckEntity> {
        return deckDao.getAllDecks()
    }

    suspend fun insertDeck(deck: DeckEntity) {
        deckDao.insertDeck(deck)
    }

    suspend fun deleteDeck(deck: DeckEntity) {
        deckDao.deleteDeck(deck)
    }
}