package com.example.financetracker.feature.cashback.domain

data class Cashback(
    val id: Int,
    val category: CashbackCategory,
    val percent: Double,
    val bank: String,
    val iconId: String,
    val conditions: CashbackConditions,
)