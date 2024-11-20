package com.example.todoapp.data.repository

import com.example.todoapp.data.source.ApiService
import com.example.todoapp.data.source.RetrofitInstance
import com.example.todoapp.domain.model.TodoItem
import com.example.todoapp.domain.model.TodoRequest
import com.example.todoapp.domain.model.TodoResponse
import retrofit2.Response



class TodoItemsRepository {
    private val retrofit = RetrofitInstance.getRetrofitInstance()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    suspend fun getTodoItems(): Response<TodoResponse> {
        return apiService.getData()
    }

    suspend fun postTodoItem(revision: Int, todoItem: TodoItem): Response<TodoResponse> {
        val request = TodoRequest(status = "ok", element = todoItem)
        return apiService.postData(revision, request)
    }

    suspend fun updateTodoItem(id: String, revision: Int, todoItem: TodoItem): Response<TodoResponse> {
        val request = TodoRequest(status = "ok", element = todoItem)
        return apiService.updateTask(id, revision, request)
    }

    suspend fun deleteTodoItem(id: String, revision: Int): Response<TodoResponse> {
        return apiService.deleteTask(id, revision)
    }
}
