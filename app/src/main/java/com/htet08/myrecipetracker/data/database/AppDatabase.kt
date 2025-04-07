package com.htet08.myrecipetracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.htet08.myrecipetracker.data.dao.RecipeDao
import com.htet08.myrecipetracker.data.entities.InstructionStepEntity
import com.htet08.myrecipetracker.data.entities.RecipeEntity
import com.htet08.myrecipetracker.data.entities.RecipeTagCrossRef
import com.htet08.myrecipetracker.data.entities.TagEntity

@Database(
    entities = [RecipeEntity::class, InstructionStepEntity::class, TagEntity::class, RecipeTagCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
