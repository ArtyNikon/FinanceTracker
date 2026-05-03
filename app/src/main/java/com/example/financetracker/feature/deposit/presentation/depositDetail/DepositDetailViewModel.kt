package com.example.financetracker.feature.deposit.presentation.depositDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.financetracker.app.FinanceTrackerApplication
import com.example.financetracker.feature.deposit.domain.model.Deposit
import com.example.financetracker.feature.deposit.domain.repository.DepositRepository
import com.example.financetracker.feature.deposit.domain.util.toMoney
import com.example.financetracker.feature.deposit.presentation.main.DepositViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DepositDetailViewModel(
    private val depositRepository: DepositRepository,
    private val id: Long
) : ViewModel() {
    private val _state = MutableStateFlow(DepositDetailState())
    val state: StateFlow<DepositDetailState> = _state.asStateFlow()

    init {
        loadDeposit()
    }

    private fun loadDeposit() {
        viewModelScope.launch {
            val deposit = depositRepository.getDepositById(id)

            val periods = buildIncomePeriods(deposit.termMonths)
            val selectedPeriod = periods.lastOrNull() ?: 1

            _state.value = DepositDetailState(
                deposit = deposit,
                availableIncomePeriods = periods,
                selectedIncomePeriodMonths = selectedPeriod,
                approximateIncomeText = calculateApproximateIncome(
                    deposit = deposit,
                    months = selectedPeriod
                )
                    .toMoney(),
                currentEarningsText = calculateCurrentEarnings(deposit)
            )
        }
    }

    fun onIncomePeriodSelected(months: Int) {
        val deposit = _state.value.deposit ?: return

        _state.value = _state.value.copy(
            selectedIncomePeriodMonths = months,
            approximateIncomeText = calculateApproximateIncome(
                deposit = deposit,
                months = months
            ).toMoney()
        )
    }

    fun onEditClick() {

    }

    fun onDeleteClick(onDeleted: () -> Unit) {
        viewModelScope.launch {
            depositRepository.deleteDepositById(id)
            onDeleted()
        }
    }

    private fun buildIncomePeriods(termMonths: Int): List<Int> {
        return listOf(1, 3, 6, termMonths)
            .filter { it <= termMonths }
            .distinct()
    }

    private fun calculateApproximateIncome(
        deposit: Deposit,
        months: Int
    ): Long {
        // исправить формулу расчёта
        return ((deposit.amount * deposit.rate / 100) / 12 * months).toLong()
    }

    private fun calculateCurrentEarnings(
        deposit: Deposit
    ): String {
        // исправить формулу расчёта
        return "0"
    }

    companion object {
        fun Factory(id: Long): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val depositRepository =
                    (this[APPLICATION_KEY] as FinanceTrackerApplication)
                        .financeTrackerContainer
                        .depositRepository
                DepositDetailViewModel(
                    depositRepository = depositRepository,
                    id = id
                )
            }
        }
    }
}