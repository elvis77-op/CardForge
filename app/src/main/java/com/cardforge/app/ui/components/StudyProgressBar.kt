package com.cardforge.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StudyProgressBar(

    progress: Int,
    total: Int,
    windowSize: Int = 20

) {

    val size = windowSize.coerceAtMost(total)

    val center = size / 2

    val start = when {

        total <= size -> 0

        progress < center -> 0

        progress > total - center -> total - size

        else -> progress - center

    }

    val end = start + size

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(14.dp),

        horizontalArrangement = Arrangement.spacedBy(4.dp)

    ) {

        for (i in start until end) {

            val color = if (i < progress)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color,
                        RoundedCornerShape(4.dp)
                    )
            )

        }

    }

}