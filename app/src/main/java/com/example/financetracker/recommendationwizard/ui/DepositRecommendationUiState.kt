package com.example.financetracker.recommendationwizard.ui

import com.example.financetracker.recommendationwizard.domain.Question

data class DepositRecommendationUiState(
    val questions: List<Question>,
    val currentQuestionIndex: Int,
    val currentQuestion: Question = questions[currentQuestionIndex],
    val isWizardFinished: Boolean = false
)