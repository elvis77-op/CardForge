package com.cardforge.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.cardforge.app.utils.TxtImporter
import com.cardforge.app.utils.TextChunker
import com.cardforge.app.cardgen.BasicCardGenerator
import com.cardforge.app.database.DatabaseProvider
import com.cardforge.app.repository.CardRepository
import com.cardforge.app.ui.components.NotificationMessage
import com.cardforge.app.viewmodel.CardViewModel
import com.cardforge.app.viewmodel.CardViewModelFactory
import com.cardforge.app.ui.components.NotificationHost
import com.cardforge.app.ui.components.CardPreviewItem

@Composable
fun CardListScreen(
    context: android.content.Context,
    deckId: Long,
    onStartReview: () -> Unit
) {

    val scope = rememberCoroutineScope()

    val repository = remember {
        CardRepository(DatabaseProvider.getDatabase(context).cardDao())
    }

    val viewModel: CardViewModel = viewModel(
        factory = CardViewModelFactory(repository)
    )

    val cards by viewModel.cards.collectAsState()

    val listState = rememberLazyListState()

    var showDialog by remember { mutableStateOf(false) }

    val notifications =
        remember { mutableStateListOf<NotificationMessage>() }

    fun pushNotification(msg: NotificationMessage) {

        notifications.add(0, msg)

        scope.launch {

            kotlinx.coroutines.delay(3000)

            notifications.remove(msg)

        }
    }

    val txtPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        uri?.let {

            val text = TxtImporter.readText(context, it)
            val chunks = TextChunker.splitText(text)

            val generatedCards =
                BasicCardGenerator.generateCards(deckId, chunks)

            scope.launch {

                generatedCards.forEach {
                    repository.insertCard(it)
                }

                viewModel.loadCards(deckId)

                pushNotification(
                    NotificationMessage(
                        text = "${generatedCards.size} cards imported"
                    )
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCards(deckId)
    }

    LaunchedEffect(cards) {

        val newestIndex =
            cards.indexOfFirst {
                System.currentTimeMillis() - it.createdAt < 5000
            }

        if (newestIndex != -1) {
            listState.animateScrollToItem(newestIndex)
        }

    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Cards: ${cards.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row {

                Button(
                    onClick = { showDialog = true }
                ) {
                    Text("Add Card")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        txtPicker.launch("text/plain")
                    }
                ) {
                    Text("Import TXT")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onStartReview() }
                ) {
                    Text("Start Review")
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {

                items(
                    items = cards,
                    key = { it.id }
                ) { card ->

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

                        CardPreviewItem(

                            card = card,

                            onDelete = {

                                viewModel.deleteCard(card, deckId)

                                pushNotification(
                                    NotificationMessage(
                                        text = "Card deleted",
                                        actionLabel = "UNDO",
                                        onAction = {

                                            viewModel.addCard(
                                                deckId,
                                                card.front,
                                                card.back
                                            )

                                        }
                                    )
                                )

                            }

                        )

                    }

                }

            }

        }

        NotificationHost(
            messages = notifications,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

    }

    if (showDialog) {

        AddCardDialog(

            onAdd = { front, back ->

                viewModel.addCard(deckId, front, back)

                pushNotification(
                    NotificationMessage(
                        text = "Card added"
                    )
                )

                showDialog = false

            },

            onDismiss = {
                showDialog = false
            }

        )

    }

}


