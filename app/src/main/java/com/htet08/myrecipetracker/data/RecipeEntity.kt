package com.htet08.myrecipetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val ingredients: String,
    val instructions: String,
    val tags: String? = null,  // Comma-separated list of tags
    val imageUri: String? = null,  // URI as String for the image
    val stepImages: String? = null,  // Store multiple images (if you want multiple images)
    val timestamp: Long = System.currentTimeMillis() // Timestamp for sorting or filtering
)


