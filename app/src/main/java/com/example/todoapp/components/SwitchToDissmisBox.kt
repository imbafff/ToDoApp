package com.example.todoapp.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onComplete: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    // Переменные для проверки, чтобы действия не повторялись
    var isDeleted by remember { mutableStateOf(false) }
    var isCompleted by remember { mutableStateOf(false) }

    // Регистрируем изменение состояния свайпа
    val showDeleteButton = swipeState.dismissDirection == SwipeToDismissBoxValue.EndToStart
    val showCompleteButton = swipeState.dismissDirection == SwipeToDismissBoxValue.StartToEnd

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = swipeState,
        backgroundContent = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        when (swipeState.dismissDirection) {
                            SwipeToDismissBoxValue.EndToStart -> Color.Red
                            SwipeToDismissBoxValue.StartToEnd -> Color.Green
                            else -> Color.Transparent
                        }
                    ),
                horizontalArrangement = when (swipeState.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> Arrangement.End
                    SwipeToDismissBoxValue.StartToEnd -> Arrangement.Start
                    else -> Arrangement.Center
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showCompleteButton && !isCompleted) {
                    // Обработка завершения задачи
                    onComplete()
                    isCompleted = true // Отметим, что задача завершена
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Complete",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (showDeleteButton && !isDeleted) {
                    // Обработка удаления задачи
                    onDelete()
                    isDeleted = true // Отметим, что задача удалена
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        content()
    }
}
