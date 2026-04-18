package com.example.financetracker.recommendationwizard.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.financetracker.recommendationwizard.domain.Answer
import com.example.financetracker.recommendationwizard.domain.WizardQuestionsProvider

class DepositRecommendationViewModel : ViewModel() {

    var uiState by mutableStateOf(
        DepositRecommendationUiState(
            questions = WizardQuestionsProvider.getQuestions(),
            currentQuestionIndex = 0
        )
    )
        private set

    fun onNextClick() {
        val currentQuestion = uiState.currentQuestion

        if (!checkAnswer(currentQuestion.answer)) {
            // TODO: тут если ответ не выбран то будет подсвечиваться граница красным как сигнал ошибки
            return
        }

        if (uiState.currentQuestionIndex < uiState.questions.lastIndex) {
            uiState = uiState.copy(
                currentQuestionIndex = uiState.currentQuestionIndex + 1
            )
        } else {
            // TODO: тут будет логика завершения wizard
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
        when (val answer = uiState.currentQuestion.answer) {
            is Answer.RadioButton ->
                uiState = uiState.copy(
                    currentQuestion = uiState.currentQuestion.copy(
                        answer = answer.copy(
                            selected = selectedAnswer
                        )
                    )
                )

            is Answer.CheckBox -> {
                val newSelected =
                    if (isChecked) {
                        answer.selected + selectedAnswer
                    } else {
                        answer.selected - selectedAnswer
                    }

                uiState = uiState.copy(
                    currentQuestion = uiState.currentQuestion.copy(
                        answer = answer.copy(
                            selected = newSelected
                        )
                    )
                )
            }
        }
    }

    private fun checkAnswer(answer: Answer): Boolean {
        return when (answer) {
            is Answer.RadioButton -> answer.selected != null
            is Answer.CheckBox -> answer.selected.isNotEmpty()
        }
    }
}