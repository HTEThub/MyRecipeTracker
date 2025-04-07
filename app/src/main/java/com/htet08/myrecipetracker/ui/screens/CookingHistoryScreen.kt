package com.htet08.myrecipetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.htet08.myrecipetracker.navigation.Routes
import com.htet08.myrecipetracker.ui.components.CookingRecipeCard
import com.htet08.myrecipetracker.ui.components.PillShapedSearchBar
import com.htet08.myrecipetracker.ui.components.RecipeBottomAppBar
import com.htet08.myrecipetracker.ui.components.RecipeTopAppBar
import com.htet08.myrecipetracker.ui.components.FilterPopup
import com.htet08.myrecipetracker.viewmodel.RecipeFormViewModel
import kotlinx.coroutines.launch

// Dummy data class representing a cooking history record.
// Adjust fields as necessary.
data class CookingHistoryItem(
    val id: Long,
    val recipeTitle: String,
    val cookedOn: String // Could be a formatted date.
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookingHistoryScreen(
    navController: NavHostController,
    viewModel: RecipeFormViewModel
) {
    val cookingHistory by viewModel.cookingHistoryFlow.collectAsState()

    // Snackbar host for messages.
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // UI-only search query state.
    var searchQuery by remember { mutableStateOf("") }
    // Controls whether the filter popup is visible.
    var showFilterPopup by remember { mutableStateOf(false) }
    // Local filter state.
    var appliedCustomFilters by remember { mutableStateOf(emptyList<String>()) }
    var appliedDietaryFilters by remember { mutableStateOf(emptyList<String>()) }

    // Local states for editing filter selections.
    var localCustomTags by remember { mutableStateOf<List<com.htet08.myrecipetracker.model.CustomTag>>(emptyList()) }
    var localDietaryTags by remember { mutableStateOf<List<com.htet08.myrecipetracker.model.CustomTag>>(emptyList()) }

    if (showFilterPopup) {
        LaunchedEffect(Unit) {
            localCustomTags = viewModel.customTags.value.toList()
            localDietaryTags = viewModel.dietaryTags.value.toList()
        }
    }

    val appliedFilters = appliedCustomFilters + appliedDietaryFilters

    val filteredHistoryItems = cookingHistory.filter { recipe ->
        recipe.title.contains(searchQuery, ignoreCase = true) &&
                (appliedFilters.isEmpty() || appliedFilters.any { filter ->
                    recipe.ingredients.contains(filter, ignoreCase = true) || recipe.title.contains(filter, ignoreCase = true)
                })
    }

    Scaffold(
        topBar = {
            RecipeTopAppBar(
                title = "Cooking History",
                leftContent = {
                    TextButton(
                        onClick = { navController.popBackStack(Routes.HOME, inclusive = false) }
                    ) {
                        Text(text = "Back", color = Color.White, fontSize = 18.sp)
                    }
                }
            )
        },
        bottomBar = { RecipeBottomAppBar() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3E9D1))
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                PillShapedSearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onFilterClick = { showFilterPopup = true },
                    onSearchClick = { /* Optionally implement search button click */ }
                )
                // Example button to clear cooking history (or perform any other action)
                Button(
                    onClick = {
                        viewModel.clearCookingHistory()
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Cooking history cleared")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Clear Cooking History", fontSize = 18.sp)
                }
                // List displaying cooking history records.
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(filteredHistoryItems) { recipe ->
                        CookingRecipeCard(
                            recipe = recipe,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
            if (showFilterPopup) {
                FilterPopup(
                    customTags = localCustomTags,
                    dietaryTags = localDietaryTags,
                    onCustomTagToggle = { index ->
                        localCustomTags = localCustomTags.toMutableList().also { list ->
                            val current = list[index]
                            list[index] = current.copy(selected = !current.selected)
                        }
                    },
                    onDietaryTagToggle = { index ->
                        localDietaryTags = localDietaryTags.toMutableList().also { list ->
                            val current = list[index]
                            list[index] = current.copy(selected = !current.selected)
                        }
                    },
                    onDismissRequest = { showFilterPopup = false },
                    onApplyFilters = {
                        appliedCustomFilters = localCustomTags.filter { it.selected }.map { it.text }
                        appliedDietaryFilters = localDietaryTags.filter { it.selected }.map { it.text }
                        showFilterPopup = false
                    }
                )
            }
        }
    }
}


