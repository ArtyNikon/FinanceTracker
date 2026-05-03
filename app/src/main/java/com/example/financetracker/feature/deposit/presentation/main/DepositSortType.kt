package com.example.financetracker.feature.deposit.presentation.main

enum class DepositSortType(
    val title: String
) {
    BY_PROFIT("По доходности"),
    BY_RATE("По ставке"),
    BY_TERM("По сроку"),
    BY_AMOUNT("По сумме")
}