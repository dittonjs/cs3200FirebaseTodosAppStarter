package com.usu.firebasetodosapplication.util

// simple function to turn a priority into some text
fun priorityText(priority: Int) = when (priority) {
    0 -> "Low"
    1 -> "Medium"
    2 -> "High"
    else -> ""
}