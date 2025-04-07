package com.htet08.myrecipetracker.data.entities

import androidx.room.Entity

@Entity(tableName = "recipe_tag_cross_ref", primaryKeys = ["recipeId", "tagId"])
data class RecipeTagCrossRef(
    val recipeId: Long,
    val tagId: Long
)