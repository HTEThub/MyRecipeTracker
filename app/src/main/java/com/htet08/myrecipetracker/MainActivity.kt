package com.htet08.myrecipetracker

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.htet08.myrecipetracker.ui.theme.MyRecipeTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyRecipeTrackerTheme {
                Scaffold(
                    topBar = { RecipeTopAppBar() },
                    bottomBar = { RecipeBottomAppBar() }
                ) { innerPadding ->
                    RecipeMainLayout(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RecipeMainLayout(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF3E9D1)) // Light warm background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            RecipeMainMenu(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun RecipeTopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)  // Adjust height as needed
            .background(Color(0xFFF3B245)), // Your chosen color
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = Color.White
        )
    }
}

@Composable
fun RecipeMainMenu(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
                Button(
                    onClick = { /* Button 1 action */ },
                    modifier = Modifier
                        .width(150.dp)
                        .height(175.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF3AA35),
                        contentColor = Color.White
                    )
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Create Recipe",
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 30.sp
                        )
                        Image(
                            painter = painterResource(id = R.drawable.create_recipe_icon),
                            contentDescription = "CreateRecipeIcon",
                            modifier = Modifier.size(90.dp), // Corrected size modifier
                            contentScale = ContentScale.Fit // Ensures image fills its size
                        )
                    }
                }

                Button(
                    onClick = { /* Button 2 action */ },
                    modifier = Modifier
                        .width(150.dp)
                        .height(175.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF3AA35),
                        contentColor = Color.White
                    )
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Recipe List",
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 30.sp
                        )
                        Image(
                            painter = painterResource(id = R.drawable.recipe_book_icon),
                            contentDescription = "RecipeBookIcon",
                            modifier = Modifier.size(90.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(30.dp))

            Button(
                onClick = { /* Button 3 action */ },
                modifier = Modifier
                    .width(150.dp)
                    .height(175.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF3AA35),
                    contentColor = Color.White
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Cooking History",
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 30.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.cooking_history_icon),
                        contentDescription = "CookingHistoryIcon",
                        modifier = Modifier.size(90.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}



@Composable
fun RecipeBottomAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFFF3B245)), // Your chosen color
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bottom Bar",
            color = Color.White
        )
    }
}



@Preview(showBackground = true)
@Composable
fun RecipeMainLayoutPreview() {
    MyRecipeTrackerTheme {
        Scaffold(
            topBar = { RecipeTopAppBar() },
            bottomBar = { RecipeBottomAppBar() }
        ) { innerPadding ->
            RecipeMainLayout(modifier = Modifier.padding(innerPadding))
        }
    }
}
