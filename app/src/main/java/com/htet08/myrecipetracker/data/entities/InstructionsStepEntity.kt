package com.htet08.myrecipetracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instruction_steps")
data class InstructionStepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    // Will be set after inserting the recipe
    val recipeId: Long,
    val stepNumber: Int,
    val instruction: String,
    val imageUri: String? = null
)
