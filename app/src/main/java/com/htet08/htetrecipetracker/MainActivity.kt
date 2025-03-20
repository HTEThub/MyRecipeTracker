package com.htet08.htetrecipetracker

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.htet08.htetrecipetracker.ui.theme.HtetRecipeTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HtetRecipeTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RecipeMainLayout(
//                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeMainLayout(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        // Top bar
        RecipeTopAppBar()


        // Main content
        RecipeMainMenu(modifier = Modifier.weight(1f))


        // Bottom bar
        RecipeBottomAppBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)  // Adjust height as needed
            .background(Color(0xFFFFA500)), // Use your chosen color here
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.app_name))
    }
}

@Composable
fun RecipeMainMenu(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(50.dp),
            ) {
                Button(
                    onClick = { /* Button 1 action */ },
                    modifier = Modifier.width(150.dp)
                ) {
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Create Recipe")
                        Text("(CreateRecipeIcon)")
                    }
                }
                Button(
                    onClick = { /* Button 2 action */ },
                    modifier = Modifier.width(150.dp)
                ) {
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Recipe List")
                        Text("(RecipeListIcon)")
                    }
                }
            }

            Spacer(modifier = Modifier.size(30.dp))


            Button(
                onClick = { /* Button 3 action */ },
                modifier = Modifier.width(150.dp)
            ) {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Recipe List")
                    Text("(RecipeListIcon)")
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeBottomAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)  // Adjust height as needed
            .background(Color(0xFFFFA500)), // Use your chosen color here
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

    }

}



@Preview(showBackground = true)
@Composable
fun MainLayoutPreview() {
    HtetRecipeTrackerTheme {
        RecipeMainLayout()
    }
}