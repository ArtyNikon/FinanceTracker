package com.example.financetracker.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.financetracker.feature.deposit.data.local.DepositDAO
import com.example.financetracker.feature.deposit.data.local.DepositEntity

@Database(
    entities = [DepositEntity::class],
    version = 1,
    exportSchema = true
)
abstract class FinanceTrackerDatabase : RoomDatabase() {

    abstract fun depositDao(): DepositDAO

    companion object {
        @Volatile
        private var dataBase: FinanceTrackerDatabase? = null

        fun getDataBase(context: Context): FinanceTrackerDatabase {
            return dataBase ?: synchronized(this) {
                val newDataBase = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = FinanceTrackerDatabase::class.java,
                    name = "finance_tracker_database"
                ).build()

                dataBase = newDataBase
                newDataBase
            }
        }
    }
}