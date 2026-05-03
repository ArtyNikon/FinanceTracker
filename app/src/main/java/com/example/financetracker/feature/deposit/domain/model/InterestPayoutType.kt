package com.example.financetracker.feature.deposit.domain.model

enum class InterestPayoutType(val title: String) {
    DAILY_BALANCE("На ежедневный остаток"),
    MONTHLY("Ежемесячно"),
    END_OF_TERM("В конце срока")
}