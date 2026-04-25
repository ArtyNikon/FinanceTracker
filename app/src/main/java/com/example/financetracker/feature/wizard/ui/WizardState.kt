package com.example.financetracker.feature.wizard.ui

import com.example.financetracker.feature.wizard.domain.Question
import com.example.financetracker.feature.wizard.domain.WizardScreen

data class WizardState(
    val questions: List<Question>,
    val currentQuestionIndex: Int,
    val currentScreen: WizardScreen,
    val showError: Boolean = false
) {
    val currentQuestion: Question
        get() = questions[currentQuestionIndex]

    val progress: Float
        get() = (currentQuestionIndex + 1) / questions.size.toFloat()
}