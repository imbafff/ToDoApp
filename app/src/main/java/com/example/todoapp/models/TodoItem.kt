package com.example.todoapp.models

import java.util.Date

data class TodoItem(
    val id: Int,
    var text: String,
    var importance: Importance,
    var deadline: String? = null,
    var isCompleted: Boolean = false,
    var createdAt: String? = null,
    var modifiedAt: Date? = null
)