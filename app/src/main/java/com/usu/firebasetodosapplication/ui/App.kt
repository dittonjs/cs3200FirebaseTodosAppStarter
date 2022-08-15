package com.usu.firebasetodosapplication.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.usu.firebasetodosapplication.ui.navigation.RootNavigation
import com.usu.firebasetodosapplication.ui.theme.FirebaseTodosApplicationTheme

@Composable
fun App() {
    FirebaseTodosApplicationTheme {
        RootNavigation()
    }
}