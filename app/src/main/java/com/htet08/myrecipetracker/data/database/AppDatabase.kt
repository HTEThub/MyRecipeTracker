package com.htet08.myrecipetracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.htet08.myrecipetracker.data.dao.RecipeDao
import com.htet08.myrecipetracker.data.entities.InstructionStepEntity
import com.htet08.myrecipetracker.data.entities.Recipe
import com.htet08.myrecipetracker.data.entities.RecipeTagCrossRef
import com.htet08.myrecipetracker.data.entities.TagEntity

@Database(
    entities = [
        Recipe::class,
        InstructionStepEntity::class,
        TagEntity::class,
        RecipeTagCrossRef::class  // Ensure this is included
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}

