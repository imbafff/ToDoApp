package com.example.todoapp.data.api.model

import com.example.todoapp.models.TodoItem
import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("revision")
    val revision: Int? = null,
    @SerializedName("list")
    val list: List<TodoItem>? = null
)