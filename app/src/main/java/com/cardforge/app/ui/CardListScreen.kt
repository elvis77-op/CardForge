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
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.cardforge.app.utils.TxtImporter
import com.cardforge.app.utils.TextChunker
import com.cardforge.app.cardgen.BasicCardGenerator
import com.cardforge.app.database.DatabaseProvider
import com.cardforge.app.repository.CardRepository
import com.cardforge.app.viewmodel.CardViewModel
import com.cardforge.app.viewmodel.CardViewModelFactory

@Composable
fun CardListScreen(
    context: android.content.Context,
    deckId: Long,
    onStartReview: () -> Unit
) {
    val scope = rememberCoroutineScope()


    val database = DatabaseProvider.getDatabase(context)
    val repository = CardRepository(database.cardDao())

    val viewModel: CardViewModel = viewModel(
        factory = CardViewModelFactory(repository)
    )

    val cards by viewModel.cards.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    val navController = rememberNavController()

    val txtPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        uri?.let {

            val text = TxtImporter.readText(context, it)

            val chunks = TextChunker.splitText(text)

            val cards =
                BasicCardGenerator.generateCards(deckId, chunks)

            scope.launch {

                cards.forEach { card ->
                    repository.insertCard(card)
                }

                viewModel.loadCards(deckId)

            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCards(deckId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

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

            Button(
                onClick = {
                    navController.navigate("review/$deckId")
                }
            ) {
                Text("Start Review")
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(cards) { card ->
                val isNew =
                    System.currentTimeMillis() - card.createdAt < 5000

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            if (isNew)
                                Color(0xFFA5D6A7)   // 浅绿色
                            else
                                MaterialTheme.colorScheme.surface
                    )
                ){

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text("Front: ${card.front}")
                        Text("Back: ${card.back}")

                        TextButton(
                            onClick = { viewModel.deleteCard(card, deckId) }
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