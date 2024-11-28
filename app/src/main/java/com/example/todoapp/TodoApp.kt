package com.example.todoapp

import android.app.Application
import com.example.todoapp.di.AppComponent
import dagger.internal.DaggerGenerated


class TodoApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        DaggerGenerated()
    }
}
