package com.usu.firebasetodosapplication.ui.models

data class Todo(
    val id: String?,
    val userId: String?,
    val title: String?,
    val priority: Int?,
    val estimatedCompletionTime: Int?,
    val description: String?,
    val isCompleted: Boolean?
) {
    companion object Priority {
        const val PRIORITY_LOW = 0
        const val PRIORITY_MEDIUM = 1
        const val PRIORITY_HIGH = 2
    }
}
