package com.example.financetracker.feature.deposit.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.financetracker.app.FinanceTrackerApplication
import com.example.financetracker.feature.deposit.domain.model.Deposit
import com.example.financetracker.feature.deposit.domain.repository.DepositRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class DepositViewModel(
    private val depositRepository: DepositRepository
) : ViewModel() {

    private val selectedSort = MutableStateFlow(DepositSortType.BY_AMOUNT)

    val state: StateFlow<DepositState> =
        combine(
            depositRepository.getAllDeposits(),
            selectedSort
        ) { deposits, sortType ->
            val sortedDeposits = when (sortType) {
                DepositSortType.BY_RATE -> deposits.sortedByDescending { it.rate }
                DepositSortType.BY_TERM -> deposits.sortedByDescending { it.termMonths }
                DepositSortType.BY_AMOUNT -> deposits.sortedByDescending { it.amount }
                DepositSortType.BY_PROFIT -> deposits
            }

            DepositState(
                deposits = sortedDeposits,
                recommendations = emptyList(),
                selectedSort = sortType
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DepositState()
        )

    fun onSortSelected(sortType: DepositSortType) {
        selectedSort.value = sortType
    }

    fun onDepositClick(deposit: Deposit) {
        // TODO
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val depositRepository =
                    (this[APPLICATION_KEY] as FinanceTrackerApplication)
                        .financeTrackerContainer
                        .depositRepository
                DepositViewModel(
                    depositRepository = depositRepository
                )
            }
        }
    }
}