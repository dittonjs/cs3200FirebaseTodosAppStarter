package com.usu.firebasetodosapplication.ui.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.usu.firebasetodosapplication.ui.screens.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.launchNavigation.route || it.route == Routes.splashScreen.route } == true) {
                TopAppBar() {
                    if (currentDestination?.route == Routes.todos.route) {
                        IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu button")
                        }
                    } else {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                    Text(text = "Todos")
                }
            }
        },
        drawerContent = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.launchNavigation.route || it.route == Routes.splashScreen.route } == true) {
                DropdownMenuItem(onClick = {
                    // TODO Log the user out
                    scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Closed)
                    }
                    navController.navigate(Routes.launchNavigation.route) {
                        popUpTo(0) // clear back stack and basically start the app from scratch
                    }
                }) {
                    Icon(Icons.Default.ExitToApp, "Logout")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Logout")
                }
            }
        },
        floatingActionButton = {
            if (currentDestination?.route == Routes.todos.route) {
                FloatingActionButton(onClick = {navController.navigate(Routes.editTodo.route)}) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add todo")
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.splashScreen.route,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            navigation(route = Routes.launchNavigation.route, startDestination = Routes.launch.route) {
                composable(route = Routes.launch.route) { LaunchScreen(navController) }
                composable(route = Routes.signIn.route) { SignInScreen(navController) }
                composable(route = Routes.signUp.route) { SignUpScreen(navController) }
            }
            navigation(route = Routes.todosNavigation.route, startDestination = Routes.todos.route) {
                composable(route = Routes.editTodo.route) { TodosModificationScreen(navController) }
                composable(route = Routes.todos.route) { TodosScreen(navController) }
            }
            composable(route = Routes.splashScreen.route) { SplashScreen(navController) }
        }
    }
}