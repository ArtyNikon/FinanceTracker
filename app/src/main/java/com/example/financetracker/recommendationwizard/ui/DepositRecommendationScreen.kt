package com.example.financetracker.recommendationwizard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financetracker.R
import com.example.financetracker.recommendationwizard.domain.Answer
import com.example.financetracker.recommendationwizard.domain.Question
import com.example.financetracker.recommendationwizard.ui.DepositRecommendationViewModel

@Composable
fun DepositRecommendationScreen(
    viewModel: DepositRecommendationViewModel = viewModel()
) {


}

//@Preview
@Composable
fun DepositRecommendationScreenPreview() {
    DepositRecommendationScreen()
}

@Composable
fun WizardWelcomeScreen() {
    Scaffold(
        topBar = {
            TextButton(onClick = {}) { Text("Закрыть") }
        },
        bottomBar = {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 24.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Начать",
                    style = MaterialTheme.typography.labelLarge,

                    )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(350.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.welcome_wizard),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Зададим один вопрос",
                modifier = Modifier
                    .align(Alignment.Start),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Left
            )
            Text(
                text = "Хотим узнать, интересуетесь ли вы криптовалютами, и рассказать о возможностях торговли",
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth(0.7f),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = Color.Gray
            )
        }
    }

}

@Composable
fun WizardQuestionScreen(
    onCloseClick: () -> Unit,
    viewModel: DepositRecommendationViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val currentQuestion = uiState.currentQuestion

    Scaffold(
        topBar = {
            WizardTopBar(
                progress = 0.5f,
                onCloseClick = onCloseClick
            )
        },
        bottomBar = {
            WizardBottomBar(
                onNextClick = viewModel::onNextClick,
                onBackClick = viewModel::onBackClick,
            )
        }
    ) { innerPadding ->
        WizardContent(
            currentQuestion = currentQuestion,
            modifier = Modifier.padding(innerPadding),
            onAnswerClick = { answer ->
                viewModel.onAnswerSelected(answer)
            },
            onCheckedChange = { answer, isChecked ->
                viewModel.onAnswerSelected(answer, isChecked)
            }
        )
    }
}

@Composable
fun WizardTopBar(
    progress: Float,
    onCloseClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextButton(
            onClick = onCloseClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text("Закрыть")
        }

        LinearProgressIndicator(
            progress = progress, //Тут пока логику прогресса не реализована
            modifier = Modifier
                .align(Alignment.Center)
                .width(100.dp)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun WizardBottomBar(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Продолжить",
                style = MaterialTheme.typography.labelLarge,

                )
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Назад",
                style = MaterialTheme.typography.labelLarge,

                )
        }
    }
}

@Composable
fun WizardContent(
    currentQuestion: Question,
    modifier: Modifier = Modifier,
    onAnswerClick: (String) -> Unit,
    onCheckedChange: (String, Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = currentQuestion.questionText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 12.dp, top = 12.dp)
        )

        when (val answer = currentQuestion.answer) {
            is Answer.RadioButton -> {
                Text(
                    text = "Выберите один вариант",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 12.dp)
                )

                RadioAnswersBlock(
                    answers = answer.list,
                    selected = answer.selected,
                    onAnswerClick = onAnswerClick
                )
            }

            is Answer.CheckBox -> {
                Text(
                    text = "Можете выбрать несколько вариантов",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 12.dp)
                )

                CheckBoxAnswersBlock(
                    answers = answer.list,
                    selected = answer.selected,
                    onCheckedChange = onCheckedChange
                )
            }
        }
    }
}

@Composable
fun RadioAnswersBlock(
    answers: List<String>,
    selected: String?,
    onAnswerClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(answers) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(vertical = 12.dp)
                    .clickable { onAnswerClick(item) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 12.dp)
                )

                RadioButton(
                    selected = selected == item,
                    onClick = { onAnswerClick(item) }
                )
            }
        }
    }
}

@Composable
fun CheckBoxAnswersBlock(
    answers: List<String>,
    selected: List<String>,
    onCheckedChange: (String, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(answers) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(vertical = 12.dp)
                    .clickable { onCheckedChange(item, !selected.contains(item)) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 12.dp)
                )

                Checkbox(
                    checked = selected.contains(item),
                    onCheckedChange = { isChecked ->
                        onCheckedChange(item, isChecked)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun WizardQuestionScreenPreview() {
    WizardQuestionScreen(
        onCloseClick = {},
        viewModel = viewModel()
    )
}

@Preview
@Composable
fun WizardWelcomeScreenPreview() {
    WizardWelcomeScreen()
}