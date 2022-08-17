package com.usu.firebasetodosapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.usu.firebasetodosapplication.ui.models.Todo
import com.usu.firebasetodosapplication.ui.repositories.TodosRepository

class TodosScreenState {
    val _todos = mutableStateListOf<Todo>()
    val todos: List<Todo> get() = _todos
    var showHigherPriorityItemsFirst by mutableStateOf(false)
    var loading by mutableStateOf(true)
}

class TodosViewModel(application: Application): AndroidViewModel(application) {
    val uiState = TodosScreenState()
    suspend fun getTodos() {
        val todos = TodosRepository.getTodos()
        uiState._todos.clear()
        uiState._todos.addAll(todos)
    }

    suspend fun toggleTodoCompletion(todo: Todo) {
        val todoCopy = todo.copy(completed = !(todo.completed ?: true))
        // optimistically update the ui
        uiState._todos[uiState._todos.indexOf(todo)] = todoCopy

        // go to firebase
        TodosRepository.updateTodo(todoCopy)
    }
}