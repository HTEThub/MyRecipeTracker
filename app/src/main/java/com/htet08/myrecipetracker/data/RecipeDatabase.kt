import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
        RecipeTagCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration() // For development
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
