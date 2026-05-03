package com.example.financetracker.feature.deposit.data.repository

import com.example.financetracker.feature.deposit.data.local.DepositDAO
import com.example.financetracker.feature.deposit.data.mapper.toDomain
import com.example.financetracker.feature.deposit.data.mapper.toEntity
import com.example.financetracker.feature.deposit.domain.model.Deposit
import com.example.financetracker.feature.deposit.domain.repository.DepositRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DepositRepositoryImpl(
    private val depositDao: DepositDAO
) : DepositRepository {

    override fun getAllDeposits(): Flow<List<Deposit>> {
        return depositDao.getAllDeposits().map { entities ->
            entities
                .map { entity ->
                    entity.toDomain()
                }
        }
    }

    override suspend fun getDepositById(id: Long): Deposit {
        return depositDao.getDepositById(id).toDomain()
    }

    override suspend fun addDeposit(deposit: Deposit) {
        depositDao.addDeposit(deposit.toEntity())
    }

    override suspend fun updateDeposit(deposit: Deposit) {
        depositDao.updateDeposit(deposit.toEntity())
    }

    override suspend fun deleteDeposit(deposit: Deposit) {
        depositDao.deleteDeposit(deposit.toEntity())
    }

    override suspend fun deleteDepositById(id: Long) {
        depositDao.deleteDepositById(id)
    }

}