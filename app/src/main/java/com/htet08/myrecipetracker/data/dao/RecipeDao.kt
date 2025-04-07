package com.htet08.myrecipetracker.data.dao

import androidx.room.*
import com.htet08.myrecipetracker.data.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstructionSteps(steps: List<InstructionStepEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: TagEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(tags: List<TagEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeTagCrossRef(crossRef: RecipeTagCrossRef)

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeWithStepsAndTags(recipeId: Long): RecipeWithStepsAndTags?

    @Transaction
    @Query("SELECT * FROM recipes")
    fun getAllRecipesWithStepsAndTags(): Flow<List<RecipeWithStepsAndTags>>

    @Query("DELETE FROM recipe_tag_cross_ref")
    suspend fun clearRecipeTagCrossRefs()

    @Query("DELETE FROM instruction_steps")
    suspend fun clearInstructionSteps()

    @Query("DELETE FROM recipes")
    suspend fun clearRecipes()
}
