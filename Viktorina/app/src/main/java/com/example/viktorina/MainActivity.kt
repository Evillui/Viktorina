package com.example.viktorina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viktorina.screens.QuestionScreen
import com.example.viktorina.screens.ResultScreen
import com.example.viktorina.screens.WelcomeScreen
import com.example.viktorina.ui.theme.QuizAppTheme
import com.example.viktorina.viewmodel.QuizEvent
import com.example.viktorina.viewmodel.QuizViewModel
import com.example.viktorina.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuizApp()
                }
            }
        }
    }
}

@Composable
fun QuizApp() {
    val viewModel: QuizViewModel = viewModel()
    val uiState = viewModel.uiState.value

    when (uiState.currentScreen) {
        Screen.WELCOME -> {
            WelcomeScreen(
                onStartQuiz = { viewModel.onEvent(QuizEvent.StartQuiz) }
            )
        }

        Screen.QUESTION -> {
            QuestionScreen(
                uiState = uiState,
                onAnswerSelected = { index ->
                    viewModel.onEvent(QuizEvent.SelectAnswer(index))
                },
                onNextClicked = {
                    viewModel.onEvent(QuizEvent.NextQuestion)
                }
            )
        }

        Screen.RESULT -> {
            ResultScreen(
                correctAnswers = uiState.correctAnswers,
                totalQuestions = uiState.totalQuestions,
                onRestart = {
                    viewModel.onEvent(QuizEvent.RestartQuiz)
                }
            )
        }
    }
}