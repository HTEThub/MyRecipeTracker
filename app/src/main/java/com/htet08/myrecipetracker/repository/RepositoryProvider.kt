package com.htet08.myrecipetracker.repository

import android.content.Context
import com.htet08.myrecipetracker.data.database.DatabaseProvider

object RepositoryProvider {
    fun provideRecipeRepository(context: Context): RecipeRepository {
        val database = DatabaseProvider.getDatabase(context)
        return RecipeRepository(database.recipeDao())
    }
}


