package com.example.financetracker.feature.deposit.presentation.main

import com.example.financetracker.feature.deposit.domain.model.Deposit
import com.example.financetracker.feature.deposit.domain.model.RecommendedDeposit
import com.example.financetracker.feature.deposit.domain.util.toMoney

data class DepositState(
    val deposits: List<Deposit> = emptyList(),
    val recommendations: List<RecommendedDeposit> = emptyList(),
    val selectedSort: DepositSortType = DepositSortType.BY_PROFIT
) {
    val totalAmount: String
        get() = deposits.sumOf { it.amount }.toMoney()

    val averageRate: String
        get() = if (deposits.isEmpty()) {
            "0%"
        } else {
            val avg = deposits.map { it.rate }.average()
            "${String.format("%.1f", avg).replace('.', ',')}%"
        }
}