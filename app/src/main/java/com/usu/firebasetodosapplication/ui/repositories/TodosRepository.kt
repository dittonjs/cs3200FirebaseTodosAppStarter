package com.usu.firebasetodosapplication.ui.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.usu.firebasetodosapplication.ui.models.Todo
import kotlinx.coroutines.tasks.await

object TodosRepository {

    private val todosCache = mutableListOf<Todo>()
    private var cacheInitialized = false

    suspend fun getTodos(): List<Todo> {
        if (!cacheInitialized){
            val snapshot = Firebase.firestore
                .collection("todos")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .get()
                .await()
            todosCache.addAll(snapshot.toObjects())
            cacheInitialized = true
        }

        return todosCache

    }

    suspend fun createTodo(
        title: String,
        description: String,
        estimateCompletionTime: Int,
        priority: Int
    ): Todo {
        val doc = Firebase.firestore.collection("todos").document()
        val todo = Todo(
            title = title,
            description = description,
            estimatedCompletionTime = estimateCompletionTime,
            priority = priority,
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            completed = false
        )
        doc.set(todo).await()
        todosCache.add(todo)
        return todo
    }

    suspend fun updateTodo(todo: Todo) {
        Firebase.firestore
            .collection("todos")
            .document(todo.id!!)
            .set(todo)
            .await()

        val oldTodoIndex = todosCache.indexOfFirst {
            it.id == todo.id
        }
        todosCache[oldTodoIndex] = todo
    }
}