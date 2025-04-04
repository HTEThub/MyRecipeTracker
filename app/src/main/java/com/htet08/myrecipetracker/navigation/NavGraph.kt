package com.htet08.myrecipetracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import com.htet08.myrecipetracker.ui.screens.CreateRecipeScreen.CreatingRecipeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.htet08.myrecipetracker.ui.components.RecipeBottomAppBar
import com.htet08.myrecipetracker.ui.components.RecipeTopAppBar
import com.htet08.myrecipetracker.ui.screens.HomeScreen
import androidx.compose.ui.Modifier
import com.htet08.myrecipetracker.ui.screens.CreateRecipeScreen.AddTagsToRecipeScreen

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.MAIN) {

        composable(Routes.MAIN) {
            Scaffold(
                topBar = {
                    RecipeTopAppBar(title = "My Recipe Tracker")
                },
                bottomBar = {
                    RecipeBottomAppBar()
                }
            ) { innerPadding ->
                // Pass the innerPadding to your screen if needed
                HomeScreen(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }

        }
        composable(Routes.CREATE_RECIPE) {
            CreatingRecipeScreen(navController = navController)
        }

        composable(Routes.ADD_TAGS) {
            AddTagsToRecipeScreen(navController)
        }
    }
}
