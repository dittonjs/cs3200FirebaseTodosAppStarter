package com.usu.firebasetodosapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.usu.firebasetodosapplication.ui.models.Todo
import com.usu.firebasetodosapplication.ui.repositories.TodosRepository
import kotlin.Exception

class TodosModificationScreenState {
    var title by mutableStateOf("")
    var estimatedCompletionTime by mutableStateOf("1")
    var priority by mutableStateOf(Todo.PRIORITY_LOW)
    var description by mutableStateOf("")
    var titleError by mutableStateOf(false)
    var completionTimeError by mutableStateOf(false)
    var dropdownExpanded by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var saveSuccess by mutableStateOf(false)
}

class TodosModificationViewModel(application: Application): AndroidViewModel(application) {
    val uiState = TodosModificationScreenState()
    var id: String? = null
    suspend fun setupInitialState(id: String?) {
        if (id == null || id == "new") return
        this.id = id
        val todo = TodosRepository.getTodos().find { it.id == id } ?: return
        // note: handle todo that isn't found
        uiState.title = todo.title ?: ""
        uiState.description = todo.description ?: ""
        uiState.estimatedCompletionTime = "${todo.estimatedCompletionTime ?: "1"}"
        uiState.priority = todo.priority ?: Todo.PRIORITY_LOW

    }

    fun updateCompletionTime(input: String) {
        uiState.completionTimeError = false
        uiState.errorMessage = ""
        try {
            input.filter { !it.isWhitespace() }.toInt()
        } catch (e: Exception) {
            uiState.completionTimeError = true
            uiState.errorMessage = "Estimated completion time must be a whole number greater than 0."
        } finally {
            uiState.estimatedCompletionTime = input.filter { !it.isWhitespace() }
        }
    }

    suspend fun saveTodo() {
        // we handle the updates of completion time in real time
        if (uiState.completionTimeError) return

        uiState.errorMessage = ""
        uiState.titleError = false


        if (uiState.title.isEmpty()) {
            uiState.titleError = true
            uiState.errorMessage = "Title cannot be blank."
            return
        }

        if (id == null) { // create new
            TodosRepository.createTodo(
                uiState.title,
                uiState.description,
                uiState.estimatedCompletionTime.toInt(),
                uiState.priority
            )
        } else { // update
            val todo = TodosRepository.getTodos().find { it.id == id } ?: return
            TodosRepository.updateTodo(
                todo.copy(
                    title = uiState.title,
                    priority = uiState.priority,
                    description = uiState.description,
                    estimatedCompletionTime = uiState.estimatedCompletionTime.toInt()
                )
            )
        }

        uiState.saveSuccess = true
    }
}