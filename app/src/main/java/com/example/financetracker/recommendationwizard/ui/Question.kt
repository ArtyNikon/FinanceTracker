package com.example.financetracker.recommendationwizard.ui

import androidx.annotation.StringRes
import com.example.financetracker.R

enum class Question(@StringRes val titleRes: Int) {
    DepositGoal(R.string.question_one),
    ExtraInfo1(R.string.question_two),
    ExtraInfo2(R.string.question_three),
    ExtraInfo3(R.string.question_four),
    ExtraInfo4(R.string.question_five),
}