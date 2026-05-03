package com.example.financetracker.feature.deposit.presentation.adddeposit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.financetracker.app.FinanceTrackerApplication
import com.example.financetracker.feature.deposit.domain.model.BankOption
import com.example.financetracker.feature.deposit.domain.model.Deposit
import com.example.financetracker.feature.deposit.domain.model.InterestPayoutType
import com.example.financetracker.feature.deposit.domain.model.ProductType
import com.example.financetracker.feature.deposit.domain.repository.DepositRepository
import kotlinx.coroutines.launch

class AddDepositBottomSheetViewModel(
    private val depositRepository: DepositRepository
) : ViewModel() {

    var uiState by mutableStateOf(AddDepositBottomSheetUiState())
        private set

    fun show() {
        uiState = uiState.copy(isVisible = true)
    }

    fun dismissAndReset() {
        uiState = AddDepositBottomSheetUiState()
    }

    fun onModeSelected(mode: AddDepositMode) {
        uiState = uiState.copy(
            selectedMode = mode,
            showValidationErrors = false
        )
    }

    fun onBankSelected(bank: BankOption) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(selectedBank = bank),
            showValidationErrors = false
        )
    }

    fun onProductTypeSelected(type: ProductType) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(
                productType = type
            ),
            showValidationErrors = false
        )
    }

    fun onAmountChanged(value: String) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(amount = value),
            showValidationErrors = false
        )
    }

    fun onRateChanged(value: String) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(rate = value),
            showValidationErrors = false
        )
    }

    fun onTermMonthsChanged(value: String) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(termMonths = value),
            showValidationErrors = false
        )
    }

    fun onOpenDateChanged(value: String) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(openDate = value),
            showValidationErrors = false
        )
    }

    fun onInterestPayoutTypeSelected(type: InterestPayoutType) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(interestPayoutType = type),
            showValidationErrors = false
        )
    }

    fun onTopUpChanged(value: Boolean) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(allowTopUp = value)
        )
    }

    fun onPartialWithdrawalChanged(value: Boolean) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(allowPartialWithdrawal = value)
        )
    }

    fun onCommentChanged(value: String) {
        uiState = uiState.copy(
            draft = uiState.draft.copy(comment = value)
        )
    }

    fun onSaveClick() {
        if (!isFormValid()) {
            uiState = uiState.copy(showValidationErrors = true)
            return
        }

        val draft = uiState.draft

        val openedAtMillis = parseDateMillis(draft.openDate)
            ?: System.currentTimeMillis()

        val bank = draft.selectedBank ?: return
        val payoutType = draft.interestPayoutType ?: return

        val deposit = Deposit(
            id = 0,
            bankName = bank.title,
            productType = draft.productType,
            rate = draft.rate.replace(",", ".").toDouble(),
            amount = draft.amount.replace(" ", "").toLong(),
            payoutType = payoutType,
            termMonths = draft.termMonths.toInt(),
            openedAtMillis = openedAtMillis
        )

        viewModelScope.launch {
            depositRepository.addDeposit(deposit)
            dismissAndReset()
        }
    }

    private fun isFormValid(): Boolean {
        val draft = uiState.draft

        return draft.selectedBank != null &&
                draft.amount.isNotBlank() &&
                draft.rate.isNotBlank() &&
                draft.termMonths.isNotBlank() &&
                draft.openDate.isNotBlank() &&
                draft.interestPayoutType != null
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val depositRepository =
                    (this[APPLICATION_KEY] as FinanceTrackerApplication)
                        .financeTrackerContainer
                        .depositRepository
                AddDepositBottomSheetViewModel(
                    depositRepository = depositRepository
                )
            }
        }
    }
}