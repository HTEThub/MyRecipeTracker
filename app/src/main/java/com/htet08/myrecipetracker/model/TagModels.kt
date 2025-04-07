package com.htet08.myrecipetracker.model

data class CustomTag(
    val text: String,
    val isCustom: Boolean = true, // true for custom tags; false for dietary tags
    var selected: Boolean = false
)
