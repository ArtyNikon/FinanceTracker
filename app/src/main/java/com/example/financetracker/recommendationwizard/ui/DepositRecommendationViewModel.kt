package com.example.financetracker.recommendationwizard.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.financetracker.recommendationwizard.domain.DepositGoal

class DepositRecommendationViewModel : ViewModel() {

    var uiState by mutableStateOf(
        DepositRecommendationUiState(
            currentQuestion = Question.DepositGoal
        )
    )
        private set

    fun onDepositGoalSelected(goal: DepositGoal) {
        uiState = uiState.copy(selectedDepositGoal = goal)
    }

    fun onNextClicked() {
        uiState = when (uiState.currentQuestion) {
            Question.DepositGoal -> uiState.copy(currentQuestion = Question.ExtraInfo1)
            Question.ExtraInfo1 -> uiState.copy(currentQuestion = Question.ExtraInfo2)
            Question.ExtraInfo2 -> uiState.copy(currentQuestion = Question.ExtraInfo3)
            Question.ExtraInfo3 -> uiState.copy(currentQuestion = Question.ExtraInfo4)
            Question.ExtraInfo4 -> uiState.copy(isWizardFinished = true)
        }
    }

    fun onBackClicked() {
        uiState = when (uiState.currentQuestion) {
            Question.DepositGoal -> uiState
            Question.ExtraInfo1 -> uiState.copy(currentQuestion = Question.DepositGoal)
            Question.ExtraInfo2 -> uiState.copy(currentQuestion = Question.ExtraInfo1)
            Question.ExtraInfo3 -> uiState.copy(currentQuestion = Question.ExtraInfo2)
            Question.ExtraInfo4 -> uiState.copy(currentQuestion = Question.ExtraInfo3)
        }
    }
}