package com.cardforge.app.ui.components

data class NotificationMessage(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null
)