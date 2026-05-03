package com.example.financetracker.feature.deposit.presentation.adddeposit

import com.example.financetracker.feature.deposit.domain.model.BankOption
import com.example.financetracker.feature.deposit.data.mock.BankRepositoryMock

data class AddDepositBottomSheetUiState(
    val isVisible: Boolean = false,
    val selectedMode: AddDepositMode = AddDepositMode.MANUAL,
    val availableBanks: List<BankOption> = BankRepositoryMock.getBanks(),
    val draft: ManualDepositDraft = ManualDepositDraft(),
    val showValidationErrors: Boolean = false
) {
    val isManualMode: Boolean
        get() = selectedMode == AddDepositMode.MANUAL
}