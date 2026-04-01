package com.example.financetracker.recommendationwizard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DepositRecommendationScreen(
    viewModel: DepositRecommendationViewModel = viewModel()
) {
    Column{
        Text(
            text = stringResource(viewModel.uiState.currentQuestion.titleRes)
        )
        Row {

        }
    }


}