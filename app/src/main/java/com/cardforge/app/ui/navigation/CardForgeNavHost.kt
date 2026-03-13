package com.cardforge.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.cardforge.app.ui.DeckDetailScreen
import com.cardforge.app.ui.DeckListScreen
import com.cardforge.app.ui.ReviewScreen
import com.cardforge.app.viewmodel.ReviewViewModelFactory

@Composable
fun CardForgeNavHost(
    reviewFactory: ReviewViewModelFactory
) {

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
                deckId = deckId,
                navController = navController
            )
        }

        composable("review/{deckId}") { backStackEntry ->

            val deckId =
                backStackEntry.arguments
                    ?.getString("deckId")!!
                    .toLong()

            ReviewScreen(
                deckId = deckId,
                factory = reviewFactory
            )

        }
    }
}