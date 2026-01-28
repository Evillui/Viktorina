package com.example.viktorina.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.viktorina.data.Question
import com.example.viktorina.data.QuizRepository

class QuizViewModel : ViewModel() {

    private val _uiState = mutableStateOf(QuizUiState())
    val uiState = _uiState
    private val questions: List<Question> = QuizRepository.getQuizQuestions()

    init {
        loadQuestions()
    }

    fun onEvent(event: QuizEvent) {
        when (event) {
            QuizEvent.StartQuiz -> {
                startQuiz()
            }

            is QuizEvent.SelectAnswer -> {
                selectAnswer(event.index)
            }

            QuizEvent.NextQuestion -> {
                goToNextQuestion()
            }

            QuizEvent.RestartQuiz -> {
                restartQuiz()
            }
        }
    }

    private fun startQuiz() {
        resetState()
        _uiState.value = _uiState.value.copy(
            currentScreen = Screen.QUESTION
        )
    }

    private fun selectAnswer(index: Int) {
        _uiState.value = _uiState.value.copy(
            selectedAnswerIndex = index,
            isNextButtonEnabled = true
        )
    }

    private fun goToNextQuestion() {
        val currentState = _uiState.value
        val currentQuestion = questions[currentState.currentQuestionIndex]
        val isCorrect = currentState.selectedAnswerIndex == currentQuestion.correctAnswerIndex
        val newCorrectAnswers = if (isCorrect) currentState.correctAnswers + 1 else currentState.correctAnswers
        val isLastQuestion = currentState.currentQuestionIndex == questions.size - 1

        _uiState.value = if (isLastQuestion) {
            currentState.copy(
                correctAnswers = newCorrectAnswers,
                currentScreen = Screen.RESULT,
                selectedAnswerIndex = null,
                isNextButtonEnabled = false
            )
        } else {
            currentState.copy(
                correctAnswers = newCorrectAnswers,
                currentQuestionIndex = currentState.currentQuestionIndex + 1,
                selectedAnswerIndex = null,
                isNextButtonEnabled = false
            )
        }
    }

    private fun restartQuiz() {
        resetState()
        _uiState.value = _uiState.value.copy(
            currentScreen = Screen.WELCOME
        )
    }

    private fun resetState() {
        _uiState.value = _uiState.value.copy(
            currentQuestionIndex = 0,
            selectedAnswerIndex = null,
            correctAnswers = 0,
            isNextButtonEnabled = false
        )
    }

    private fun loadQuestions() {
        _uiState.value = QuizUiState(
            questions = questions,
            totalQuestions = questions.size
        )
    }
}