package com.htet08.myrecipetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.htet08.myrecipetracker.repository.RepositoryProvider

class RecipeFormViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeFormViewModel::class.java)) {
            val repository = RepositoryProvider.provideRecipeRepository(context)
            @Suppress("UNCHECKED_CAST")
            return RecipeFormViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
