package com.example.todoapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF121212),  // Темный цвет для фона
    secondary = Color(0xFF03DAC5), // Акцентный цвет
    background = Color(0xFF121212), // Фон
    onPrimary = Color.Black, // Цвет текста на основном фоне
    onSecondary = Color.Black, // Цвет текста на вторичном фоне
    onBackground = Color.White // Цвет текста на фоне
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Black, // Черный для светлой темы
    secondary = Color(0xFF03DAC5), // Акцентный зеленый
    background = Color(0xFFFFFBFE), // Светлый фон
    onPrimary = Color.Black, // Белый текст на основном фоне
    onSecondary = Color.Black, // Черный текст на вторичном фоне
    onBackground = Color.Black // Черный текст на светлом фоне
)

@Composable
fun ToDoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,  // Выключены динамические цвета для упрощения
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
