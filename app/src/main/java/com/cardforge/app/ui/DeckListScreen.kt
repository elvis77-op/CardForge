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
import com.cardforge.app.repository.DeckRepository
import com.cardforge.app.viewmodel.DeckViewModel
import com.cardforge.app.viewmodel.DeckViewModelFactory

@Composable
fun DeckListScreen(
    context: android.content.Context,
    onDeckClick: (Long) -> Unit
) {

    val database = DatabaseProvider.getDatabase(context)
    val repository = DeckRepository(database.deckDao())

    val viewModel: DeckViewModel = viewModel(
        factory = DeckViewModelFactory(repository)
    )

    val decks by viewModel.decks.collectAsState()

    var newDeckName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadDecks()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "CardForge",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {

            TextField(
                value = newDeckName,
                onValueChange = { newDeckName = it },
                label = { Text("New Deck") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newDeckName.isNotBlank()) {
                        viewModel.addDeck(newDeckName, null)
                        newDeckName = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(decks) { deck ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    onClick = { onDeckClick(deck.id) }
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(deck.name)

                        TextButton(
                            onClick = { viewModel.deleteDeck(deck) }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}