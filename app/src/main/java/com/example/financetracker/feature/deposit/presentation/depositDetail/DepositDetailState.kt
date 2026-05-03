package com.example.financetracker.feature.deposit.presentation.depositDetail

import com.example.financetracker.feature.deposit.domain.model.Deposit

data class DepositDetailState(
    val deposit: Deposit? = null,

    val availableIncomePeriods: List<Int> = emptyList(),
    val selectedIncomePeriodMonths: Int = 1,

    val amountText: String = "",
    val rateText: String = "",
    val termText: String = "",
    val openedDateText: String = "",
    val payoutTypeText: String = "",

    val approximateIncomeText: String = "",
    val currentEarningsText: String = ""
)