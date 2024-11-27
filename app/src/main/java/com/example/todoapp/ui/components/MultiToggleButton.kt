package com.example.todoapp.ui.components

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
import androidx.compose.material3.HorizontalDivider
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


@Composable
fun MultiToggleButton(
    currentSelection: String,
    onToggleChange: (String) -> Unit
) {
    val toggleStates = listOf("low", "basic", "important")
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
                        "low" -> {
                            Icon(
                                painter = painterResource(id = R.drawable.img_4),
                                contentDescription = "Low importance",
                                tint = if (isSelected) Color.Black else Color.Gray,
                                modifier = Modifier
                                    .size(iconSize)
                                    .padding(4.dp)
                            )
                        }
                        "basic" -> {
                            Text(
                                "нет",
                                color = textColor,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                        "important" -> {
                            Text(
                                "‼\uFE0F",
                                color = if (isSelected) Color.Red else Color.Black,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }

                if (index != (toggleStates.size - 1)) {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(1.dp)
                            .width(0.8.dp)
                            .height(20.dp),
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
