package com.cardforge.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun FlipCard(
    front: String,
    back: String,
    onFlip: (Boolean) -> Unit
) {

    var flipped by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable {

                flipped = !flipped
                onFlip(flipped)

            },
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {

                    rotationY = rotation
                    cameraDistance = 12 * density

                },

            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)

        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {

                if (rotation <= 90f) {

                    Text(
                        text = front,
                        style = MaterialTheme.typography.headlineMedium
                    )

                } else {

                    Box(
                        modifier = Modifier.graphicsLayer {
                            rotationY = 180f
                        }
                    ) {

                        Text(
                            text = back,
                            style = MaterialTheme.typography.headlineMedium
                        )

                    }

                }

            }

        }

    }

}