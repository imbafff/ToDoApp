package com.example.todoapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.models.TodoItem
import com.example.todoapp.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("todo/list")
    suspend fun getData(@Header("Authorization") token: String): Response<TodoResponse>

    @POST("todo/list")
    suspend fun postData(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoRequest: TodoRequest
    ): Response<TodoResponse>

    @PUT("todo/list/{id}")
    suspend fun updateTask(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoRequest: TodoRequest
    ): Response<TodoResponse>

    @DELETE("todo/list/{id}")
    suspend fun deleteTask(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): Response<TodoResponse>
}

data class TodoResponse(
    val status: String,
    val revision: Int,
    val list: List<TodoItem>
)

data class TodoRequest(
    val status: String,
    val element: TodoItem
)

class MainViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://hive.mrdekk.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> get() = _todoList

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _currentRevision = MutableStateFlow(0)
    val currentRevision: StateFlow<Int> get() = _currentRevision

    private var currentJob: Job? = null

    fun fetchData(token: String) {
        currentJob?.cancel()
        _loading.value = true
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getData("Bearer $token")
                if (response.isSuccessful) {
                    val todoItems = response.body()?.list ?: emptyList()
                    _todoList.value = todoItems
                    TodoItemsRepository.todoItems = todoItems.toMutableList()
                    Log.d("MainViewModel", "Todo List: $todoItems")
                } else {
                    val errorMessage = when (response.code()) {
                        404 -> "Не удалось найти список задач. Пожалуйста, проверьте подключение или попытайтесь позже."
                        500 -> "Внутренняя ошибка сервера. Попробуйте снова позже."
                        else -> "Произошла ошибка при загрузке данных: ${response.code()}. Пожалуйста, попробуйте позже."
                    }
                    _error.value = errorMessage
                    Log.e("MainViewModel", errorMessage)
                }
            } catch (e: Exception) {
                _error.value = "Произошла ошибка: ${e.localizedMessage}. Пожалуйста, проверьте интернет-соединение и повторите попытку."
                Log.e("MainViewModel", "Ошибка запроса: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun postTodoData(token: String, todoItemPost: TodoItem) {
        currentJob?.cancel()
        _loading.value = true
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val getRevisionResponse = apiService.getData("Bearer $token")
                val revision = getRevisionResponse.body()?.revision ?: 0
                val request = TodoRequest(status = "ok", element = todoItemPost)
                val response = apiService.postData(
                    token = "Bearer $token",
                    revision = revision,
                    todoRequest = request
                )
                Log.d("MainViewModel", "Updated Todo List: $request")
                if (response.isSuccessful) {
                    val todoItems = response.body()?.list ?: emptyList()
                    _todoList.value = todoItems
                    TodoItemsRepository.todoItems = todoItems.toMutableList()
                    _currentRevision.value = revision
                    Log.d("MainViewModel", "request: $request")
                } else {
                    val errorMessage = when (response.code()) {
                        400 -> "Некорректный запрос. Пожалуйста, проверьте данные и попробуйте снова."
                        401 -> "Не авторизован. Пожалуйста, войдите в систему."
                        500 -> "Ошибка сервера. Пожалуйста, попробуйте позже."
                        else -> "Произошла ошибка при добавлении задачи: ${response.code()}. Пожалуйста, попробуйте позже."
                    }
                    _error.value = errorMessage
                    Log.e("MainViewModel", errorMessage)
                }
            } catch (e: Exception) {
                _error.value = "Произошла ошибка: ${e.localizedMessage}. Пожалуйста, проверьте интернет-соединение и повторите попытку."
                Log.e("MainViewModel", "Ошибка POST запроса: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateTodoData(token: String, id: String, todoItemPost: TodoItem) {
        currentJob?.cancel()
        _loading.value = true
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val getRevisionResponse = apiService.getData("Bearer $token")
                val revision = getRevisionResponse.body()?.revision ?: 0
                val request = TodoRequest(status = "ok", element = todoItemPost)
                val response = apiService.updateTask(
                    id = id,
                    token = "Bearer $token",
                    todoRequest = request, revision = revision
                )
                if (response.isSuccessful) {
                    val todoItems = response.body()?.list ?: emptyList()
                    _todoList.value = todoItems
                    TodoItemsRepository.todoItems = todoItems.toMutableList()
                    _currentRevision.value = revision
                    Log.d("sneck", "Updated Todo List: $todoItems")
                } else {
                    val errorMessage = when (response.code()) {
                        404 -> "Задача не найдена для обновления. Пожалуйста, проверьте ID задачи."
                        400 -> "Некорректные данные для обновления. Пожалуйста, проверьте их и попробуйте снова."
                        500 -> "Ошибка сервера. Попробуйте позже."
                        else -> "Произошла ошибка при обновлении задачи: ${response.code()}. Пожалуйста, попробуйте позже."
                    }
                    _error.value = errorMessage
                    Log.e("MainViewModel", errorMessage)
                }
            } catch (e: Exception) {
                _error.value = "Произошла ошибка: ${e.localizedMessage}. Пожалуйста, проверьте интернет-соединение и повторите попытку."
                Log.e("MainViewModel", "Ошибка PUT запроса: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteTodoData(token: String, id: String) {
        currentJob?.cancel()
        _loading.value = true
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val getRevisionResponse = apiService.getData("Bearer $token")
                val revision = getRevisionResponse.body()?.revision ?: 0
                val response = apiService.deleteTask(
                    id = id,
                    token = "Bearer $token",
                    revision = revision
                )
                if (response.isSuccessful) {
                    val todoItems = response.body()?.list ?: emptyList()
                    _todoList.value = todoItems
                    TodoItemsRepository.todoItems = todoItems.toMutableList()
                    _currentRevision.value = revision
                    Log.d("MainViewModel", "Todo item deleted: $id")
                } else {
                    val errorMessage = when (response.code()) {
                        404 -> "Задача не найдена для удаления. Пожалуйста, проверьте ID задачи."
                        500 -> "Ошибка сервера. Попробуйте позже."
                        else -> "Произошла ошибка при удалении задачи: ${response.code()}. Пожалуйста, попробуйте позже."
                    }
                    _error.value = errorMessage
                    Log.e("MainViewModel", errorMessage)
                }
            } catch (e: Exception) {
                _error.value = "Произошла ошибка: ${e.localizedMessage}. Пожалуйста, проверьте интернет-соединение и повторите попытку."
                Log.e("MainViewModel", "Ошибка DELETE запроса: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
    }
}
