package com.htet08.myrecipetracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.htet08.myrecipetracker.R
import com.htet08.myrecipetracker.navigation.Routes

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3E9D1))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            RecipeMainMenu(navController = navController, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun RecipeMainMenu(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
                Button(
                    onClick = { navController.navigate(Routes.CREATE_RECIPE) },
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
                            modifier = Modifier.size(90.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Button(
                    onClick = { navController.navigate(Routes.SAVED_RECIPES) },
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
                onClick = { navController.navigate(Routes.COOKING_HISTORY) },
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
