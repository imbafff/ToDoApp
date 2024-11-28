package com.example.todoapp.di

import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.utils.ErrorHandler
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideTodoItemsRepository(): TodoItemsRepository {
        return TodoItemsRepository()
    }

    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return ErrorHandler()
    }
}
