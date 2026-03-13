package com.cardforge.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cardforge.app.viewmodel.ReviewViewModel
import com.cardforge.app.viewmodel.ReviewViewModelFactory
import com.cardforge.app.database.DatabaseProvider
import com.cardforge.app.repository.CardRepository
import com.cardforge.app.repository.ReviewRepository

@Composable
fun ReviewScreen(
    deckId: Long,
    factory: ReviewViewModelFactory
) {

    val context = LocalContext.current

    val viewModel: ReviewViewModel = viewModel(factory = factory)

    val cards by viewModel.cards.collectAsState()

    var currentIndex by remember { mutableStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCards(deckId)
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

        Spacer(modifier = Modifier.height(16.dp))

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

            Button(
                onClick = { showAnswer = true }
            ) {
                Text("Show Answer")
            }

        } else {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {

                        viewModel.reviewCard(card, 1)

                        currentIndex++
                        showAnswer = false
                    }
                ) {
                    Text("Again")
                }

                Button(
                    onClick = {

                        viewModel.reviewCard(card, 3)

                        currentIndex++
                        showAnswer = false
                    }
                ) {
                    Text("Good")
                }

                Button(
                    onClick = {

                        viewModel.reviewCard(card, 4)

                        currentIndex++
                        showAnswer = false
                    }
                ) {
                    Text("Easy")
                }

            }

        }

    }

}