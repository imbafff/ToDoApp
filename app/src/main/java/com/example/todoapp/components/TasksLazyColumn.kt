package com.example.todoapp.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todoapp.models.TodoItem
import com.example.todoapp.viewModel.MainViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

object CurTask {
    var curTask: TodoItem? = null
}


@Composable
fun TasksLazyColumn(innerPadding: PaddingValues, navController: NavController) {
    val viewModel: MainViewModel = viewModel()
    val tasks by viewModel.todoList.collectAsState()
    val currentRevision by viewModel.currentRevision.collectAsState()

    LaunchedEffect(currentRevision) {
        viewModel.fetchData("Aerinon")
    }


    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier.background(Color(0xFFF7F6F2)).padding(10.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .height(17.dp)
                    .background(Color.White)
            )
        }
        items(tasks) { task ->
            SwipeBox(
                modifier = Modifier.animateContentSize(),
                onDelete = {
                    // Удаляем задачу
                    viewModel.deleteTodoData("Aerinon", task.id)
                },
                onComplete = {
                    // Изменяем состояние выполнения задачи
                    task.isCompleted = !task.isCompleted
                    // Обновляем задачу
                    viewModel.updateTodoData("Aerinon", task.id, task)
                }
            ) {
                TaskUi(task, onTaskClick = {
                    navController.navigate("add_task")
                    CurTask.curTask = task
                })
            }
        }
        item {
            TextButton(
                onClick = {
                    navController.navigate("add_task")
                    CurTask.curTask = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(Color.White)
            ) {
                Text(
                    "Новое", color = Color.Gray, fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(start = 18.dp)
                )
            }
        }
    }
}


