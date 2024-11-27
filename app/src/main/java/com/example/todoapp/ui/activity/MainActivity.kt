package com.example.todoapp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.screens.AddTaskScreen
import com.example.todoapp.ui.screens.MainScreen
import com.example.todoapp.ui.viewModel.MainViewModel


class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        mainViewModel.fetchData()
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
                                mainViewModel.updateTodoData(todoItem.first.id, todoItem.first)
                            } else {
                                mainViewModel.postTodoData(todoItem.first)
                            }
                            mainViewModel.fetchData()

                            navController.popBackStack()
                        },
                        onBack = { navController.popBackStack() },
                        onDelete = { id ->
                            mainViewModel.deleteTodoData(id)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
