package com.usu.firebasetodosapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.usu.firebasetodosapplication.ui.components.TodoListItem
import com.usu.firebasetodosapplication.ui.viewmodels.TodosViewModel
import com.usu.firebasetodosapplication.util.Analytics
import kotlinx.coroutines.launch

@Composable
fun TodosScreen(navHostController: NavHostController) {
    val viewModel: TodosViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        Analytics.logScreenVisit("Todos")
    }
    val sortedList by remember(state.showHigherPriorityItemsFirst) {
        derivedStateOf {
            if (state.showHigherPriorityItemsFirst) {
                state.todos.sortedBy { it.priority }.reversed()
            } else {
                state.todos
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.getTodos()
    }
    Column() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = state.showHigherPriorityItemsFirst, onCheckedChange = { state.showHigherPriorityItemsFirst = it })
            Text(text = "Show high priority items first")
        }
        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)) {
            items(sortedList, key = {it.id!!}) { todo ->
                TodoListItem(
                    todo = todo,
                    toggle = {
                        scope.launch {
                            viewModel.toggleTodoCompletion(todo)
                        }
                    },
                    onEditPressed = {
                        navHostController.navigate("edittodo?id=${todo.id}")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}