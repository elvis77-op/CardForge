package com.cardforge.app.database.dao

import androidx.room.*
import com.cardforge.app.database.entity.DeckEntity

@Dao
interface DeckDao {

    @Query("SELECT * FROM decks")
    suspend fun getAllDecks(): List<DeckEntity>

    @Insert
    suspend fun insertDeck(deck: DeckEntity)

    @Delete
    suspend fun deleteDeck(deck: DeckEntity)

}