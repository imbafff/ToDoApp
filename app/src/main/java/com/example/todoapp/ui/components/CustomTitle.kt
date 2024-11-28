package com.example.todoapp.ui.components


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.R
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.ui.viewModel.MainViewModel
import com.example.todoapp.ui.viewModel.MainViewModelFactory
import com.example.todoapp.utils.ErrorHandler


@Composable
fun CustomTitle(isCollapsed: Boolean) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(TodoItemsRepository(), ErrorHandler())
    )
    val tasks by viewModel.todoList.collectAsState()
    val completedTasksCount = tasks.count { it.isCompleted }
    val isVisible by viewModel.isVisible.collectAsState()

    val textSize by animateFloatAsState(targetValue = if (isCollapsed) 24f else 38f, label = "")
    val paddingStart by animateDpAsState(targetValue = if (isCollapsed) 0.dp else 60.dp, label = "")
    val iconSize by animateDpAsState(targetValue = if (isCollapsed) 40.dp else 48.dp, label = "")

    LaunchedEffect(true) {
        viewModel.fetchData()
    }

    if (isCollapsed) {
        Row(
            modifier = Modifier
                .background(Color(0xFFF7F6F2))
                .fillMaxSize()
                .padding(end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Мои дела",
                fontWeight = FontWeight.Bold,
                fontSize = textSize.sp,
                color = Black
            )
            IconButton(
                onClick = { viewModel.toggleVisibility() },
                modifier = Modifier.size(iconSize)
            ) {
                Image(
                    painter = painterResource(
                        id = if (isVisible) R.drawable.img_10 else R.drawable.img_9
                    ),
                    colorFilter = ColorFilter.tint(Color(0xFF007AFF)),
                    contentDescription = if (isVisible) "Visible" else "Not visible",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    } else {
        Column(modifier = Modifier.padding(start = paddingStart, end = 24.dp)) {
            Text(
                text = "Мои дела",
                fontWeight = FontWeight.Bold,
                fontSize = textSize.sp,
                color = Black
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Выполнено - $completedTasksCount",
                    fontSize = 20.sp,
                    color = LightGray
                )
                IconButton(
                    onClick = { viewModel.toggleVisibility() },
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isVisible) R.drawable.img_10 else R.drawable.img_9
                        ),
                        colorFilter = ColorFilter.tint(Color(0xFF007AFF)),
                        contentDescription = if (isVisible) "Visible" else "Not visible",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
