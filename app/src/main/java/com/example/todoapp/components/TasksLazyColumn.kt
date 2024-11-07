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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.models.TodoItem
import com.example.todoapp.repository.TodoItemsRepository

object CurTask {
    var curTask: TodoItem? = null
}

@Composable
fun TasksLazyColumn(innerPadding: PaddingValues, navController: NavController) {
    val tasks = TodoItemsRepository.getAllTodoItems()

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
            SwipeBox(modifier = Modifier.animateContentSize(), onDelete = {}, onComplete = {}) {
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
