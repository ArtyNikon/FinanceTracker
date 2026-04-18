package com.example.financetracker.recommendationwizard.domain

object WizardQuestionsProvider {

    fun getQuestions(): List<Question> {
        return listOf(
            Question(
                questionText = "Вопрос 1",
                answer = Answer.RadioButton(
                    list = listOf("Ответ 1", "Ответ 2"),
                    selected = null,
                )
            ),
            Question(
                questionText = "Вопрос 2",
                answer = Answer.RadioButton(
                    list = listOf("Ответ 1", "Ответ 2", "Ответ 3"),
                    selected = null,
                )
            ),
            Question(
                questionText = "Вопрос 3",
                answer = Answer.CheckBox(
                    list = listOf("Ответ 1", "Ответ 2", "Ответ 3", "Ответ 4"),
                    selected = emptyList(),
                )
            ),
            Question(
                questionText = "Вопрос 4",
                answer = Answer.CheckBox(
                    list = listOf(
                        "Ответ 1",
                        "Ответ 2",
                        "Ответ 3",
                        "Ответ 4",
                        "Ответ 5",
                        "Ответ 6",
                        "Ответ 7",
                        "Ответ 8",
                        "Ответ 9",
                        "Ответ 10"
                    ),
                    selected = emptyList(),
                )
            ),
            Question(
                questionText = "Вопрос 5",
                answer = Answer.RadioButton(
                    list = listOf("Ответ 1", "Ответ 2", "Ответ 3", "Ответ 4"),
                    selected = null,
                )
            ),
            Question(
                questionText = "Вопрос 6",
                answer = Answer.RadioButton(
                    list = listOf("Ответ 1", "Ответ 2"),
                    selected = null,
                )
            ),
            Question(
                questionText = "Вопрос 4",
                answer = Answer.CheckBox(
                    list = listOf(
                        "Ответ 1",
                        "Ответ 2",
                        "Ответ 3",
                        "Ответ 4",
                        "Ответ 5",
                        "Ответ 6",
                        "Ответ 7"
                    ),
                    selected = emptyList(),
                )
            )
        )
    }
}