package com.cardforge.app.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class ProgressState {
    UNCOMPLETED,
    GOOD,
    EASY,
    AGAIN
}

class ProgressBlock(
    val index: Int
) {
    var state by mutableStateOf(ProgressState.UNCOMPLETED)
}

