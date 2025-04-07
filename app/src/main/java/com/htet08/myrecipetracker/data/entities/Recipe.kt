package com.htet08.myrecipetracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val ingredients: String,
    val imageUri: String? = null,
//    val step1Inst: String
)


