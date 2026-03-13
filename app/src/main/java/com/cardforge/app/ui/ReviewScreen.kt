package com.cardforge.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cardforge.app.viewmodel.ReviewViewModel
import com.cardforge.app.viewmodel.ReviewViewModelFactory
import com.cardforge.app.ui.components.FlipCard

@Composable
fun ReviewScreen(
    deckId: Long,
    factory: ReviewViewModelFactory
) {

    val viewModel: ReviewViewModel = viewModel(factory = factory)

    val cards by viewModel.cards.collectAsState()

    var currentIndex by remember { mutableStateOf(0) }

    var flipped by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadDueCards(deckId)
    }

    if (cards.isEmpty()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No cards to review")
        }

        return
    }

    if (currentIndex >= cards.size) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("🎉 Review Complete")
        }

        return
    }

    val card = cards[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Card ${currentIndex + 1} / ${cards.size}",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        FlipCard(
            front = card.front,
            back = card.back,
            onFlip = { flipped = true }
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (flipped) {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {

                        viewModel.reviewCard(card, 1)

                        currentIndex++
                        flipped = false

                    }
                ) {
                    Text("Again")
                }

                Button(
                    onClick = {

                        viewModel.reviewCard(card, 3)

                        currentIndex++
                        flipped = false

                    }
                ) {
                    Text("Good")
                }

                Button(
                    onClick = {

                        viewModel.reviewCard(card, 4)

                        currentIndex++
                        flipped = false

                    }
                ) {
                    Text("Easy")
                }

            }

        } else {

            Text(
                text = "Tap card to reveal answer",
                style = MaterialTheme.typography.bodyMedium
            )

        }

    }

}