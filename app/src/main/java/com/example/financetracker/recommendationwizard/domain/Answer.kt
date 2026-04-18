package com.example.financetracker.recommendationwizard.domain

sealed class Answer {
    abstract val list: List<String>

    data class RadioButton(
        override val list: List<String>,
        var selected: String?,

        ) : Answer()

    data class CheckBox(
        override val list: List<String>,
        val selected: List<String>
    ) : Answer()
}