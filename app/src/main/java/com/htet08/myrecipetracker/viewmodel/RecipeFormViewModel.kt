package com.htet08.myrecipetracker.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htet08.myrecipetracker.data.RecipeDao
import com.htet08.myrecipetracker.data.RecipeEntity
import com.htet08.myrecipetracker.data.RecipeDatabase
import com.htet08.myrecipetracker.model.CustomTag
import com.htet08.myrecipetracker.model.InstructionStepData
import kotlinx.coroutines.launch

class RecipeFormViewModel(private val recipeDao: RecipeDao) : ViewModel() {

    // Recipe details
    val title = mutableStateOf("")
    val ingredients = mutableStateOf("")
    val imageUri = mutableStateOf<Uri?>(null)

    // Step 1 details
    val step1ImageUri = mutableStateOf<Uri?>(null)
    val step1Text = mutableStateOf("")

    // Dynamic steps
    val dynamicSteps = mutableStateOf<List<InstructionStepData>>(emptyList())

    // Tags
    val customTags = mutableStateOf<List<CustomTag>>(emptyList())
    val dietaryTags = mutableStateOf<List<CustomTag>>(
        listOf(
            CustomTag("Vegetarian"),
            CustomTag("Vegan"),
            CustomTag("Gluten-Free"),
            CustomTag("Dairy-Free"),
            CustomTag("Nut-Free"),
            CustomTag("Low-Carb"),
            CustomTag("High-Protein"),
            CustomTag("Keto"),
            CustomTag("Paleo"),
            CustomTag("Halal")
        )
    )
    val showTagInput = mutableStateOf(false)
    val newTagText = mutableStateOf("")

    fun saveRecipe() {
        // Combine tags into a comma-separated string for storage in the database
        val tags = (customTags.value + dietaryTags.value.filter { it.selected }.map { it.text }).joinToString(", ")

        // Combine step images into a comma-separated string (for simplicity in this example)
        val stepImages = dynamicSteps.value.joinToString(", ") { it.imageUri?.toString() ?: "" }

        // Create the RecipeEntity object
        val recipe = RecipeEntity(
            title = title.value,
            ingredients = ingredients.value,
            instructions = step1Text.value, // This can be updated later to hold all the step instructions
            tags = tags,
            imageUri = imageUri.value?.toString() ?: "", // Handle nullability
            stepImages = stepImages,
            timestamp = System.currentTimeMillis() // Timestamp for when the recipe is created
        )

        // Save the recipe to the database
        viewModelScope.launch {
            recipeDao.insertRecipe(recipe) // Insert the recipe into the database
        }
    }


    // Clear the form
    fun clearForm() {
        title.value = ""
        ingredients.value = ""
        imageUri.value = null
        step1ImageUri.value = null
        step1Text.value = ""
        dynamicSteps.value = emptyList()
        customTags.value = emptyList()
        dietaryTags.value = dietaryTags.value.map { it.copy(selected = false) }
        showTagInput.value = false
        newTagText.value = ""
    }
}

