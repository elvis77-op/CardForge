package com.cardforge.app.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cardforge.app.database.entity.CardEntity

@Composable
fun CardPreviewItem(
    card: CardEntity,
    onDelete: () -> Unit
) {

    val dismissState =
        rememberSwipeToDismissBoxState(
            confirmValueChange = {

                if (it == SwipeToDismissBoxValue.EndToStart) {
                    onDelete()
                    true
                } else false

            }
        )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {

                Text(
                    text = "Delete",
                    color = Color.Red
                )

            }

        }

    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 8.dp),

            shape = RoundedCornerShape(20.dp),

            elevation = CardDefaults.cardElevation(4.dp)

        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Front",
                    style = MaterialTheme.typography.labelMedium
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 6.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {

                    Text(
                        text = card.front,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Back",
                    style = MaterialTheme.typography.labelMedium
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 6.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {

                    Text(
                        text = card.back,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }

            }

        }

    }

}