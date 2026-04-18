package com.example.financetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.financetracker.recommendationwizard.ui.DepositRecommendationScreen
import com.example.financetracker.recommendationwizard.ui.WizardQuestionScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DepositRecommendationScreen()
        }
    }
}