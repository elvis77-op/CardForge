package com.cardforge.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun AddCardDialog(
    onAdd: (String, String) -> Unit,
    onDismiss: () -> Unit
) {

    var front by remember { mutableStateOf("") }
    var back by remember { mutableStateOf("") }

    AlertDialog(

        onDismissRequest = onDismiss,

        title = { Text("Add Card") },

        text = {

            Column {

                TextField(
                    value = front,
                    onValueChange = { front = it },
                    label = { Text("Front") }
                )

                TextField(
                    value = back,
                    onValueChange = { back = it },
                    label = { Text("Back") }
                )
            }
        },

        confirmButton = {

            Button(
                onClick = { onAdd(front, back) }
            ) {
                Text("Add")
            }
        }
    )
}