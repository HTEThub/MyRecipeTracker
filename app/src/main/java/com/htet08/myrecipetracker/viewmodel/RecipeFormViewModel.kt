package com.htet08.myrecipetracker.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htet08.myrecipetracker.data.entities.InstructionStepEntity
import com.htet08.myrecipetracker.data.entities.Recipe
import com.htet08.myrecipetracker.data.entities.TagEntity
import com.htet08.myrecipetracker.repository.RecipeRepository
import com.htet08.myrecipetracker.model.CustomTag
import com.htet08.myrecipetracker.model.InstructionStepData
import kotlinx.coroutines.launch

class RecipeFormViewModel(private val repository: RecipeRepository) : ViewModel() {

    val savedRecipesFlow = repository.getAllRecipes()


    // Existing UI state fields
    val title = mutableStateOf("")
    val ingredients = mutableStateOf("")
    val imageUri = mutableStateOf<Uri?>(null)

    // Step 1 inputs
    val step1ImageUri = mutableStateOf<Uri?>(null)
    val step1Text = mutableStateOf("")

    // Dynamic instruction steps
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

    fun saveRecipeToDatabase() {
        viewModelScope.launch {
            // Build the Recipe from UI input
            val recipe = Recipe(
                title = title.value,
                ingredients = ingredients.value,
                imageUri = imageUri.value?.toString()
            )

            // Build the list of InstructionStepEntities (including step 1)
            val stepsList = mutableListOf<InstructionStepEntity>()
            if (step1Text.value.isNotBlank()) {
                stepsList.add(
                    InstructionStepEntity(
                        recipeId = 0, // placeholder; repository will set the correct value
                        stepNumber = 1,
                        instruction = step1Text.value,
                        imageUri = step1ImageUri.value?.toString()
                    )
                )
            }
            dynamicSteps.value.forEach { stepData ->
                if (stepData.text.isNotBlank()) {
                    stepsList.add(
                        InstructionStepEntity(
                            recipeId = 0, // will be updated in repository
                            stepNumber = 0, // placeholder; will be re-assigned
                            instruction = stepData.text,
                            imageUri = stepData.imageUri?.toString()
                        )
                    )
                }
            }

            // Combine both custom and dietary tags and filter the selected ones
            val allTags = (customTags.value + dietaryTags.value).filter { it.selected }
            val tagEntities = allTags.map { tag ->
                TagEntity(
                    text = tag.text,
                    isCustom = tag.isCustom,
                    selected = tag.selected
                )
            }

            // Save the recipe along with its steps and tags using the repository
            repository.saveRecipe(recipe, stepsList, tagEntities)

            // Clear the form after saving
            clearForm()
        }
    }

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

    fun clearSavedRecipes() {
        viewModelScope.launch {
            repository.clearSavedRecipes()
        }
    }
}
