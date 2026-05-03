package com.example.financetracker.feature.deposit.presentation.adddeposit

import com.example.financetracker.feature.deposit.domain.model.BankOption
import com.example.financetracker.feature.deposit.domain.model.InterestPayoutType
import com.example.financetracker.feature.deposit.domain.model.ProductType

data class ManualDepositDraft(
    val selectedBank: BankOption? = null,
    val productType: ProductType = ProductType.DEPOSIT,
    val amount: String = "",
    val rate: String = "",
    val termMonths: String = "",
    val openDate: String = "",
    val interestPayoutType: InterestPayoutType? = null,
    val allowTopUp: Boolean = false,
    val allowPartialWithdrawal: Boolean = false,
    val comment: String = ""
)