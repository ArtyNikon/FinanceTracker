package com.example.financetracker.feature.deposit.data.mock

import com.example.financetracker.feature.deposit.domain.model.BankOption

object BankRepositoryMock {
    fun getBanks(): List<BankOption> {
        return listOf(
            BankOption(id = "tbank", title = "Т-Банк"),
            BankOption(id = "sber", title = "СберБанк"),
            BankOption(id = "vtb", title = "ВТБ"),
            BankOption(id = "alpha", title = "Альфа-Банк"),
            BankOption(id = "ozon", title = "Озон Банк")
        )
    }
}