package com.cardforge.app.utils

object TextChunker {

    fun splitText(
        text: String,
        chunkSize: Int = 500
    ): List<String> {

        val words = text.split(" ")

        val chunks = mutableListOf<String>()

        var current = mutableListOf<String>()

        for (word in words) {

            current.add(word)

            if (current.size >= chunkSize) {

                chunks.add(current.joinToString(" "))
                current = mutableListOf()
            }
        }

        if (current.isNotEmpty()) {
            chunks.add(current.joinToString(" "))
        }

        return chunks
    }
}