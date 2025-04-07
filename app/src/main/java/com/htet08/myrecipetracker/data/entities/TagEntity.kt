package com.htet08.myrecipetracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val isCustom: Boolean = false, // false for dietary tags, true for custom tags
    val selected: Boolean = false
)