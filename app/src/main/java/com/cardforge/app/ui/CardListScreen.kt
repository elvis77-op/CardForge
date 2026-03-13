package com.cardforge.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cardforge.app.database.DatabaseProvider
import com.cardforge.app.repository.CardRepository
import com.cardforge.app.viewmodel.CardViewModel
import com.cardforge.app.viewmodel.CardViewModelFactory

@Composable
fun CardListScreen(
    context: android.content.Context,
    deckId: Long
) {

    val database = DatabaseProvider.getDatabase(context)
    val repository = CardRepository(database.cardDao())

    val viewModel: CardViewModel = viewModel(
        factory = CardViewModelFactory(repository)
    )

    val cards by viewModel.cards.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCards(deckId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = { showDialog = true }
        ) {
            Text("Add Card")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(cards) { card ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text("Front: ${card.front}")
                        Text("Back: ${card.back}")

                        TextButton(
                            onClick = { viewModel.deleteCard(card) }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddCardDialog(
            onAdd = { front, back ->
                viewModel.addCard(deckId, front, back)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}