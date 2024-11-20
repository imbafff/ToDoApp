package com.example.todoapp.domain.model


data class TodoResponse(
    val status: String,
    val revision: Int,
    val list: List<TodoItem>
)