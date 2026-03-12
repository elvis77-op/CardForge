package com.cardforge.app.ui.review

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cardforge.app.model.Flashcard

@Composable
fun ReviewScreen(card: Flashcard) {

    var showAnswer by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = if (!showAnswer) card.front else card.back,
                    style = MaterialTheme.typography.headlineMedium
                )

            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        if (!showAnswer) {

            Button(onClick = { showAnswer = true }) {
                Text("Show Answer")
            }

        } else {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(onClick = { /* Again */ }) {
                    Text("Again")
                }

                Button(onClick = { /* Good */ }) {
                    Text("Good")
                }

                Button(onClick = { /* Easy */ }) {
                    Text("Easy")
                }

            }

        }

    }

}