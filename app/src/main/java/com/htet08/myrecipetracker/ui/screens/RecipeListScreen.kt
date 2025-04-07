package com.htet08.myrecipetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.htet08.myrecipetracker.navigation.Routes
import com.htet08.myrecipetracker.ui.components.RecipeBottomAppBar
import com.htet08.myrecipetracker.ui.components.RecipeTopAppBar
import com.htet08.myrecipetracker.viewmodel.RecipeFormViewModel
import kotlinx.coroutines.launch

@Composable
fun SavedRecipesScreen(
    navController: NavHostController,
    viewModel: RecipeFormViewModel
) {
    // Collect the saved recipes as state.
    val recipes by viewModel.savedRecipesFlow.collectAsState(initial = emptyList())
    // Snackbar host for messages.
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    // UI-only search query state.
    var searchQuery by remember { mutableStateOf("") }

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
            // The Column below holds fixed elements (search bar and clear button) and a scrollable list.
            Column(modifier = Modifier.fillMaxSize()) {
                // Fixed Search Bar with Filter Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search recipes...") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { /* TODO: add filter functionality later */ },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = "Filter Settings"
                        )
                    }
                }
                // Fixed Clear Saved Recipes Button
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
                // Scrollable list of recipes.
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(recipes) { recipeWithDetails ->
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = recipeWithDetails.recipe.title,
                                fontSize = 20.sp
                            )
                            Text(
                                text = recipeWithDetails.recipe.ingredients
                            )
                            // Optionally, display steps and tags if needed.
                        }
                    }
                }
            }
        }
    }
}
