package com.cardforge.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.cardforge.app.database.entity.CardEntity
import com.cardforge.app.ui.ReviewItem

@Composable
fun StudyProgressBar(
    queue: List<ReviewItem>,
    currentIndex: Int,
    windowSize: Int = 20
) {

    val m = queue.size
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

            val queueIndex = windowStart + i

            val item = queue[queueIndex]
            val state = item.state

            val baseColor = when (state) {

                ProgressState.UNCOMPLETED ->
                    MaterialTheme.colorScheme.surfaceVariant

                ProgressState.GOOD ->
                    MaterialTheme.colorScheme.primary

                ProgressState.EASY ->
                    Color(0xFF26C6DA)

                ProgressState.AGAIN ->
                    Color(0xFFFF5252)
            }

            val bounce by rememberInfiniteTransition().animateFloat(
                initialValue = 0.9f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(350),
                    repeatMode = RepeatMode.Reverse
                )
            )

            val shake by rememberInfiniteTransition().animateFloat(
                initialValue = -3f,
                targetValue = 3f,
                animationSpec = infiniteRepeatable(
                    animation = tween(120),
                    repeatMode = RepeatMode.Reverse
                )
            )

            val animatedColor by animateColorAsState(
                targetValue = baseColor,
                animationSpec = tween(350)
            )

            val pointerScale by animateFloatAsState(
                targetValue = if (i == pointer) 1.2f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy
                )
            )

            val flashAlpha =
                if (state == ProgressState.AGAIN || state == ProgressState.EASY) {

                    val transition = rememberInfiniteTransition()

                    transition.animateFloat(
                        initialValue = 0.4f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(200),
                            repeatMode = RepeatMode.Reverse
                        )
                    ).value

                } else 1f


            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .graphicsLayer {

                        if (
                            state == ProgressState.AGAIN
                        ) {
                            alpha = flashAlpha
                        }
                        else if (
                            state == ProgressState.EASY
                        ){
                            translationX = shake
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
                                when (state) {
                                    ProgressState.UNCOMPLETED ->
                                        MaterialTheme.colorScheme.surface

                                    ProgressState.GOOD ->
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)

                                    ProgressState.EASY ->
                                        Color(0xFF26C6DA).copy(alpha = 0.9f)

                                    ProgressState.AGAIN ->
                                        Color(0xFFFF5252).copy(alpha = 0.95f)
                                },
                                RoundedCornerShape(3.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = Color.White.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(3.dp)
                            )
                            .graphicsLayer {

                                scaleX = pointerScale
                                scaleY = pointerScale

                            }
                    )

                }

            }

        }

    }

}


