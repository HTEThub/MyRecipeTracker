package com.htet08.myrecipetracker.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment

@Composable
fun ConfirmDialogPopup(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismissText: String = "No",
    onConfirmText: String = "Yes",
    onExtraAction: (() -> Unit)? = null,
    extraActionText: String = "Save as Draft"
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        TextButton(onClick = onConfirm) {
                            Text(onConfirmText)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        TextButton(onClick = onDismiss) {
                            Text(onDismissText)
                        }
                    }

                    if (onExtraAction != null) {
                        TextButton(onClick = onExtraAction) {
                            Text(extraActionText)
                        }
                    }
                }
            },
            dismissButton = {}
        )
    }
}
