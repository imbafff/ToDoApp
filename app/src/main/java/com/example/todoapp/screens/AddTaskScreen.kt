package com.example.todoapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.components.CurTask
import com.example.todoapp.components.MultiToggleButton
import com.example.todoapp.models.Importance
import com.example.todoapp.models.TodoItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(onAdd: (TodoItem) -> Unit, onBack: () -> Unit) {
    val task = CurTask.curTask
    var taskText by remember { mutableStateOf(task?.text ?: "") }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var selectedOption: Importance by remember { mutableStateOf(task?.importance ?: Importance.LOW) }
    var dateText by remember { mutableStateOf(task?.deadline) }
    var checked by remember {
        mutableStateOf(task?.deadline != null)
    }

    val context = LocalContext.current

    val showDatePicker = {
        val calendar = Calendar.getInstance()
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
                dateText = dateFormat.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    LaunchedEffect(checked) {
        if (checked) {
            if (dateText == null) {
                showDatePicker()
            }
        } else {
            dateText = null
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        onBack() }) {
                        Text("Сохранить", color = Color(0xFF2196F3))
                    }
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier.shadow(if (scrollBehavior.state.overlappedFraction > 0f) 8.dp else 0.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = if (scrollBehavior.state.overlappedFraction > 0f) {
                        Color(0xFFEFEFEF)
                    } else {
                        Color.White
                    }
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item {
                TextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    textStyle = TextStyle(fontSize = 16.sp),
                    placeholder = { Text("Что надо сделать...", fontSize = 16.sp) },
                    modifier = Modifier
                        .heightIn(104.dp)
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Важность", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    MultiToggleButton(selectedOption) { newSelection ->
                        selectedOption = newSelection
                    }
                }
            }
            item {
                Divider(modifier = Modifier.padding(16.dp))
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Сделать до", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = dateText ?: "", fontSize = 14.sp, color = Color.Blue)
                    }
                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF2196F3),
                            checkedTrackColor = Color(0xFFBBDEFB),
                            uncheckedThumbColor = Color(0xFF2196F3)
                        )
                    )
                }
            }
            item { HorizontalDivider() }
            item {
                TextButton(
                    onClick = { onBack },
                    modifier = Modifier
                        .padding(top = 16.dp),

                    ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Удалить",
                        tint = if (task == null) Color.LightGray else Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Удалить", color = if (task == null) Color.LightGray else Color.Red)
                }
            }
        }
    }
}
