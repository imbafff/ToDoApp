package com.example.todoapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.models.Importance

@Composable
fun MultiToggleButton(
    currentSelection: Importance,
    onToggleChange: (Importance) -> Unit
) {
    val toggleStates = listOf(Importance.LOW, Importance.NORMAL, Importance.HIGH)
    val containerColor = Color(0xFFE0E0E0)
    val iconSize = 24.dp

    Box(
        modifier = Modifier
            .background(containerColor, shape = RoundedCornerShape(8.dp))
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min), verticalAlignment = Alignment.CenterVertically) {
            toggleStates.forEachIndexed { index, toggleState ->
                val isSelected = currentSelection == toggleState
                val backgroundTint = if (isSelected) Color.White else Color.Transparent
                val textColor = Color.Black

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(backgroundTint)
                        .toggleable(
                            value = isSelected,
                            enabled = true,
                            onValueChange = { selected ->
                                if (selected) {
                                    onToggleChange(toggleState)
                                }
                            }
                        )
                        .padding(vertical = 6.dp, horizontal = 8.dp)
                ) {
                    when (toggleState) {
                        Importance.LOW -> {
                            Icon(
                                painter = painterResource(id = R.drawable.img_4),
                                contentDescription = "Low importance",
                                tint = if (isSelected) Color.Black else Color.Gray,
                                modifier = Modifier
                                    .size(iconSize)
                                    .padding(4.dp)
                            )
                        }
                        Importance.NORMAL -> {
                            Text(
                                "нет",
                                color = textColor,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                        Importance.HIGH -> {
                            Text(
                                "‼️",
                                color = if (isSelected) Color.Red else Color.Black,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }

                if (index != toggleStates.size - 1) {
                    Divider(
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(1.dp)
                            .width(0.8.dp)
                            .height(20.dp)
                    )
                }
            }
        }
    }
}
