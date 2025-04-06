package com.htet08.myrecipetracker.ui.screens.CreateRecipeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.htet08.myrecipetracker.data.RecipeDatabase
import com.htet08.myrecipetracker.model.CustomTag
import com.htet08.myrecipetracker.navigation.Routes
import com.htet08.myrecipetracker.ui.components.RecipeBottomAppBar
import com.htet08.myrecipetracker.ui.components.RecipeTopAppBar
import com.htet08.myrecipetracker.viewmodel.RecipeFormViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddTagsToRecipeScreen(
    navController: NavHostController,
    viewModel: RecipeFormViewModel
) {
    val customTags by viewModel.customTags
    val showTagInput by viewModel.showTagInput
    val newTagText by viewModel.newTagText
    val dietaryTags by viewModel.dietaryTags

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val db = RecipeDatabase.getDatabase(context) // Get the database
    val recipeDao = db.recipeDao() // Get the RecipeDao
    val viewModel: RecipeFormViewModel = viewModel()


    fun handleBackOrSave() {
        val tag = newTagText.trim()
        if (tag.isNotBlank()) {
            if (customTags.none { it.text == tag }) {
                viewModel.customTags.value = customTags + CustomTag(text = tag)
            } else {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Tag \"$tag\" already exists")
                }
            }
        }
        viewModel.newTagText.value = ""
        viewModel.showTagInput.value = false
        focusManager.clearFocus()
    }

    fun saveRecipe() {
        if (viewModel.title.value.isBlank()) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Please enter a title before saving your recipe.")
            }
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Recipe saved successfully!")
            }
            viewModel.saveRecipe()
            navController.popBackStack(Routes.HOME, inclusive = false)
        }
    }

    Scaffold(
        topBar = {
            RecipeTopAppBar(
                title = "Select Tags",
                leftContent = {
                    TextButton(onClick = {
                        handleBackOrSave()
                        navController.popBackStack()
                    }) {
                        Text(
                            text = "Back",
                            color = Color.White,
                            fontSize = 25.sp
                        )
                    }
                },
                rightContent = {
                    TextButton(onClick = {
                        handleBackOrSave()
                        saveRecipe()
                    }) {
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
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 80.dp),
                snackbar = { data ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color(0xFF323232),
                            tonalElevation = 6.dp
                        ) {
                            Text(
                                text = data.visuals.message,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        TagInputLayout(
            innerPadding = innerPadding,
            focusRequester = focusRequester,
            focusManager = focusManager,
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagInputLayout(
    innerPadding: PaddingValues,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    viewModel: RecipeFormViewModel
) {
    val customTags by viewModel.customTags
    val showTagInput by viewModel.showTagInput
    val newTagText by viewModel.newTagText
    val dietaryTags by viewModel.dietaryTags

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFFF3E9D1))
            .padding(20.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    val tag = newTagText.trim()
                    if (tag.isNotBlank()) {
                        if (customTags.none { it.text == tag }) {
                            viewModel.customTags.value = customTags + CustomTag(text = tag)
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Tag \"$tag\" already exists")
                            }
                        }
                    }
                    viewModel.newTagText.value = ""
                    viewModel.showTagInput.value = false
                    focusManager.clearFocus()
                })
            }
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
            customTags.forEachIndexed { index, tag ->
                TagChip(
                    text = tag.text,
                    isSelected = tag.selected,
                    onClick = {
                        viewModel.customTags.value = customTags.toMutableList().apply {
                            set(index, get(index).copy(selected = !tag.selected))
                        }
                    },
                    onDelete = {
                        viewModel.customTags.value = customTags.toMutableList().apply {
                            removeAt(index)
                        }
                    }
                )
            }

            if (showTagInput) {
                OutlinedTextField(
                    value = newTagText,
                    onValueChange = { viewModel.newTagText.value = it },
                    placeholder = { Text("Enter tag") },
                    singleLine = true,
                    modifier = Modifier
                        .widthIn(min = 100.dp, max = 250.dp)
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            val tag = newTagText.trim()
                            if (tag.isNotBlank()) {
                                if (customTags.none { it.text == tag }) {
                                    viewModel.customTags.value = customTags + CustomTag(text = tag)
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Tag \"$tag\" already exists")
                                    }
                                }
                            }
                            viewModel.newTagText.value = ""
                            viewModel.showTagInput.value = false
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
                TagChip(
                    text = "+",
                    onClick = { viewModel.showTagInput.value = true }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Dietary Preferences",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            dietaryTags.forEachIndexed { index, tag ->
                TagChip(
                    text = tag.text,
                    isSelected = tag.selected,
                    onClick = {
                        viewModel.dietaryTags.value = dietaryTags.toMutableList().apply {
                            set(index, get(index).copy(selected = !tag.selected))
                        }
                    }
                )
            }
        }
    }
}



@Composable
fun TagChip(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    onDelete: (() -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (isSelected) Color.White else Color(0xFFE0E0E0),
        border = if (isSelected) BorderStroke(1.dp, Color.Gray) else null,
        tonalElevation = 2.dp,
        onClick = onClick // toggles selection
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
                    .padding(end = if (onDelete != null) 8.dp else 0.dp)
                    .defaultMinSize(minWidth = 32.dp)
                    .widthIn(max = 280.dp),
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


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AddTagsToRecipeScreenPreview() {
//    val navController = rememberNavController()
//    val dummyViewModel = remember { RecipeFormViewModel() }
//    AddTagsToRecipeScreen(
//        navController = navController,
//        viewModel = dummyViewModel
//    )
//}
