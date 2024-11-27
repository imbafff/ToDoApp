package com.example.todoapp.utils

import android.util.Log
import retrofit2.Response


class ErrorHandler {

    fun handleError(response: Response<*>) {
        val errorMessage = when (response.code()) {
            400 -> "Некорректный запрос."
            401 -> "Ошибка авторизации."
            404 -> "Ресурс не найден."
            500 -> "Ошибка сервера."
            else -> "Неизвестная ошибка: ${response.code()}."
        }
        Log.e("ErrorHandler", errorMessage)
    }

    fun handleException(e: Exception) {
        Log.e("ErrorHandler", "Ошибка запроса: ${e.localizedMessage}")
    }
}
