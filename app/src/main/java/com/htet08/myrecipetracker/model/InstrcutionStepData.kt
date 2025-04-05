package com.htet08.myrecipetracker.model

import android.net.Uri

data class InstructionStepData(
    val id: Int,
    var text: String = "",
    var imageUri: Uri? = null
)