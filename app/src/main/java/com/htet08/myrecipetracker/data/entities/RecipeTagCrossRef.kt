package com.htet08.myrecipetracker.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["recipeId", "tagId"], tableName = "recipe_tag_cross_ref")
data class RecipeTagCrossRef(
    val recipeId: Long,
    val tagId: Long
)