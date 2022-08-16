package com.usu.firebasetodosapplication.ui.navigation

data class Screen(val route: String)

object Routes {
    val launchNavigation = Screen("launchnavigation")
    val todosNavigation = Screen("todosnavigation")
    val launch = Screen("launch")
    val signIn = Screen("signin")
    val signUp = Screen("signup")
    val todos = Screen("todos")
    val editTodo = Screen("edittodo?id={id}")
    val splashScreen = Screen("splashscreen")
}