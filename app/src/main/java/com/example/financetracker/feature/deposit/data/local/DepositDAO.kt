package com.example.financetracker.feature.deposit.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DepositDAO {

    @Query("SELECT * FROM deposits")
    fun getAllDeposits(): Flow<List<DepositEntity>>

    @Query("SELECT * FROM deposits WHERE id = :id")
    suspend fun getDepositById(id: Long): DepositEntity

    @Insert
    suspend fun addDeposit(deposit: DepositEntity)

    @Update
    suspend fun updateDeposit(deposit: DepositEntity)

    @Delete
    suspend fun deleteDeposit(deposit: DepositEntity)

    @Query("DELETE FROM deposits WHERE id = :id")
    suspend fun deleteDepositById(id: Long)
}