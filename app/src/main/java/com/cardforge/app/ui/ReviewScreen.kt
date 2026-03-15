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
import com.cardforge.app.ui.components.ProgressBlock
import com.cardforge.app.ui.components.ProgressState
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

    val queue = remember { mutableStateListOf<CardEntity>() }

    val progressBlocks = remember {
        mutableStateListOf<ProgressBlock>()
    }

    fun answerCard(
        card: CardEntity,
        state: ProgressState
    ) {

        val index = queue.indexOfFirst { it.id == card.id }

        if (index == -1) return

        val blockIndex =
            cards.indexOfFirst { it.id == card.id }

        if (blockIndex != -1) {
            progressBlocks[blockIndex].state = state
        }

        val removedCard = queue.removeAt(index)

        if (state == ProgressState.AGAIN) {

            val insertIndex = (queue.size * 0.7).toInt()

            queue.add(insertIndex, removedCard)

        }

    }



    LaunchedEffect(Unit) {
        viewModel.loadDueCards(deckId)
    }

    LaunchedEffect(cards) {

        if (queue.isEmpty()) {
            queue.addAll(cards)
        }

        if (progressBlocks.isEmpty()) {

            cards.forEachIndexed { i, _ ->
                progressBlocks.add(
                    ProgressBlock(index = i)
                )
            }

        }

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

    if (queue.isEmpty()) {

        Scaffold(

            topBar = {

                TopAppBar(

                    title = { Text("Review Complete") },

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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {

                Text("🎉 Review Complete")

            }

        }

        return
    }

    val card = queue.first()

    val currentIndex = cards.size - queue.size

    LaunchedEffect(card.id) {
        flipped = false
    }

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
                blocks = progressBlocks,
                currentIndex = currentIndex
            )


            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Card ${cards.size - queue.size} / ${cards.size}",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(24.dp))


            key(card.id) {
                FlipCard(
                    front = card.front,
                    back = card.back,
                    onFlip = { flipped = true }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (flipped) {

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {

                            viewModel.reviewCard(card, 1)

                            answerCard(card, ProgressState.AGAIN)


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

                            answerCard(card, ProgressState.GOOD)

                        }
                    ) {
                        Text("Good")
                    }

                    Button(
                        onClick = {

                            viewModel.reviewCard(card, 4)

                            answerCard(card, ProgressState.EASY)


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
