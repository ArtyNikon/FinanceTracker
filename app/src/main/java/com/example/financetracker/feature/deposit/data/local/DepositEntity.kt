package com.example.financetracker.feature.deposit.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deposits")
data class DepositEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("bank_name") val bankName: String,
    @ColumnInfo("product_type") val productType: String,
    val rate: Double,
    val amount: Long,
    @ColumnInfo("payout_type") val payoutType: String,
    @ColumnInfo("term_months") val termMonths: Int,
    @ColumnInfo("opened_at_millis") val openedAtMillis: Long
)