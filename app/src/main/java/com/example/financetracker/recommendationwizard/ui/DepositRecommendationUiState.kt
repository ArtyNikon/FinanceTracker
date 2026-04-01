package com.example.financetracker.recommendationwizard.ui

import com.example.financetracker.recommendationwizard.domain.DepositGoal

data class DepositRecommendationUiState(
    val currentQuestion: Question = Question.DepositGoal,
    val selectedDepositGoal: DepositGoal? = null,
    val isWizardFinished: Boolean = false
)