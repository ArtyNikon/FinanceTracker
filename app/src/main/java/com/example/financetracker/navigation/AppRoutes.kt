package com.example.financetracker.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object Home : AppRoute

    @Serializable
    data object Goals : AppRoute

    @Serializable
    data object Deposit : AppRoute

    @Serializable
    data object Cashback : AppRoute

    @Serializable
    data object Profile : AppRoute
}

@Serializable
data object DepositWizardGraph

sealed interface DepositWizardRoute {
    @Serializable
    data object Welcome : DepositWizardRoute

    @Serializable
    data object Question : DepositWizardRoute

    @Serializable
    data object Finish : DepositWizardRoute
}