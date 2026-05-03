package com.example.financetracker.core.di

import android.content.Context
import com.example.financetracker.core.database.FinanceTrackerDatabase
import com.example.financetracker.feature.deposit.data.repository.DepositRepositoryImpl
import com.example.financetracker.feature.deposit.domain.repository.DepositRepository

class FinanceTrackerContainer(
    private val context: Context
) {
    private val financeTrackerDatabase: FinanceTrackerDatabase by lazy {
        FinanceTrackerDatabase.getDataBase(context)
    }

    val depositRepository: DepositRepository by lazy {
        DepositRepositoryImpl(
            depositDao = financeTrackerDatabase.depositDao()
        )
    }
}