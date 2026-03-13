package com.cardforge.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cardforge.app.database.DatabaseProvider
import com.cardforge.app.repository.CardRepository
import com.cardforge.app.repository.ReviewRepository
import com.cardforge.app.ui.DeckListScreen
import com.cardforge.app.ui.navigation.CardForgeNavHost
import com.cardforge.app.ui.theme.CardForgeTheme
import com.cardforge.app.viewmodel.ReviewViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = DatabaseProvider.getDatabase(this)

        val cardRepository =
            CardRepository(database.cardDao())

        val reviewRepository =
            ReviewRepository(database.reviewDao())

        val reviewFactory =
            ReviewViewModelFactory(
                cardRepository,
                reviewRepository
            )

        setContent {

            CardForgeNavHost(
                reviewFactory = reviewFactory
            )

        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CardForgeTheme {
        Greeting("Android")
    }
}