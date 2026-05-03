package com.example.financetracker.feature.deposit.domain.repository

import com.example.financetracker.feature.deposit.domain.model.Deposit
import kotlinx.coroutines.flow.Flow

interface DepositRepository {

    fun getAllDeposits(): Flow<List<Deposit>>

    suspend fun getDepositById(id: Long): Deposit

    suspend fun addDeposit(deposit: Deposit)

    suspend fun updateDeposit(deposit: Deposit)

    suspend fun deleteDeposit(deposit: Deposit)

    suspend fun deleteDepositById(id: Long)
}