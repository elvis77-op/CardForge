package com.cardforge.app.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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

data class ReviewItem(
    val card: CardEntity,
    var state: ProgressState = ProgressState.UNCOMPLETED
)
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

    val queue = remember { mutableStateListOf<ReviewItem>() }

    var currentIndex by remember { mutableIntStateOf(0) }

    fun answerCard(
        item: ReviewItem,
        state: ProgressState
    ) {

        val index = queue.indexOf(item)
        if (index == -1) return

        item.state = state

        when (state) {

            ProgressState.AGAIN -> {

                val removed = queue.removeAt(index)

                val insertIndex =
                    minOf(index + 3, queue.size)

                queue.add(insertIndex, removed)

            }

            ProgressState.GOOD -> {

                currentIndex++

            }

            ProgressState.EASY -> {

                currentIndex++

            }

            else -> {}
        }

    }



    LaunchedEffect(Unit) {
        viewModel.loadDueCards(deckId)
    }

    LaunchedEffect(cards) {

        queue.clear()
        cards.forEach {
            queue.add(ReviewItem(it))
        }
        currentIndex = 0

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

    if (currentIndex == queue.size) {

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
            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Text("Back to Deck")

            }

        }

        return
    }

    val currentItem = queue[currentIndex]
    val card = currentItem.card

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
                queue = queue,
                currentIndex = currentIndex
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Card ${currentIndex + 1} / ${queue.size}",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

//            Box {
//                key(card.id) {
//                    FlipCard(
//                        front = card.front,
//                        back = card.back,
//                        onFlip = { flipped = true }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(32.dp))
//
//                if (currentItem.state == ProgressState.AGAIN) {
//
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.TopEnd)
//                            .padding(8.dp)
//                            .background(
//                                Color(0xFFFF5252),
//                                RoundedCornerShape(6.dp)
//                            )
//                            .padding(horizontal = 8.dp, vertical = 4.dp)
//                    ) {
//
//                        Text(
//                            text = "Reviewed Before",
//                            color = Color.White,
//                            style = MaterialTheme.typography.labelSmall
//                        )
//
//                    }
//                }
//            }
            key(card.id) {

                FlipCard(

                    front = {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (currentItem.state == ProgressState.AGAIN)
                                        Color(0xFFFF5252).copy(alpha = 0.05f)
                                    else Color.Transparent
                                )
                        ) {

                            if (currentItem.state == ProgressState.AGAIN) {

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .background(Color(0xFFFF5252))
                                )

                            }

                            Text(
                                text = card.front,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(horizontal = 32.dp),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                maxLines = 6,
                                overflow = TextOverflow.Ellipsis
                            )

                        }

                    },

                    back = {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (currentItem.state == ProgressState.AGAIN)
                                        Color(0xFFFF5252).copy(alpha = 0.05f)
                                    else Color.Transparent
                                )
                        ) {
                            if (currentItem.state == ProgressState.AGAIN) {

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .background(Color(0xFFFF5252))
                                )

                            }

                            Text(
                                text = card.back,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(horizontal = 32.dp),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                maxLines = 8,
                                overflow = TextOverflow.Ellipsis
                            )

                        }

                    },

                    onFlip = { flipped = true }

                )

            }

            if (flipped) {

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {

                            viewModel.reviewCard(card, 1)

                            answerCard(currentItem, ProgressState.AGAIN)


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

                            answerCard(currentItem, ProgressState.GOOD)

                        }
                    ) {
                        Text("Good")
                    }

                    Button(
                        onClick = {

                            viewModel.reviewCard(card, 4)

                            answerCard(currentItem, ProgressState.EASY)


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
