package com.cardforge.app.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: CardForgeDatabase? = null

    fun getDatabase(context: Context): CardForgeDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                CardForgeDatabase::class.java,
                "cardforge_database"
            ).build()

            INSTANCE = instance
            instance
        }
    }
}