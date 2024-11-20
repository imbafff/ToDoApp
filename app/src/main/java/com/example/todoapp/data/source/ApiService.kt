package com.example.todoapp.data.source

import com.example.todoapp.domain.model.TodoRequest
import com.example.todoapp.domain.model.TodoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {
    @GET("todo/list")
    suspend fun getData(): Response<TodoResponse>

    @POST("todo/list")
    suspend fun postData(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoRequest: TodoRequest
    ): Response<TodoResponse>

    @PUT("todo/list/{id}")
    suspend fun updateTask(
        @Path("id") id: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoRequest: TodoRequest
    ): Response<TodoResponse>

    @DELETE("todo/list/{id}")
    suspend fun deleteTask(
        @Path("id") id: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): Response<TodoResponse>
}