package com.usu.firebasetodosapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.usu.firebasetodosapplication.ui.models.Todo
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
        uiState.saveSuccess = true
        // TODO: handle new todo creation and modification
    }
}