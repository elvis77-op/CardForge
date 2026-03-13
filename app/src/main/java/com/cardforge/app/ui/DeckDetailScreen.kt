package com.cardforge.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeckDetailScreen(
    deckId: Long
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Deck ID: $deckId",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO add card */ }
        ) {
            Text("Add Card")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* TODO review cards */ }
        ) {
            Text("Start Review")
        }
    }
}