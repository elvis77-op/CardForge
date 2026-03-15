package com.cardforge.app.cardgen

import com.cardforge.app.database.entity.CardEntity

object BasicCardGenerator {

    fun generateCards(
        deckId: Long,
        textChunks: List<String>
    ): List<CardEntity> {

        val cards = mutableListOf<CardEntity>()

        var count = 1

        for (chunk in textChunks) {

            val sentences =
                chunk.split(". ")

            for (sentence in sentences) {

                if (sentence.length > 20) {

                    val card = CardEntity(
                        deckId = deckId,
                        front = "Card$count",
                        back = sentence,
                        cardType = "basic"
                    )

                    count++

                    cards.add(card)
                }
            }
        }

        return cards
    }
}