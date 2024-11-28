package com.example.todoapp.di

import com.example.todoapp.ui.viewModel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
}
