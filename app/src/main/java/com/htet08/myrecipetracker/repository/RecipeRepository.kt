package com.htet08.myrecipetracker.repository

import com.htet08.myrecipetracker.data.dao.RecipeDao
import com.htet08.myrecipetracker.data.entities.InstructionStepEntity
import com.htet08.myrecipetracker.data.entities.Recipe
import com.htet08.myrecipetracker.data.entities.RecipeTagCrossRef
import com.htet08.myrecipetracker.data.entities.RecipeWithStepsAndTags
import com.htet08.myrecipetracker.data.entities.TagEntity
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {

    suspend fun saveRecipe(
        recipe: Recipe,
        steps: List<InstructionStepEntity>,
        tags: List<TagEntity>
    ): Long {
        // Insert recipe and retrieve its generated ID
        val recipeId = recipeDao.insertRecipe(recipe)

        // Set the correct recipeId and step numbers for each instruction step
        val stepsWithRecipeId = steps.mapIndexed { index, step ->
            step.copy(recipeId = recipeId, stepNumber = index + 1)
        }
        recipeDao.insertInstructionSteps(stepsWithRecipeId)

        // Insert each tag. For tags marked as selected, link them with the recipe.
        tags.forEach { tag ->
            val tagId = recipeDao.insertTag(tag)
            if (tag.selected) {
                recipeDao.insertRecipeTagCrossRef(RecipeTagCrossRef(recipeId, tagId))
            }
        }

        return recipeId
    }

    // Optional: Function to retrieve all recipes with their steps and tags.
    fun getAllRecipes(): Flow<List<RecipeWithStepsAndTags>> {
        return recipeDao.getAllRecipesWithStepsAndTags()
    }

    suspend fun clearSavedRecipes() {
        recipeDao.clearRecipeTagCrossRefs()
        recipeDao.clearInstructionSteps()
        recipeDao.clearRecipes()
    }
}
