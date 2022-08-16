package com.usu.firebasetodosapplication.ui.models

data class Todo(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val priority: Int? = null,
    val estimatedCompletionTime: Int? = null,
    val description: String? = null,
    val completed: Boolean? = null
) {
    companion object Priority {
        const val PRIORITY_LOW = 0
        const val PRIORITY_MEDIUM = 1
        const val PRIORITY_HIGH = 2
    }
}
