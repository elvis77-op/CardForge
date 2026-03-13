package com.cardforge.app.database.dao

import androidx.room.*
import com.cardforge.app.database.entity.CardEntity

@Dao
interface CardDao {

    @Query("""
    SELECT * FROM cards 
    WHERE deckId = :deckId
    """)
    suspend fun getCardsForDeck(deckId: Long): List<CardEntity>

    @Query("""
    SELECT c.*
    FROM cards c
    LEFT JOIN reviews r
    ON c.id = r.cardId
    WHERE c.deckId = :deckId
    AND (r.nextReview IS NULL OR r.nextReview <= :now)
    """)
    suspend fun getDueCards(
        deckId: Long,
        now: Long
    ): List<CardEntity>

    @Insert
    suspend fun insertCard(card: CardEntity)

    @Delete
    suspend fun deleteCard(card: CardEntity)

}