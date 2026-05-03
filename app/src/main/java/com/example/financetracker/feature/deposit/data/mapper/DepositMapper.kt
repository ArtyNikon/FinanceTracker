package com.example.financetracker.feature.deposit.data.mapper

import com.example.financetracker.feature.deposit.data.local.DepositEntity
import com.example.financetracker.feature.deposit.domain.model.Deposit
import com.example.financetracker.feature.deposit.domain.model.InterestPayoutType
import com.example.financetracker.feature.deposit.domain.model.ProductType

fun DepositEntity.toDomain(): Deposit {
    return Deposit(
        id = id,
        bankName = bankName,
        productType = productType.toProductType(),
        rate = rate,
        amount = amount,
        payoutType = payoutType.toInterestPayoutType(),
        termMonths = termMonths,
        openedAtMillis = openedAtMillis,
    )
}

fun Deposit.toEntity(): DepositEntity {
    return DepositEntity(
        id = id,
        bankName = bankName,
        productType = productType.name,
        rate = rate,
        amount = amount,
        payoutType = payoutType.name,
        termMonths = termMonths,
        openedAtMillis = openedAtMillis,
    )
}

private fun String.toInterestPayoutType(): InterestPayoutType {
    return runCatching {
        InterestPayoutType.valueOf(this)
    }.getOrDefault(InterestPayoutType.MONTHLY)
}

private fun String.toProductType(): ProductType {
    return runCatching {
        ProductType.valueOf(this)
    }.getOrDefault(ProductType.DEPOSIT)
}