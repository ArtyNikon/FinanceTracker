package com.example.financetracker.feature.deposit.domain.model

data class Deposit(
    val id: Long,
    val bankName: String,
    val productType: ProductType,
    val rate: Double,
    val amount: Long,
    val payoutType: InterestPayoutType,
    val termMonths: Int,
    val openedAtMillis: Long
)