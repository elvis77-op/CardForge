package com.cardforge.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cardforge.app.ui.components.FlipCard
import com.cardforge.app.ui.components.ReviewProgressBar
import com.cardforge.app.viewmodel.ReviewViewModel
import com.cardforge.app.viewmodel.ReviewViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    deckId: Long,
    factory: ReviewViewModelFactory,
    navController: NavController
) {

    val viewModel: ReviewViewModel = viewModel(factory = factory)

    val cards by viewModel.cards.collectAsState()

    var currentIndex by remember { mutableStateOf(0) }

    var flipped by remember { mutableStateOf(false) }

    val progress =
        (currentIndex + 1).toFloat() / cards.size.toFloat()

    ReviewProgressBar(
        current = currentIndex,
        total = cards.size
    )

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
    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Review")
                },

                navigationIcon = {

                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )

                    }

                }

            )

        }

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),

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
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
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

}