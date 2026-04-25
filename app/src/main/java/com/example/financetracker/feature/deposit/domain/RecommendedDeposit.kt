package com.example.financetracker.feature.deposit.domain

data class RecommendedDeposit(
    val id: String,
    val bankName: String,
    val productName: String,
    val rate: Double,
    val term: Int,
    val minAmount: Long,
)