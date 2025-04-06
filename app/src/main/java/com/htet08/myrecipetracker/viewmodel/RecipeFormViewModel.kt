package com.htet08.myrecipetracker.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.htet08.myrecipetracker.model.CustomTag
import com.htet08.myrecipetracker.model.InstructionStepData

class RecipeFormViewModel : ViewModel() {

    val title = mutableStateOf("")
    val ingredients = mutableStateOf("")
    val imageUri = mutableStateOf<Uri?>(null)

    // Step 1 image
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

