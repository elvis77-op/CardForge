package com.cardforge.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cardforge.app.database.entity.CardEntity

@Composable
fun CardReviewScreen(

    card: CardEntity,
    onAnswer: (Int) -> Unit

) {

    var showBack by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = card.front,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (showBack) {

            Text(card.back)

            Spacer(modifier = Modifier.height(24.dp))

            Row {

                Button(onClick = { onAnswer(1) }) {
                    Text("Hard")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { onAnswer(3) }) {
                    Text("Good")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { onAnswer(4) }) {
                    Text("Easy")
                }
            }

        } else {

            Button(
                onClick = { showBack = true }
            ) {
                Text("Show Answer")
            }
        }
    }
}