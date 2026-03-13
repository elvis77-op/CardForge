package com.cardforge.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

            shape = RoundedCornerShape(18.dp),

            elevation = CardDefaults.cardElevation(4.dp)

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {

                    Text(
                        text = card.front,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }

                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        )
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
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