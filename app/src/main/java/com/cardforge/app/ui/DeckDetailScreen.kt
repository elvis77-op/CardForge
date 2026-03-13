package com.cardforge.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import com.cardforge.app.utils.TxtImporter
import com.cardforge.app.utils.TextChunker
import com.cardforge.app.cardgen.BasicCardGenerator
import com.cardforge.app.database.DatabaseProvider
import com.cardforge.app.repository.CardRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun DeckDetailScreen(
    deckId: Long
) {

    val context = LocalContext.current

    val database = DatabaseProvider.getDatabase(context)
    val repository = CardRepository(database.cardDao())

    val scope = rememberCoroutineScope()

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

            }
        }
    }

    Column {

        Button(
            onClick = { txtPicker.launch("text/plain") }
        ) {
            Text("Import TXT")
        }

        CardListScreen(
            context = context,
            deckId = deckId
        )
    }
}