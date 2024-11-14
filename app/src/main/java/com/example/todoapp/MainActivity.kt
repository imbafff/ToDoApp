package com.example.todoapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.screens.AddTaskScreen
import com.example.todoapp.screens.MainScreen
import com.example.todoapp.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()  // Получаем ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mainViewModel.fetchData("Aerinon")
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val errorMessage = mainViewModel.error.collectAsState().value

            LaunchedEffect(errorMessage) {
                errorMessage?.let {
                    Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                }
            }

            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(navController)
                }
                composable("add_task") {
                    AddTaskScreen(
                        onAdd = { todoItem ->
                            if (todoItem.second) {
                                mainViewModel.updateTodoData("Aerinon", todoItem.first.id, todoItem.first)
                            } else {
                                mainViewModel.postTodoData("Aerinon", todoItem.first)
                            }

                            // Возвращаемся на главный экран
                            navController.popBackStack()
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
