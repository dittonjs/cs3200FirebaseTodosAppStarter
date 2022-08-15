package com.usu.firebasetodosapplication.ui.screens

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.usu.firebasetodosapplication.ui.components.FormField
import com.usu.firebasetodosapplication.ui.models.Todo
import com.usu.firebasetodosapplication.ui.viewmodels.TodosModificationViewModel
import com.usu.firebasetodosapplication.ui.viewmodels.TodosViewModel
import com.usu.firebasetodosapplication.util.priorityText
import kotlinx.coroutines.launch
import java.lang.Exception

@Composable
fun TodosModificationScreen(navHostController: NavHostController) {
    val viewModel: TodosModificationViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(state.saveSuccess) {
        if (state.saveSuccess) {
            navHostController.popBackStack()
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        FormField(
            value = state.title,
            onValueChange = { state.title = it },
            placeholder = { Text(text = "Title") }
        )
        Spacer(modifier = Modifier.height(4.dp))
        FormField(
            value = state.description,
            onValueChange = { state.description = it },
            placeholder = { Text(text = "Description") }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ){

            Box(Modifier.fillMaxWidth(.5f)) {
                OutlinedTextField(
                    value = priorityText(state.priority),
                    modifier = Modifier
                        .clickable {
                            state.dropdownExpanded = true
                        },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    },
                    enabled = false,
                    label = { Text(text = "Priority")},
                    onValueChange = {}
                )
                DropdownMenu(expanded = state.dropdownExpanded, onDismissRequest = { state.dropdownExpanded = false }) {
                    listOf(
                        Todo.PRIORITY_LOW,
                        Todo.PRIORITY_MEDIUM,
                        Todo.PRIORITY_HIGH
                    ).forEach {
                        DropdownMenuItem(onClick = {
                            state.priority = it
                            state.dropdownExpanded = false
                        }) {
                            Text(text = priorityText(it))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = state.estimatedCompletionTime,
                onValueChange = {
                    viewModel.updateCompletionTime(it)
                },
                isError = state.completionTimeError,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                label = { Text(text = "Time Estimate") },
                trailingIcon = { Text(text = "hr(s)")},
                maxLines = 1
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                scope.launch {
                    viewModel.saveTodo()
                }
            }) {
                Text(text = "Save")
            }
        }
        Text(
            text = state.errorMessage,
            style = TextStyle(color = MaterialTheme.colors.error),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Right
        )
    }
}