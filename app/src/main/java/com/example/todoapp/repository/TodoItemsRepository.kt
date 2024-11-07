package com.example.todoapp.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.todoapp.models.Importance
import com.example.todoapp.models.TodoItem
import java.util.Date

object TodoItemsRepository {
    private val todoItems = mutableStateListOf<TodoItem>()

    init {
        repeat(20) { index ->
            val todoItem = TodoItem(
                id = System.currentTimeMillis().toInt(),
                text = "Task $index",
                importance = when (index % 3) {
                    0 -> Importance.LOW
                    1 -> Importance.NORMAL
                    else -> Importance.HIGH
                },
                deadline = if (index % 4 == 0) "1 марта 2024" else null,
                isCompleted = index % 5 == 0
            )
            todoItems.add(todoItem)
        }
    }

    fun getAllTodoItems(): List<TodoItem> = todoItems

    fun addTodoItem(todoItem: TodoItem) {
        todoItems.add(todoItem)
    }

    fun removeTodoItem(id: Int) {
        todoItems.removeIf { it.id == id }
    }

    fun getValueById(id: Int): TodoItem {
        return todoItems.sortedBy { it.id == id }[0]
    }
}