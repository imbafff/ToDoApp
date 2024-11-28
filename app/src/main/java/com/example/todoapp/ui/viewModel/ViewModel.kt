package com.example.todoapp.ui.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.di.AppComponent
import com.example.todoapp.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.todoapp.utils.ErrorHandler
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelScope

@Module
class ViewModelModule {
    @Provides
    @ViewModelScope
    fun provideMainViewModel(
        todoItemsRepository: TodoItemsRepository,
        errorHandler: ErrorHandler
    ): MainViewModel {
        return MainViewModel(todoItemsRepository, errorHandler)
    }
}

@Component(modules = [ViewModelModule::class], dependencies = [AppComponent::class])
@ViewModelScope
interface ViewModelComponent {
    fun inject(viewModel: MainViewModel)
}


class MainViewModel @Inject constructor(
    val apiRepository: TodoItemsRepository,
    val errorHandler: ErrorHandler
) : ViewModel() {
    private var _isVisible = MutableStateFlow(true)
    val isVisible: MutableStateFlow<Boolean> get() = _isVisible
    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> get() = _todoList

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error
    private val _loading = MutableStateFlow(false)
    // val loading: StateFlow<Boolean> get() = _loading // будет использоваться в будущем!


    fun toggleVisibility() {
        _isVisible.value = !_isVisible.value
    }

    fun fetchData() {
        performRequest {
            val response = apiRepository.getTodoItems()
            if (response.isSuccessful) {
                _todoList.value = response.body()?.list ?: emptyList()
            } else {
                errorHandler.handleError(response)
                _error.value = "Ошибка при загрузке данных"
            }
        }
    }

    fun postTodoData(todoItem: TodoItem) {
        performRequest {
            val response = apiRepository.getTodoItems()
            val revision = response.body()?.revision ?: 0
            val postResponse = apiRepository.postTodoItem(revision, todoItem)

            if (postResponse.isSuccessful) {
                fetchData()
            } else {
                errorHandler.handleError(postResponse)
                _error.value = "Ошибка при добавлении задачи"
            }
        }
    }

    fun updateTodoData(id: String, todoItem: TodoItem) {
        performRequest {
            val response = apiRepository.getTodoItems()
            val revision = response.body()?.revision ?: 0
            val updateResponse = apiRepository.updateTodoItem(id, revision, todoItem)

            if (updateResponse.isSuccessful) {
                fetchData()
            } else {
                errorHandler.handleError(updateResponse)
                _error.value = "Ошибка при обновлении задачи"
            }
        }
    }

    fun deleteTodoData(id: String) {
        performRequest {
            val response = apiRepository.getTodoItems()
            val revision = response.body()?.revision ?: 0
            val deleteResponse = apiRepository.deleteTodoItem(id, revision)

            if (deleteResponse.isSuccessful) {
                fetchData()
            } else {
                errorHandler.handleError(deleteResponse)
                _error.value = "Ошибка при удалении задачи"
            }
        }
    }

    private fun performRequest(request: suspend () -> Unit) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                request()
            } catch (e: Exception) {
                errorHandler.handleException(e)
                _error.value = "Ошибка: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}


class MainViewModelFactory(
    private val apiRepository: TodoItemsRepository,
    private val errorHandler: ErrorHandler
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Проверяем, что модель именно MainViewModel
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiRepository, errorHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}