package com.example.todoapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.domain.model.TodoItem
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TaskUi(task: TodoItem, onTaskClick: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        appendInlineContent("icon", "[icon]")
        append(task.text)
    }

    val inlineContent = mapOf(
        "icon" to InlineTextContent(
            placeholder = Placeholder(
                width = if (task.importance == "basic") 0.sp else 24.sp,
                height = if (task.importance == "basic") 0.sp else 24.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            when (task.importance) {
                "low" -> Image(
                    painter = painterResource(id = R.drawable.img_4),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                "basic" -> Text("")
                "important" -> Image(
                    painter = painterResource(id = R.drawable.img_8),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )

    Row(
        modifier = Modifier
            .shadow(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(1.dp))
            .background(Color.LightGray)
            .background(Color.White)
            .padding(8.dp)
            .clickable(onClick = onTaskClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Действие для левой кнопки */ }) {
            Image(
                painter = painterResource(
                    if (task.isCompleted) R.drawable.img
                    else when (task.importance) {
                        "important" -> R.drawable.img_2
                        else -> {R.drawable.img_1}
                    }
                ),
                contentDescription = "checkBox",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = annotatedString,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    inlineContent = inlineContent,
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontSize = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = if (task.isCompleted) Color.LightGray else Color.Black
                )
            }

            task.deadline?.let {
                val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
                val formattedDate = dateFormat.format(it)
                Text(text = formattedDate, color = Color.LightGray)
            }
        }

        IconButton(onClick = { }) {
            Image(
                painter = painterResource(id = R.drawable.img_3),
                contentDescription = "Right Button",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

