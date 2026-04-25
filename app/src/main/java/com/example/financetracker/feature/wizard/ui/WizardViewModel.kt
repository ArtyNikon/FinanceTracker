package com.example.financetracker.feature.wizard.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.financetracker.feature.wizard.domain.Answer
import com.example.financetracker.feature.wizard.domain.WizardQuestionsProvider
import com.example.financetracker.feature.wizard.domain.WizardScreen

class WizardViewModel : ViewModel() {

    var uiState by mutableStateOf(
        WizardState(
            questions = WizardQuestionsProvider.getQuestions(),
            currentQuestionIndex = 0,
            currentScreen = WizardScreen.WELCOME // Если в БД есть история прохождений — показываем QUESTIONS, иначе — WELCOME.
        )
    )
        private set

    fun onNextClick() {
        val currentQuestion = uiState.currentQuestion

        if (!checkAnswer(currentQuestion.answer)) {
            uiState = uiState.copy(showError = true)
            return
        }

        uiState = if (uiState.currentQuestionIndex < uiState.questions.lastIndex) {
            uiState.copy(
                currentQuestionIndex = uiState.currentQuestionIndex + 1
            )
        } else {
            uiState.copy(currentScreen = WizardScreen.FINISH)
        }
    }

    fun onBackClick() {
        if (uiState.currentQuestionIndex > 0) {
            uiState = uiState.copy(currentQuestionIndex = uiState.currentQuestionIndex - 1)
        }
    }

    fun onAnswerSelected(
        selectedAnswer: String,
        isChecked: Boolean = false
    ) {
        val index = uiState.currentQuestionIndex
        val currentQuestion = uiState.questions[index]

        val updatedQuestion = when (val answer = currentQuestion.answer) {
            is Answer.RadioButton -> {
                currentQuestion.copy(
                    answer = answer.copy(
                        selected = selectedAnswer
                    )
                )
            }

            is Answer.CheckBox -> {
                val newSelected =
                    if (isChecked) {
                        answer.selected + selectedAnswer
                    } else {
                        answer.selected - selectedAnswer
                    }

                currentQuestion.copy(
                    answer = answer.copy(
                        selected = newSelected
                    )
                )
            }
        }

        val updatedQuestions = uiState.questions.toMutableList().apply {
            this[index] = updatedQuestion
        }

        uiState = uiState.copy(
            questions = updatedQuestions,
            showError = false
        )
    }

    private fun checkAnswer(answer: Answer): Boolean {
        return when (answer) {
            is Answer.RadioButton -> answer.selected != null
            is Answer.CheckBox -> answer.selected.isNotEmpty()
        }
    }
}