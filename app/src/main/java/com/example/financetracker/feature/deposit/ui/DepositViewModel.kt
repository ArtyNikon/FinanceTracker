package com.example.financetracker.feature.deposit.ui

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.financetracker.feature.deposit.domain.Deposit
import com.example.financetracker.feature.deposit.domain.RecommendedDeposit


class DepositViewModel : ViewModel() {

    var uiState by mutableStateOf(
        DepositState(
            deposits = sampleDeposits()
        )
    )
        private set

    init {
        sortDeposits(uiState.selectedSort)
    }

    fun onSortSelected(sortType: DepositSortType) {
        uiState = uiState.copy(selectedSort = sortType)
        sortDeposits(sortType)
    }

    fun onAddDepositClick() {
        // TODO
    }

    fun onDepositClick(deposit: Deposit) {
        // TODO
    }

    private fun sortDeposits(sortType: DepositSortType) {
        val sorted = when (sortType) {
            DepositSortType.BY_PROFIT -> uiState.deposits.sortedByDescending { it.expectedIncome }
            DepositSortType.BY_RATE -> uiState.deposits.sortedByDescending { it.rate }
            DepositSortType.BY_TERM -> uiState.deposits.sortedByDescending { it.term }
            DepositSortType.BY_AMOUNT -> uiState.deposits.sortedByDescending { it.amount }
        }

        uiState = uiState.copy(deposits = sorted)
    }
}

private fun sampleDeposits(): List<Deposit> = listOf(
    Deposit(
        id = "1",
        bankName = "Сбер",
        productName = "Накопительный счёт",
        rate = 17.2,
        amount = 120_000,
        term = 12,
        expectedIncome = 20_640,
        daysLeft = 210,
    ),
    Deposit(
        id = "2",
        bankName = "Т-Банк",
        productName = "Вклад",
        rate = 18.1,
        amount = 200_000,
        term = 9,
        expectedIncome = 30_000,
        daysLeft = 120,
    ),
    Deposit(
        id = "3",
        bankName = "Озон Банк",
        productName = "Цифровые продукты",
        rate = 16.5,
        amount = 220_000,
        term = 6,
        expectedIncome = 16_830,
        daysLeft = 60,
    )
)