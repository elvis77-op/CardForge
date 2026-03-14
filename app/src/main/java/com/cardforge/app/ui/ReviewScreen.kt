package com.cardforge.app.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cardforge.app.database.entity.CardEntity
import com.cardforge.app.ui.components.FlipCard
import com.cardforge.app.ui.components.ReviewProgressBar
import com.cardforge.app.ui.components.StudyProgressBar
import com.cardforge.app.viewmodel.ReviewViewModel
import com.cardforge.app.viewmodel.ReviewViewModelFactory

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    deckId: Long,
    factory: ReviewViewModelFactory,
    navController: NavController
) {

    val viewModel: ReviewViewModel = viewModel(factory = factory)

    val cards by viewModel.cards.collectAsState()

    var flipped by remember { mutableStateOf(false) }

    val newCards =
        cards.count { it.id > 0 }

    val reviewCards =
        cards.size - newCards

    val queue = remember { mutableStateListOf<CardEntity>() }

    val completed = cards.size - queue.size

    val remaining = queue.size

    LaunchedEffect(Unit) {
        viewModel.loadDueCards(deckId)
    }

    LaunchedEffect(cards) {

        queue.clear()
        queue.addAll(cards)

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

    if (completed >= cards.size) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("🎉 Review Complete")
        }

        return
    }

    val card = queue[0]

    Scaffold(

        topBar = {

            TopAppBar(

                title = { Text("Review") },

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
                .padding(padding)
                .padding(20.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StudyProgressBar(

                completed = completed,
                remaining = remaining

            )
//            ReviewProgressBar(
//                current = currentIndex,
//                total = cards.size
//            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Card $completed / ${cards.size}",
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

                            queue.addLast(card)
                            queue.removeFirst()
                            flipped = false

                        }
                    ) {
                        Text("Again")
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {

                            viewModel.reviewCard(card, 3)

                            queue.removeFirst()
                            flipped = false

                        }
                    ) {
                        Text("Good")
                    }

                    Button(
                        onClick = {

                            viewModel.reviewCard(card, 4)

                            queue.removeFirst()
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