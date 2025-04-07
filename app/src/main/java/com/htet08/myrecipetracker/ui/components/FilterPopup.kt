package com.htet08.myrecipetracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import com.htet08.myrecipetracker.ui.screens.CreateRecipeScreen.TagChip
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.htet08.myrecipetracker.model.CustomTag

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterPopup(
    customTags: List<CustomTag>,
    dietaryTags: List<CustomTag>,
    onCustomTagToggle: (index: Int) -> Unit,
    onDietaryTagToggle: (index: Int) -> Unit,
    onDismissRequest: () -> Unit,
    onApplyFilters: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Filter Recipes", style = MaterialTheme.typography.titleMedium) },
        text = {
            Column {
                Text("Custom Tags", style = MaterialTheme.typography.bodyMedium)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    customTags.forEachIndexed { index, tag ->
                        TagChip(
                            text = tag.text,
                            isSelected = tag.selected,
                            onClick = { onCustomTagToggle(index) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Dietary Preferences", style = MaterialTheme.typography.bodyMedium)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    dietaryTags.forEachIndexed { index, tag ->
                        TagChip(
                            text = tag.text,
                            isSelected = tag.selected,
                            onClick = { onDietaryTagToggle(index) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onApplyFilters) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}
