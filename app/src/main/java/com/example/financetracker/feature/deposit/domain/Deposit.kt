package com.example.financetracker.feature.deposit.domain

data class Deposit(
    val id: String,
    val bankName: String,
    val productName: String,
    val rate: Double,
    val amount: Long,
    val term: Int,
    val expectedIncome: Long,
    val daysLeft: Int,
)