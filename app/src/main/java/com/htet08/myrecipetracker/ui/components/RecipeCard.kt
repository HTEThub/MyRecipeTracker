package com.htet08.myrecipetracker.ui.components

import android.media.SoundPool
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.htet08.myrecipetracker.R
import com.htet08.myrecipetracker.data.entities.Recipe

@Composable
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onSaveToHistory: () -> Unit = {} // Callback when the button is pressed.
) {
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Create a SoundPool instance.
    val soundPool = remember {
        SoundPool.Builder().setMaxStreams(1).build()
    }
    // Load the sound effect from the raw folder.
    val soundId = remember {
        soundPool.load(context, R.raw.pan_close, 1)
    }

    Card(
        modifier = modifier
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            if (!recipe.imageUri.isNullOrEmpty()) {
                AsyncImage(
                    model = recipe.imageUri,
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.LightGray)
                )
            }
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(8.dp)) {
                    // Split the ingredients string on commas and trim whitespace.
                    val ingredientsList = recipe.ingredients.split(",").map { it.trim() }
                    // Display ingredients.
                    ingredientsList.forEach { ingredient ->
                        Text(
                            text = ingredient,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Display first step instruction label.
                    Text(
                        text = "Instructions:",
                        style = MaterialTheme.typography.titleSmall
                    )
                    // Uncomment and use when needed.
                    // Text(
                    //     text = recipe.step1Inst,
                    //     style = MaterialTheme.typography.bodyMedium
                    // )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Button to save this recipe card to Cooking History.
                    Button(
                        onClick = {
                            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                            onSaveToHistory()
                                  },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Save to Cooking History")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    // Create a sample Recipe for preview purposes.
    val sampleRecipe = Recipe(
        title = "Sample Recipe",
        ingredients = "1 cup of flour, 2 eggs, 1/2 cup of sugar",
        imageUri = null,
//        step1Inst = "Mix all the ingredients in a bowl until smooth."
    )
    RecipeCard(
        recipe = sampleRecipe,
        modifier = Modifier.padding(16.dp),
        onSaveToHistory = { /* Here you can add logic to save the recipe to cooking history */ }
    )
}
