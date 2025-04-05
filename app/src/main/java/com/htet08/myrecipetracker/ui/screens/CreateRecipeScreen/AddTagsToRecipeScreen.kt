package com.htet08.myrecipetracker.ui.screens.CreateRecipeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.htet08.myrecipetracker.ui.components.RecipeBottomAppBar
import com.htet08.myrecipetracker.ui.components.RecipeTopAppBar
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddTagsToRecipeScreen(navController: NavHostController) {
    var customTags by remember { mutableStateOf(listOf<String>()) }
    var showTagInput by remember { mutableStateOf(false) }
    var newTagText by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            RecipeTopAppBar(
                title = "Add Tags",
                leftContent = {
                    TextButton(onClick = { /* TODO: Handle back */ }) {
                        Text(
                            text = "Back",
                            color = Color.White,
                            fontSize = 25.sp
                        )
                    }
                },
                rightContent = {
                    TextButton(onClick = { /* TODO: Handle save */ }) {
                        Text(
                            text = "Save",
                            color = Color.White,
                            fontSize = 25.sp
                        )
                    }
                }
            )
        },
        bottomBar = {
            RecipeBottomAppBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF3E9D1))
                .padding(20.dp)
        ) {
            Text(
                text = "Custom Tags",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                customTags.forEach { tag ->
                    TagChip(
                        text = tag,
                        onDelete = {
                            customTags = customTags - tag
                        }
                    )
                }

                if (showTagInput) {
                    OutlinedTextField(
                        value = newTagText,
                        onValueChange = { newTagText = it },
                        placeholder = { Text("Enter tag") },
                        singleLine = true,
                        modifier = Modifier
                            .widthIn(min = 100.dp, max = 250.dp)
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                val tag = newTagText.trim()
                                if (tag.isNotBlank() && tag !in customTags) {
                                    customTags = customTags + tag
                                }
                                newTagText = ""
                                showTagInput = false
                                focusManager.clearFocus()
                            }
                        )
                    )

                    LaunchedEffect(showTagInput) {
                        if (showTagInput) {
                            delay(100)
                            focusRequester.requestFocus()
                        }
                    }
                } else {
                    TagChip(text = "+", onClick = { showTagInput = true })
                }
            }

        }
        }
    }


@Composable
fun TagChip(
    text: String,
    onClick: () -> Unit = {},
    onDelete: (() -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFE0E0E0),
        tonalElevation = 2.dp,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .wrapContentWidth()
        ) {
            Text(
                text = text,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .defaultMinSize(minWidth = 32.dp)
                    .widthIn(max = 280.dp), // Let it wrap if it exceeds screen width
                softWrap = true
            )

            if (onDelete != null) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete tag",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { onDelete() }
                )
            }
        }
    }
}







@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddTagsToRecipeScreenPreview() {
    val navController = rememberNavController()
    AddTagsToRecipeScreen(navController = navController)
}

