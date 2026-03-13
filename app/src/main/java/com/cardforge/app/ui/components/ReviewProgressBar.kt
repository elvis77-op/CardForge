package com.cardforge.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReviewProgressBar(
    current: Int,
    total: Int
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        for (i in 0 until total) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(

                        if (i <= current)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant,

                        shape = RoundedCornerShape(4.dp)

                    )
            )

        }

    }

}