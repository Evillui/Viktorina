package com.example.viktorina.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.viktorina.data.Question
import com.example.viktorina.data.QuizRepository

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val questions: List<Question> = QuizRepository.getQuizQuestions()

    init {
        resetQuiz()
    }

    fun onEvent(event: QuizEvent) {
        when (event) {
            QuizEvent.StartQuiz -> {
                resetQuiz()
                _uiState.value = _uiState.value.copy(
                    currentScreen = Screen.QUESTION,
                    currentQuestionIndex = 0
                )
            }

            is QuizEvent.SelectAnswer -> {
                _uiState.value = _uiState.value.copy(
                    selectedAnswerIndex = event.index,
                    isNextButtonEnabled = true
                )
            }

            QuizEvent.NextQuestion -> {
                val currentState = _uiState.value
                val currentQuestion = questions[currentState.currentQuestionIndex]

                val isCorrect = currentState.selectedAnswerIndex == currentQuestion.correctAnswerIndex

                val newCorrectAnswers = if (isCorrect) currentState.correctAnswers + 1 else currentState.correctAnswers
                val isLastQuestion = currentState.currentQuestionIndex == questions.size - 1

                if (isLastQuestion) {
                    _uiState.value = currentState.copy(
                        correctAnswers = newCorrectAnswers,
                        currentScreen = Screen.RESULT,
                        selectedAnswerIndex = null,
                        isNextButtonEnabled = false
                    )
                } else {
                    _uiState.value = currentState.copy(
                        correctAnswers = newCorrectAnswers,
                        currentQuestionIndex = currentState.currentQuestionIndex + 1,
                        selectedAnswerIndex = null,
                        isNextButtonEnabled = false
                    )
                }
            }

            QuizEvent.RestartQuiz -> {
                resetQuiz()
                _uiState.value = _uiState.value.copy(
                    currentScreen = Screen.WELCOME
                )
            }
        }
    }

    private fun resetQuiz() {
        _uiState.value = QuizUiState(
            questions = questions,
            totalQuestions = questions.size
        )
    }
}

data class QuizUiState(
    val currentScreen: Screen = Screen.WELCOME,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val totalQuestions: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val correctAnswers: Int = 0,
    val isNextButtonEnabled: Boolean = false
)

sealed class QuizEvent {
    object StartQuiz : QuizEvent()
    data class SelectAnswer(val index: Int) : QuizEvent()
    object NextQuestion : QuizEvent()
    object RestartQuiz : QuizEvent()
}

enum class Screen {
    WELCOME, QUESTION, RESULT
}