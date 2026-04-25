package com.example.financetracker.feature.deposit.ui

import com.example.financetracker.feature.deposit.domain.Deposit
import com.example.financetracker.feature.deposit.domain.RecommendedDeposit
import com.example.financetracker.feature.deposit.domain.toMoney

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

    val expectedIncome: String
        get() = deposits.sumOf { it.expectedIncome }.toMoney()
}

enum class DepositSortType(
    val title: String
) {
    BY_PROFIT("По доходности"),
    BY_RATE("По ставке"),
    BY_TERM("По сроку"),
    BY_AMOUNT("По сумме")
}