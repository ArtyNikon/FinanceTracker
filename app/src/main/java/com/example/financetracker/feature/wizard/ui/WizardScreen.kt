package com.example.financetracker.feature.wizard.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financetracker.R
import com.example.financetracker.core.ui.AppDimens
import com.example.financetracker.feature.wizard.domain.Answer
import com.example.financetracker.feature.wizard.domain.Question
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.delay

@Composable
fun WizardWelcomeScreen(
    onNextClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppDimens.ScreenHorizontalPadding,
                        vertical = AppDimens.ScreenTopPadding
                    ),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCloseClick) {
                    Text("Закрыть")
                }
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Button(
                        onClick = onNextClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(AppDimens.CardCornerRadius)
                    ) {
                        Text(
                            text = "Начать",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                text = "Зададим пару вопросов",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Узнаем, что для вас важно, и подберём подходящие финансовые решения.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun WizardWelcomeScreenPreview() {
    FinanceTrackerTheme {
        WizardWelcomeScreen(
            onNextClick = {},
            onCloseClick = {}
        )
    }
}

@Composable
fun WizardQuestionScreen(
    onCloseClick: () -> Unit,
    onFinishClick: () -> Unit,
    viewModel: WizardViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val currentQuestion = uiState.currentQuestion
    val isLastQuestion = uiState.currentQuestionIndex == uiState.questions.lastIndex

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            WizardTopBar(
                progress = uiState.progress,
                onCloseClick = onCloseClick,
                onBackClick = viewModel::onBackClick,
                isLastQuestion = isLastQuestion
            )
        },
        bottomBar = {
            WizardPrimaryBottomBar(
                buttonText = if (isLastQuestion) "Завершить" else "Продолжить",
                onClick = if (isLastQuestion) onFinishClick else viewModel::onNextClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    horizontal = AppDimens.ScreenHorizontalPadding,
                    vertical = AppDimens.ScreenTopPadding
                )
        ) {
            WizardQuestionCard(
                currentQuestion = currentQuestion,
                showError = uiState.showError
            )

            Spacer(modifier = Modifier.height(AppDimens.SectionSpacingMedium))

            when (val answer = currentQuestion.answer) {
                is Answer.RadioButton -> {
                    WizardRadioAnswersList(
                        answers = answer.list,
                        selected = answer.selected,
                        showError = uiState.showError,
                        onAnswerClick = { selectedAnswer ->
                            viewModel.onAnswerSelected(selectedAnswer)
                        }
                    )
                }

                is Answer.CheckBox -> {
                    WizardCheckBoxAnswersList(
                        answers = answer.list,
                        selected = answer.selected,
                        showError = uiState.showError,
                        onCheckedChange = { selectedAnswer, isChecked ->
                            viewModel.onAnswerSelected(selectedAnswer, isChecked)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun WizardQuestionScreenPreview() {
    FinanceTrackerTheme {
        WizardQuestionScreen(
            onCloseClick = {},
            onFinishClick = {}
        )
    }
}

@Composable
fun WizardFinishScreen(
    onCloseClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(3000)
        onCloseClick()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 3.dp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Подбираем рекомендации...",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun WizardFinishScreenPreview() {
    FinanceTrackerTheme {
        WizardFinishScreen(
            onCloseClick = {}
        )
    }
}

@Composable
private fun WizardTopBar(
    progress: Float,
    onCloseClick: () -> Unit,
    onBackClick: () -> Unit,
    isLastQuestion: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.ScreenHorizontalPadding, vertical = 12.dp)
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(44.dp)
                .clickable(onClick = onBackClick),
            shape = CircleShape,
            tonalElevation = 1.dp,
            shadowElevation = 1.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад"
                )
            }
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .align(Alignment.Center)
                .width(170.dp)
                .height(8.dp)
                .clip(RoundedCornerShape(999.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        if (!isLastQuestion) {
            TextButton(
                onClick = onCloseClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text("Закрыть")
            }
        }
    }
}

@Composable
private fun WizardPrimaryBottomBar(
    buttonText: String,
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(AppDimens.CardCornerRadius)
                ) {
                    Text(
                        text = buttonText,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun WizardQuestionCard(
    currentQuestion: Question,
    showError: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.CardCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.CardInnerPadding)
        ) {
            Text(
                text = currentQuestion.questionText,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (currentQuestion.answer) {
                    is Answer.RadioButton -> "Выберите один вариант"
                    is Answer.CheckBox -> "Выберите один или несколько вариантов"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (showError) Color(0xFFD32F2F) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun WizardRadioAnswersList(
    answers: List<String>,
    selected: String?,
    showError: Boolean,
    onAnswerClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.SectionSpacingSmall)
    ) {
        answers.forEach { item ->
            WizardAnswerCard(
                title = item,
                selected = selected == item,
                showError = showError,
                onClick = { onAnswerClick(item) },
                trailing = {
                    RadioButton(
                        selected = selected == item,
                        onClick = null
                    )
                }
            )
        }
    }
}

@Composable
private fun WizardCheckBoxAnswersList(
    answers: List<String>,
    selected: List<String>,
    showError: Boolean,
    onCheckedChange: (String, Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.SectionSpacingSmall)
    ) {
        answers.forEach { item ->
            val isChecked = selected.contains(item)

            WizardAnswerCard(
                title = item,
                selected = isChecked,
                showError = showError,
                onClick = { onCheckedChange(item, !isChecked) },
                trailing = {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = null
                    )
                }
            )
        }
    }
}

@Composable
private fun WizardAnswerCard(
    title: String,
    selected: Boolean,
    showError: Boolean,
    onClick: () -> Unit,
    trailing: @Composable () -> Unit
) {
    val borderColor = when {
        selected -> MaterialTheme.colorScheme.primary
        showError -> Color(0xFFD32F2F)
        else -> MaterialTheme.colorScheme.outlineVariant
    }

    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.06f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 14.dp, end = 10.dp, bottom = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            trailing()
        }
    }
}