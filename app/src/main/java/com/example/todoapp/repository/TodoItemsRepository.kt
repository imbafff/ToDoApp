package com.example.todoapp.repository


import com.example.todoapp.models.TodoItem
import java.util.*

object TodoItemsRepository {
    var todoItems = mutableListOf<TodoItem>()


    val count: Int
        get() = todoItems.count { it.isCompleted }



    fun getAllTodoItems() = todoItems

    fun addTodoItem(todoItem: TodoItem) {
        todoItems.add(todoItem)
    }

    fun removeTodoItem(id: String) {
        todoItems.removeAll { it.id == id }
    }
}

