package com.cardforge.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun StudyProgressBar(
    blocks: List<ProgressBlock>,
    currentIndex: Int,
    windowSize: Int = 20
) {

    val m = blocks.size
    val n = minOf(windowSize, m)
    val center = n / 2

    val windowStart = when {

        currentIndex < center -> 0

        currentIndex < m - center -> currentIndex - center

        else -> m - n
    }

    val pointer = when {

        currentIndex < center -> currentIndex

        currentIndex < m - center -> center

        else -> currentIndex - (m - n)
    }

    val flashTransition = rememberInfiniteTransition()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        for (i in 0 until n) {

            val blockIndex = windowStart + i
            val block = blocks[blockIndex]

            val baseColor = when (block.state) {

                ProgressState.UNCOMPLETED ->
                    MaterialTheme.colorScheme.surfaceVariant

                ProgressState.GOOD ->
                    MaterialTheme.colorScheme.primary

                ProgressState.EASY ->
                    MaterialTheme.colorScheme.primaryContainer

                ProgressState.AGAIN ->
                    MaterialTheme.colorScheme.outlineVariant
            }

            val animatedColor by animateColorAsState(
                targetValue = baseColor,
                animationSpec = tween(350)
            )

            val flashAlpha by flashTransition.animateFloat(
                initialValue = 0.4f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = when (block.state) {

                            ProgressState.AGAIN -> 1200
                            ProgressState.EASY -> 1600

                            else -> 800
                        }
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .graphicsLayer {

                        if (
                            block.state == ProgressState.AGAIN ||
                            block.state == ProgressState.EASY
                        ) {
                            this.alpha = flashAlpha
                        }

                    }
                    .background(
                        animatedColor,
                        RoundedCornerShape(4.dp)
                    )
            ) {

                if (i == pointer) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp)
                            .background(
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(3.dp)
                            )
                    )

                }

            }

        }

    }
}


