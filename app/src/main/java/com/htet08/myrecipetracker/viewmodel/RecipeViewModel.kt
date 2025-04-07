//package com.htet08.myrecipetracker.viewmodel
//
//import android.net.Uri
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.htet08.myrecipetracker.data.dao.RecipeDao
//import com.htet08.myrecipetracker.data.RecipeEntity
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//class RecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {
//
//    // State flow for all recipes
//    private val _recipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
//    val recipes: StateFlow<List<RecipeEntity>> = _recipes
//
//    init {
//        // Fetch recipes on initialization
//        fetchRecipes()
//    }
//
//    // Fetch recipes
//    private fun fetchRecipes() {
//        viewModelScope.launch {
//            recipeDao.getAllRecipes().collect { recipes ->
//                _recipes.value = recipes
//            }
//        }
//    }
//
//    // Insert a recipe into the database
//    fun insertRecipe(recipe: RecipeEntity) {
//        viewModelScope.launch {
//            recipeDao.insertRecipe(recipe)
//        }
//    }
//
//    // Delete a recipe from the database
//    fun deleteRecipe(recipe: RecipeEntity) {
//        viewModelScope.launch {
//            recipeDao.deleteRecipe(recipe)
//        }
//    }
//}



