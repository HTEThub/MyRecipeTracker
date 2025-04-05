package com.htet08.myrecipetracker.ui.screens.CreateRecipeScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.htet08.myrecipetracker.ui.components.RecipeTopAppBar
import com.htet08.myrecipetracker.ui.components.RecipeBottomAppBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.htet08.myrecipetracker.R
import com.htet08.myrecipetracker.model.InstructionStepData


@Composable
fun CreateRecipeScreen(navController: NavHostController) {
    val focusManager = LocalFocusManager.current
    var dynamicSteps by remember { mutableStateOf(listOf<InstructionStepData>()) }

    Scaffold(
        topBar = { RecipeTopAppBar(title = "Create Recipe") },
        bottomBar = { RecipeBottomAppBar() }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        val context = LocalContext.current

        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var step1ImageUri by remember { mutableStateOf<Uri?>(null) }
        var title by remember { mutableStateOf("") }
        var ingredients by remember { mutableStateOf("") }

        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }

        val step1ImagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            step1ImageUri = uri
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF3E9D1))
                .verticalScroll(scrollState)
                .padding(20.dp)
                .pointerInput(Unit) {
                    detectTapGestures { focusManager.clearFocus() }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            RecipeImagePicker(
                imageUri = imageUri,
                onImageClick = { imagePickerLauncher.launch("image/*") },
                onRemoveImage = { imageUri = null }
            )

            Spacer(modifier = Modifier.height(24.dp))

            RecipeTitleInput(
                title = title,
                onTitleChange = { title = it },
                focusManager = focusManager
            )

            Spacer(modifier = Modifier.height(24.dp))

            IngredientsInput(
                ingredients = ingredients,
                onValueChange = { ingredients = it },
                focusManager = focusManager
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Instructions title
            Text(
                text = "Instructions",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            var step1 by remember { mutableStateOf("") }


            // Step 1 (always shown)
            InstructionStep(
                stepNumber = 1,
                instruction = step1,
                onInstructionChange = { step1 = it },
                onAddImageClick = { step1ImagePickerLauncher.launch("image/*") },
                stepImageUri = step1ImageUri,
                onRemoveStepImage = { step1ImageUri = null }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val newId = (dynamicSteps.maxOfOrNull { it.id } ?: 1) + 1
                    dynamicSteps = dynamicSteps.toMutableList().apply {
                        add(0, InstructionStepData(id = newId))
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Add Step Below")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dynamic additional steps
            dynamicSteps.forEachIndexed { index, step ->
                // Create launcher for this specific step
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    if (uri != null) {
                        dynamicSteps = dynamicSteps.toMutableList().apply {
                            set(index, get(index).copy(imageUri = uri))
                        }
                    }
                }

                InstructionStep(
                    stepNumber = index + 2, // Step 2, 3, 4...
                    instruction = step.text,
                    onInstructionChange = { newText ->
                        dynamicSteps = dynamicSteps.toMutableList().apply {
                            set(index, get(index).copy(text = newText))
                        }
                    },
                    onAddImageClick = { launcher.launch("image/*") },
                    stepImageUri = step.imageUri,
                    onRemoveStepImage = {
                        dynamicSteps = dynamicSteps.toMutableList().apply {
                            set(index, get(index).copy(imageUri = null))
                        }
                    },
                    onDelete = {
                        dynamicSteps = dynamicSteps.toMutableList().apply {
                            removeAt(index)
                        }
                    }
                )
                // 🔽 Add Step Button (in-between)
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val newId = (dynamicSteps.maxOfOrNull { it.id } ?: 1) + 1
                        dynamicSteps = dynamicSteps.toMutableList().apply {
                            add(index + 1, InstructionStepData(id = newId))
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp)
                ) {
                    Text("Add Step Below")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun RecipeImagePicker(imageUri: Uri?, onImageClick: () -> Unit, onRemoveImage: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .clickable { onImageClick() }
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Recipe Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = onRemoveImage,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White.copy(alpha = 0.6f), shape = CircleShape)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove Image",
                    tint = Color.Black
                )
            }
        } else {
            Text(
                text = "Tap to select image",
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun RecipeTitleInput(title: String, onTitleChange: (String) -> Unit, focusManager: FocusManager) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        label = { Text("Title of Recipe") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Composable
fun IngredientsInput(ingredients: String, onValueChange: (String) -> Unit, focusManager: FocusManager) {
    Text(
        text = "Ingredients",
        fontSize = 18.sp,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )

    OutlinedTextField(
        value = ingredients,
        onValueChange = onValueChange,
        placeholder = { Text("e.g., 2 eggs, 1 cup flour, ...") },
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        maxLines = Int.MAX_VALUE,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        textStyle = LocalTextStyle.current.copy(lineHeight = 20.sp),
        singleLine = false
    )
}

@Composable
fun InstructionStep(
    stepNumber: Int,
    instruction: String,
    onInstructionChange: (String) -> Unit,
    onAddImageClick: () -> Unit,
    stepImageUri: Uri?,
    onDelete: (() -> Unit)? = null, // Only passed for dynamic steps
    onRemoveStepImage: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Step $stepNumber",
            fontSize = 18.sp,
            color = Color.Black
        )
        if (onDelete != null) {
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Step"
                )
            }
        }
    }

    OutlinedTextField(
        value = instruction,
        onValueChange = onInstructionChange,
        placeholder = { Text("Describe this step...") },
        modifier = Modifier
            .fillMaxWidth(), // No fixed height anymore
        textStyle = LocalTextStyle.current.copy(lineHeight = 20.sp),
        maxLines = Int.MAX_VALUE,
        singleLine = false
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onAddImageClick,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_image_icon),
                contentDescription = "Add Image"
            )
        }

        Text(
            text = "Add Image",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }

    stepImageUri?.let {
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Step Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { onRemoveStepImage?.invoke() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White.copy(alpha = 0.6f), shape = CircleShape)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove Step Image",
                    tint = Color.Black
                )
            }
        }
    }

}






@Preview(showBackground = true, heightDp = 1000, showSystemUi = true)
@Composable
fun CreateRecipeScreenPreview() {
    val navController = rememberNavController()
    CreateRecipeScreen(navController = navController)
}

