package com.htet08.myrecipetracker.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RecipeWithStepsAndTags(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "id",
        entity = TagEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = RecipeTagCrossRef::class,
            parentColumn = "recipeId",  // Column in RecipeTagCrossRef referencing RecipeEntity.id
            entityColumn = "tagId"        // Column in RecipeTagCrossRef referencing TagEntity.id
        )
    )
    val tags: List<TagEntity>
)

