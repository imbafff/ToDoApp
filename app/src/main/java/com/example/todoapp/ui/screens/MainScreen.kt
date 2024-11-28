package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.ui.components.CurTask
import com.example.todoapp.ui.components.CustomTitle
import com.example.todoapp.ui.components.TasksLazyColumn


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF7F6F2),
                    titleContentColor = Color(0xFFF7F6F2),
                ),
                title = {
                    CustomTitle(scrollBehavior.state.collapsedFraction > 0.5f)
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                CurTask.curTask = null
                navController.navigate("add_task")
            }, shape = CircleShape, modifier = Modifier.padding(bottom = 24.dp).clip(CircleShape).shadow(10.dp), containerColor = Color(0xFF007AFF)) {
                Icon(Icons.Filled.Add, "Floating action button.", tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        innerPadding ->
        TasksLazyColumn(innerPadding, navController)
    }
}


