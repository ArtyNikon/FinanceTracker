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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financetracker.R
import com.example.financetracker.core.ui.AppDimens
import com.example.financetracker.feature.wizard.domain.Answer
import com.example.financetracker.feature.wizard.domain.Question
import com.example.financetracker.ui.theme.AppColors
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.delay

@Composable
fun WizardWelcomeScreen(
    onNextClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            WizardBottomActions(
                buttonText = "Продолжить",
                onPrimaryClick = onNextClick,
                onCancelClick = onCloseClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
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
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Узнаем, что для вас важно, и подберём подходящие финансовые решения.",
                style = MaterialTheme.typography.bodyLarge,
                color = AppColors.TextSecondary
            )
        }
    }
}

@Composable
private fun WizardBottomActions(
    buttonText: String,
    onPrimaryClick: () -> Unit,
    onCancelClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Button(
            onClick = onPrimaryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.PrimaryBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = buttonText,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (onCancelClick != null) {
            TextButton(
                onClick = onCancelClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Отмена",
                    color = AppColors.PrimaryBlue
                )
            }
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
    val currentAnswer = currentQuestion.answer
    val isLastQuestion = uiState.currentQuestionIndex == uiState.questions.lastIndex

    var showFinishLoading by rememberSaveable {
        mutableStateOf(false)
    }

    val bottomButtonText = if (isLastQuestion) {
        "Завершить"
    } else {
        "Продолжить"
    }

    val onBottomButtonClick = {
        if (showFinishLoading) {
            Unit
        } else if (isLastQuestion) {
            showFinishLoading = true
        } else {
            viewModel.onNextClick()
        }
    }

    LaunchedEffect(showFinishLoading) {
        if (showFinishLoading) {
            delay(3000)
            onFinishClick()
        }
    }

    Scaffold(
        containerColor = Color.White,
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
                buttonText = bottomButtonText,
                onClick = onBottomButtonClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = AppDimens.ScreenHorizontalPadding),
            contentPadding = PaddingValues(
                top = AppDimens.ScreenTopPadding,
                bottom = AppDimens.SectionSpacingLarge
            ),
            verticalArrangement = Arrangement.spacedBy(AppDimens.SectionSpacingMedium)
        ) {
            item {
                WizardQuestionCard(
                    currentQuestion = currentQuestion,
                    showError = uiState.showError
                )
            }

            when (currentAnswer) {
                is Answer.RadioButton -> {
                    items(currentAnswer.list) { item ->
                        WizardAnswerCard(
                            title = item,
                            selected = currentAnswer.selected == item,
                            showError = uiState.showError,
                            onClick = {
                                viewModel.onAnswerSelected(item)
                            },
                            trailing = {
                                RadioButton(
                                    selected = currentAnswer.selected == item,
                                    onClick = null,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = AppColors.PrimaryBlue,
                                        unselectedColor = AppColors.TextSecondary
                                    )
                                )
                            }
                        )
                    }
                }

                is Answer.CheckBox -> {
                    items(currentAnswer.list) { item ->
                        val isChecked = currentAnswer.selected.contains(item)

                        WizardAnswerCard(
                            title = item,
                            selected = isChecked,
                            showError = uiState.showError,
                            onClick = {
                                viewModel.onAnswerSelected(item, !isChecked)
                            },
                            trailing = {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = null,
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = AppColors.PrimaryBlue,
                                        uncheckedColor = AppColors.TextSecondary,
                                        checkmarkColor = Color.White
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    if (showFinishLoading) {
        RecommendationLoadingDialog()
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
private fun RecommendationLoadingDialog() {
    Dialog(
        onDismissRequest = {}
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
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
                        .background(AppColors.PrimaryBlueLight),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        strokeWidth = 3.dp,
                        color = AppColors.PrimaryBlue
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Подбираем рекомендации...",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview
@Composable
fun RecommendationLoadingDialogPreview(){
    RecommendationLoadingDialog()
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
            .background(Color.White)
            .padding(horizontal = AppDimens.ScreenHorizontalPadding, vertical = 12.dp)
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(44.dp)
                .clickable(onClick = onBackClick),
            shape = CircleShape,
            color = Color.White,
            tonalElevation = 1.dp,
            shadowElevation = 1.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = AppColors.PrimaryBlue
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
            color = AppColors.ProgressGreen,
            trackColor = AppColors.ProgressTrack
        )

        if (!isLastQuestion) {
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(44.dp)
                    .clickable(onClick = onCloseClick),
                shape = CircleShape,
                color = Color.White,
                tonalElevation = 1.dp,
                shadowElevation = 1.dp
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Закрыть",
                        tint = AppColors.TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun WizardPrimaryBottomBar(
    buttonText: String,
    onClick: () -> Unit
) {
    WizardBottomActions(
        buttonText = buttonText,
        onPrimaryClick = onClick,
        onCancelClick = null
    )
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
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.CardInnerPadding)
        ) {
            Text(
                text = currentQuestion.questionText,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (currentQuestion.answer) {
                    is Answer.RadioButton -> "Выберите один вариант"
                    is Answer.CheckBox -> "Выберите один или несколько вариантов"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (showError) {
                    MaterialTheme.colorScheme.error
                } else {
                    AppColors.TextSecondary
                }
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
        selected -> AppColors.PrimaryBlue
        showError -> MaterialTheme.colorScheme.error
        else -> AppColors.Divider
    }

    val backgroundColor = if (selected) {
        AppColors.PrimaryBlueLight
    } else {
        Color.White
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
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            trailing()
        }
    }
}