package com.example.todoapp.domain.model


data class TodoRequest(
    val status: String,
    val element: TodoItem
)
