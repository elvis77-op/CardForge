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

    @Insert
    suspend fun insertCard(card: CardEntity)

    @Delete
    suspend fun deleteCard(card: CardEntity)

}