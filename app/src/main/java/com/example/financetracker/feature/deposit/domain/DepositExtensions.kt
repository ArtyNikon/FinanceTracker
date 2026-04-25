package com.example.financetracker.feature.deposit.domain

import java.util.Locale

fun Double.toRate(): String =
    "${String.format(Locale.getDefault(), "%.1f", this).replace('.', ',')}%"

fun Long.toMoney(): String =
    "%,d ₽".format(this).replace(',', ' ')

fun Int.toMoney(): String =
    this.toLong().toMoney()

fun Int.toMonths(): String =
    "$this мес"

fun Int.toDays(): String =
    "$this дней"