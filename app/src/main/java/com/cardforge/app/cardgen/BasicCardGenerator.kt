package com.cardforge.app.cardgen

import com.cardforge.app.database.entity.CardEntity

object BasicCardGenerator {

    fun generateCards(
        deckId: Long,
        textChunks: List<String>
    ): List<CardEntity> {

        val cards = mutableListOf<CardEntity>()

        for (chunk in textChunks) {

            val sentences =
                chunk.split(". ")

            for (sentence in sentences) {

                if (sentence.length > 20) {

                    val card = CardEntity(
                        deckId = deckId,
                        front = "Explain:",
                        back = sentence,
                        cardType = "basic"
                    )

                    cards.add(card)
                }
            }
        }

        return cards
    }
}