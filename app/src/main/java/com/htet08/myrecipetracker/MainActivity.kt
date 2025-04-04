package com.htet08.myrecipetracker

import com.htet08.myrecipetracker.navigation.AppNavGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.htet08.myrecipetracker.ui.theme.MyRecipeTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyRecipeTrackerTheme {
                AppNavGraph()
            }
        }
    }
}

