package com.cardforge.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.cardforge.app.ui.DeckDetailScreen
import com.cardforge.app.ui.DeckListScreen

@Composable
fun CardForgeNavHost() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "deckList"
    ) {

        composable("deckList") {
            DeckListScreen(
                context = context,
                onDeckClick = { deckId ->
                    navController.navigate("deckDetail/$deckId")
                }
            )
        }

        composable("deckDetail/{deckId}") { backStackEntry ->

            val deckId =
                backStackEntry.arguments?.getString("deckId")?.toLong() ?: 0

            DeckDetailScreen(
                deckId = deckId
            )
        }
    }
}