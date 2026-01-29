package com.example.viktorina.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.viktorina.data.QuizRepositoryContract

class QuizViewModel(
    private val repository: QuizRepositoryContract
) : ViewModel() {

    private val _uiState = mutableStateOf(QuizUiState())
    val uiState: State<QuizUiState>
        get() = _uiState

    init {
        loadQuestions()
    }

    fun onEvent(event: QuizEvent) {
        when (event) {
            QuizEvent.StartQuiz -> startQuiz()
            is QuizEvent.SelectAnswer -> selectAnswer(event.index)
            QuizEvent.NextQuestion -> goToNextQuestion()
            QuizEvent.RestartQuiz -> restartQuiz()
        }
    }

    private fun startQuiz() {
        resetState()
        _uiState.value = _uiState.value.copy(currentScreen = Screen.QUESTION)
    }

    private fun selectAnswer(index: Int) {
        _uiState.value = _uiState.value.copy(
            selectedAnswerIndex = index,
            isNextButtonEnabled = true
        )
    }

    private fun goToNextQuestion() {
        val state = _uiState.value

        val selectedIndex = state.selectedAnswerIndex ?: return

        val question = state.questions.getOrNull(state.currentQuestionIndex) ?: run {
            finishQuiz(state, state.correctAnswers)
            return
        }

        val correct = if (selectedIndex == question.correctAnswerIndex) state.correctAnswers + 1
        else state.correctAnswers

        val isLast = state.currentQuestionIndex >= state.questions.lastIndex

        if (isLast) {
            finishQuiz(state, correct)
            return
        }

        val newState = state.copy(
            correctAnswers = correct,
            currentQuestionIndex = state.currentQuestionIndex + 1,
            selectedAnswerIndex = null,
            isNextButtonEnabled = false
        )

        _uiState.value = newState
    }

    private fun restartQuiz() {
        resetState()
        _uiState.value = _uiState.value.copy(currentScreen = Screen.WELCOME)
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
        val questions = repository.getQuizQuestions()
        _uiState.value = QuizUiState(
            questions = questions,
            totalQuestions = questions.size
        )
    }
    private fun finishQuiz(state: QuizUiState, correctAnswers: Int) {
        _uiState.value = state.copy(
            correctAnswers = correctAnswers,
            currentScreen = Screen.RESULT,
            selectedAnswerIndex = null,
            isNextButtonEnabled = false
        )
    }
}