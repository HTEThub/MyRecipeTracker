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
import com.htet08.myrecipetracker.ui.components.PillShapedSearchBar
import com.htet08.myrecipetracker.ui.components.RecipeBottomAppBar
import com.htet08.myrecipetracker.ui.components.RecipeCard
import com.htet08.myrecipetracker.ui.components.RecipeTopAppBar
import com.htet08.myrecipetracker.ui.components.FilterPopup
import com.htet08.myrecipetracker.viewmodel.RecipeFormViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedRecipesScreen(
    navController: NavHostController,
    viewModel: RecipeFormViewModel
) {
    // Collect saved recipes from the viewModel.
    val recipes by viewModel.savedRecipesFlow.collectAsState(initial = emptyList())
    // Snackbar host for messages.
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    // UI-only search query state.
    var searchQuery by remember { mutableStateOf("") }
    // Controls whether the filter popup is visible.
    var showFilterPopup by remember { mutableStateOf(false) }
    // Local filter state: these hold the selections applied to filtering.
    var appliedCustomFilters by remember { mutableStateOf(emptyList<String>()) }
    var appliedDietaryFilters by remember { mutableStateOf(emptyList<String>()) }

    // Local states for editing filter selections (used only in the popup).
    // They are initialized when the popup is shown.
    var localCustomTags by remember { mutableStateOf<List<com.htet08.myrecipetracker.model.CustomTag>>(emptyList()) }
    var localDietaryTags by remember { mutableStateOf<List<com.htet08.myrecipetracker.model.CustomTag>>(emptyList()) }

    // When the filter popup is opened, initialize local states from viewModel.
    if (showFilterPopup) {
        // Initialize local copies only once.
        LaunchedEffect(Unit) {
            localCustomTags = viewModel.customTags.value.toList()
            localDietaryTags = viewModel.dietaryTags.value.toList()
        }
    }

    // Combine applied filters from custom and dietary selections.
    val appliedFilters = appliedCustomFilters + appliedDietaryFilters

    // Filter recipes based on appliedFilters.
    // Assume each recipeWithDetails has a list of tags (from the relation) that are TagEntity with a 'text' field.
    val filteredRecipes = if (appliedFilters.isEmpty()) {
        recipes
    } else {
        recipes.filter { recipeWithDetails ->
            recipeWithDetails.tags.any { tag -> tag.text in appliedFilters }
        }
    }

    Scaffold(
        topBar = {
            RecipeTopAppBar(
                title = "Saved Recipes",
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
                // Pill-shaped search bar with filter icon.
                PillShapedSearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onFilterClick = { showFilterPopup = true },
                    onSearchClick = { /* Optionally implement search button click */ }
                )
                // Clear Saved Recipes Button.
                Button(
                    onClick = {
                        viewModel.clearSavedRecipes()
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Saved recipes cleared")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Clear Saved Recipes", fontSize = 18.sp)
                }
                // Scrollable list of filtered recipes.
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(filteredRecipes) { recipeWithDetails ->
                        RecipeCard(
                            recipe = recipeWithDetails.recipe,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
            // Filter Popup: display only if showFilterPopup is true.
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
                        // When applying filters, update the appliedFilters state.
                        appliedCustomFilters = localCustomTags.filter { it.selected }.map { it.text }
                        appliedDietaryFilters = localDietaryTags.filter { it.selected }.map { it.text }
                        showFilterPopup = false
                    }
                )
            }
        }
    }
}
