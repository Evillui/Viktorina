package com.example.viktorina.viewmodel

sealed class QuizEvent {
    object StartQuiz : QuizEvent()
    data class SelectAnswer(val index: Int) : QuizEvent()
    object NextQuestion : QuizEvent()
    object RestartQuiz : QuizEvent()
}