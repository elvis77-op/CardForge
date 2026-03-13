package com.cardforge.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cardforge.app.database.dao.CardDao
import com.cardforge.app.database.dao.DeckDao
import com.cardforge.app.database.dao.ReviewDao
import com.cardforge.app.database.entity.CardEntity
import com.cardforge.app.database.entity.DeckEntity
import com.cardforge.app.database.entity.ReviewEntity

@Database(
    entities = [
        DeckEntity::class,
        CardEntity::class,
        ReviewEntity::class
    ],
    version = 1
)
abstract class CardForgeDatabase : RoomDatabase() {

    abstract fun deckDao(): DeckDao
    abstract fun cardDao(): CardDao
    abstract fun reviewDao(): ReviewDao

}