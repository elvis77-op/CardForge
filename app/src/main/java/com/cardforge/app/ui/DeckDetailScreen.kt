package com.cardforge.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun DeckDetailScreen(
    deckId: Long,
    navController: NavController
) {

    val context = LocalContext.current

    CardListScreen(
        context = context,
        deckId = deckId,
        onStartReview = {

            navController.navigate("review/$deckId")

        }
    )
}