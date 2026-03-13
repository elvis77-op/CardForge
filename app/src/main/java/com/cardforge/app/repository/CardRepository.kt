package com.cardforge.app.repository

import com.cardforge.app.database.dao.CardDao
import com.cardforge.app.database.entity.CardEntity

class CardRepository(
    private val cardDao: CardDao
) {

    suspend fun getCardsForDeck(deckId: Long): List<CardEntity> {
        return cardDao.getCardsForDeck(deckId)
    }

    suspend fun getDueCards(deckId: Long): List<CardEntity> {

        return cardDao.getDueCards(
            deckId,
            System.currentTimeMillis()
        )

    }

    suspend fun insertCard(card: CardEntity) {
        cardDao.insertCard(card)
    }

    suspend fun deleteCard(card: CardEntity) {
        cardDao.deleteCard(card)
    }
}