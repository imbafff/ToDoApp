package com.example.todoapp.data.api

import com.example.todoapp.data.api.model.PostResponse
import retrofit2.Response

interface PostService {
    suspend fun getAllPosts(): Response<List<PostResponse>>
}