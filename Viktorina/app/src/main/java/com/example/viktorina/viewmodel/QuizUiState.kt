package com.example.viktorina.viewmodel

import com.example.viktorina.data.Question

data class QuizUiState(
    val currentScreen: Screen = Screen.WELCOME,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val totalQuestions: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val correctAnswers: Int = 0,
    val isNextButtonEnabled: Boolean = false
)