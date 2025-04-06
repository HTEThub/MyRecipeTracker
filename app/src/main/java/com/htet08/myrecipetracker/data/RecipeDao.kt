package com.htet08.myrecipetracker.data

import androidx.room.*
import com.htet08.myrecipetracker.data.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    // Insert a new recipe into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    // Get all recipes sorted by the timestamp
    @Query("SELECT * FROM recipes ORDER BY timestamp DESC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    // Delete a recipe from the database
    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    // Get a recipe by its ID
    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity?
}

