package com.example.todoapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import de.charlex.compose.rememberRevealState
import de.charlex.compose.reset
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onComplete: () -> Unit,
    content: @Composable () -> Unit
) {
    val revealState = rememberRevealState()
    val coroutineScope = rememberCoroutineScope()

    RevealSwipe(
        state = revealState,
        modifier = modifier,
        directions = setOf(
            RevealDirection.StartToEnd,
            RevealDirection.EndToStart
        ),
        hiddenContentStart = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Green),
                contentAlignment = androidx.compose.ui.Alignment.CenterStart
            ) {
                IconButton(
                    onClick = {
                        onComplete()
                        coroutineScope.launch {
                            revealState.reset()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Complete",
                        tint = Color.White
                    )
                }
            }
        },
        hiddenContentEnd = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Red),
                contentAlignment = androidx.compose.ui.Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = {
                        onDelete()
                        coroutineScope.launch {
                            revealState.reset()
                        }
                    }
                ) {
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


