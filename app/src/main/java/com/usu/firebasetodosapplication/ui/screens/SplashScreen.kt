package com.usu.firebasetodosapplication.ui.screens

import android.window.SplashScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.usu.firebasetodosapplication.ui.navigation.Routes
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {

    LaunchedEffect(true) {
        val loginStatusCheck = async {
            // TODO: check to see if user is logged in
        }
        // wait for 3 seconds or until the login check is
        // done before navigating
        delay(1000)
        loginStatusCheck.await()
        // TODO: if logged in the skip the launch
        //       flow and go straight to the application
        navHostController.navigate(Routes.todosNavigation.route) {
            // makes it so that we can't get back to the
            // splash screen by pushing the back button
            popUpTo(navHostController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Firebase Todos",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
        Text(
            text = "USU-CS3200",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
        )
    }
}