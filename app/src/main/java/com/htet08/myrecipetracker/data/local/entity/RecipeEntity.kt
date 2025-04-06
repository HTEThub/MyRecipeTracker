package com.htet08.myrecipetracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val ingredients: String,
    val instructions: List<String>,
    val instructionImages: List<String>,
    val recipeImage: String?,
    val tags: List<String>,
    val timestamp: Long = System.currentTimeMillis()
)
