package com.example.financetracker.feature.deposit.presentation.adddeposit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.feature.deposit.domain.model.BankOption
import com.example.financetracker.feature.deposit.domain.model.DepositProductType
import com.example.financetracker.feature.deposit.domain.model.InterestPayoutType
import com.example.financetracker.feature.deposit.domain.model.ProductType
import com.example.financetracker.ui.theme.AppColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDepositBottomSheet(
    viewModel: AddDepositBottomSheetViewModel
) {
    val uiState = viewModel.uiState

    if (!uiState.isVisible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = viewModel::dismissAndReset,
        sheetState = sheetState,
        dragHandle = {},
        containerColor = Color.White,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        modifier = Modifier.fillMaxHeight(0.96f)
    ) {
        AddDepositBottomSheetContent(
            uiState = uiState,
            onDismiss = viewModel::dismissAndReset,
            onModeSelected = viewModel::onModeSelected,
            onBankSelected = viewModel::onBankSelected,
            onProductTypeSelected = viewModel::onProductTypeSelected,
            onAmountChange = viewModel::onAmountChanged,
            onRateChange = viewModel::onRateChanged,
            onTermMonthsChange = viewModel::onTermMonthsChanged,
            onOpenDateChange = viewModel::onOpenDateChanged,
            onInterestPayoutTypeSelected = viewModel::onInterestPayoutTypeSelected,
            onTopUpChange = viewModel::onTopUpChanged,
            onPartialWithdrawalChange = viewModel::onPartialWithdrawalChanged,
            onCommentChange = viewModel::onCommentChanged,
            onSaveClick = viewModel::onSaveClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDepositBottomSheetContent(
    uiState: AddDepositBottomSheetUiState,
    onDismiss: () -> Unit,
    onModeSelected: (AddDepositMode) -> Unit,
    onBankSelected: (BankOption) -> Unit,
    onProductTypeSelected: (ProductType) -> Unit,
    onAmountChange: (String) -> Unit,
    onRateChange: (String) -> Unit,
    onTermMonthsChange: (String) -> Unit,
    onOpenDateChange: (String) -> Unit,
    onInterestPayoutTypeSelected: (InterestPayoutType) -> Unit,
    onTopUpChange: (Boolean) -> Unit,
    onPartialWithdrawalChange: (Boolean) -> Unit,
    onCommentChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    var showBankDialog by remember { mutableStateOf(false) }
    var showTermDialog by remember { mutableStateOf(false) }
    var showOpenDatePicker by remember { mutableStateOf(false) }
    var showPayoutDialog by remember { mutableStateOf(false) }
    var showCommentDialog by remember { mutableStateOf(false) }

    val draft = uiState.draft

    if (showBankDialog) {
        BankPickerDialog(
            banks = uiState.availableBanks,
            selectedBank = draft.selectedBank,
            onDismiss = { showBankDialog = false },
            onSelected = {
                onBankSelected(it)
                showBankDialog = false
            }
        )
    }

    if (showTermDialog) {
        MonthPickerDialog(
            selectedMonth = draft.termMonths,
            onDismiss = { showTermDialog = false },
            onSelected = {
                onTermMonthsChange(it.toString())
                showTermDialog = false
            }
        )
    }

    if (showOpenDatePicker) {
        DepositDatePickerDialog(
            value = draft.openDate,
            onDismiss = { showOpenDatePicker = false },
            onDateSelected = {
                onOpenDateChange(it)
                showOpenDatePicker = false
            }
        )
    }

    if (showPayoutDialog) {
        InterestPayoutPickerDialog(
            selected = draft.interestPayoutType,
            onDismiss = { showPayoutDialog = false },
            onSelected = {
                onInterestPayoutTypeSelected(it)
                showPayoutDialog = false
            }
        )
    }

    if (showCommentDialog) {
        CommentEditDialog(
            value = draft.comment,
            onDismiss = { showCommentDialog = false },
            onSave = {
                onCommentChange(it)
                showCommentDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
            .navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
        ) {
            item {
                ModeSwitcher(
                    selectedMode = uiState.selectedMode,
                    onModeSelected = onModeSelected
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            if (uiState.isManualMode) {
                item {
                    DepositFormRow(
                        icon = Icons.Outlined.AccountBalance,
                        title = "Банк",
                        errorText = if (
                            uiState.showValidationErrors &&
                            draft.selectedBank == null
                        ) {
                            "Выберите банк"
                        } else null,
                        onClick = { showBankDialog = true }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = draft.selectedBank?.title ?: "Выбрать",
                                color = if (draft.selectedBank == null) AppColors.TextSecondary else Color.Black,
                                fontSize = 14.sp
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = AppColors.TextSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                item {
                    DepositFormRow(
                        icon = Icons.Outlined.CreditCard,
                        title = "Тип продукта"
                    ) {
                        ProductTypeSwitch(
                            selected = draft.productType,
                            onSelected = onProductTypeSelected
                        )
                    }
                }

                item {
                    DepositFormRow(
                        icon = Icons.Outlined.Wallet,
                        title = "Сумма",
                        errorText = if (
                            uiState.showValidationErrors &&
                            draft.amount.isBlank()
                        ) {
                            "Введите сумму"
                        } else null
                    ) {
                        InlineNumberField(
                            value = draft.amount,
                            placeholder = "0",
                            keyboardType = KeyboardType.Number,
                            onValueChange = { onAmountChange(formatAmountInput(it)) },
                            modifier = Modifier.width(140.dp)
                        )
                    }
                }

                item {
                    DepositFormRow(
                        icon = Icons.Outlined.Percent,
                        title = "Ставка",
                        errorText = if (
                            uiState.showValidationErrors &&
                            draft.rate.isBlank()
                        ) {
                            "Введите ставку"
                        } else null
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            InlineNumberField(
                                value = draft.rate,
                                placeholder = "0",
                                keyboardType = KeyboardType.Decimal,
                                onValueChange = { onRateChange(sanitizeRateInput(it)) },
                                modifier = Modifier.width(76.dp)
                            )

                            Text(
                                text = "%",
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                item {
                    DepositFormRow(
                        icon = Icons.Outlined.CalendarMonth,
                        title = "Срок",
                        errorText = if (
                            uiState.showValidationErrors &&
                            draft.termMonths.isBlank()
                        ) {
                            "Выберите срок"
                        } else null,
                        onClick = { showTermDialog = true }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (draft.termMonths.isBlank()) {
                                    "Выбрать"
                                } else {
                                    "${draft.termMonths} мес"
                                },
                                color = if (draft.termMonths.isBlank()) AppColors.TextSecondary else Color.Black,
                                fontSize = 14.sp
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = AppColors.TextSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                item {
                    DepositFormRow(
                        icon = Icons.Outlined.CalendarMonth,
                        title = "Дата открытия",
                        errorText = null,
                        onClick = { showOpenDatePicker = true }
                    ) {
                        Text(
                            text = draft.openDate.ifBlank { "Выбрать" },
                            color = if (draft.openDate.isBlank()) AppColors.TextSecondary else Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }

                item {
                    DepositFormRow(
                        icon = Icons.Outlined.Payments,
                        title = "Выплата процентов",
                        errorText = if (
                            uiState.showValidationErrors &&
                            draft.interestPayoutType == null
                        ) {
                            "Выберите выплату процентов"
                        } else null,
                        onClick = { showPayoutDialog = true }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = draft.interestPayoutType?.title ?: "Выбрать",
                                color = if (draft.interestPayoutType == null) AppColors.TextSecondary else Color.Black,
                                fontSize = 14.sp
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = AppColors.TextSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                item {
                    DepositFormRow(
                        icon = Icons.Outlined.AddCircleOutline,
                        title = "Пополнение"
                    ) {
                        Switch(
                            checked = draft.allowTopUp,
                            onCheckedChange = onTopUpChange,
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = AppColors.PrimaryBlue,
                                checkedThumbColor = Color.White
                            )
                        )
                    }
                }

                item {
                    DepositFormRow(
                        icon = Icons.Outlined.RemoveCircleOutline,
                        title = "Частичное снятие"
                    ) {
                        Switch(
                            checked = draft.allowPartialWithdrawal,
                            onCheckedChange = onPartialWithdrawalChange,
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = AppColors.PrimaryBlue,
                                checkedThumbColor = Color.White
                            )
                        )
                    }
                }

                item {
                    DepositFormRow(
                        icon = Icons.AutoMirrored.Outlined.Comment,
                        title = "Комментарий",
                        onClick = { showCommentDialog = true }
                    ) {
                        Text(
                            text = draft.comment.ifBlank {
                                "Например: открыт в приложении банка"
                            },
                            color = if (draft.comment.isBlank()) AppColors.TextSecondary else Color.Black,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 170.dp)
                        )
                    }
                }
            } else {
                item {
                    RecommendedModeBlock()
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Divider(color = AppColors.Divider)

        AddDepositBottomActions(
            onSaveClick = onSaveClick,
            onCancelClick = onDismiss
        )
    }
}

@Composable
private fun ModeSwitcher(
    selectedMode: AddDepositMode,
    onModeSelected: (AddDepositMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0xFFF3F7FB), RoundedCornerShape(18.dp))
            .padding(4.dp)
    ) {
        ModeSwitcherItem(
            title = "Вручную",
            selected = selectedMode == AddDepositMode.MANUAL,
            onClick = { onModeSelected(AddDepositMode.MANUAL) },
            modifier = Modifier.weight(1f)
        )

        ModeSwitcherItem(
            title = "По рекомендации",
            selected = selectedMode == AddDepositMode.RECOMMENDED,
            onClick = { onModeSelected(AddDepositMode.RECOMMENDED) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ModeSwitcherItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(15.dp))
            .background(if (selected) AppColors.PrimaryBlue else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (selected) Color.White else Color.Black,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun DepositFormRow(
    icon: ImageVector,
    title: String,
    enabled: Boolean = true,
    errorText: String? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable RowScope.() -> Unit
) {
    val rowModifier = if (enabled && onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 52.dp)
                .then(rowModifier)
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconCircle(
                icon = icon,
                enabled = enabled
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                color = if (enabled) Color.Black else AppColors.TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            trailing()
        }

        if (errorText != null) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 48.dp, bottom = 6.dp)
            )
        }

        Divider(
            color = AppColors.Divider,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 48.dp)
        )
    }
}

@Composable
private fun IconCircle(
    icon: ImageVector,
    enabled: Boolean
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .background(
                color = if (enabled) AppColors.PrimaryBlueLight else Color(0xFFF1F2F4),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled) AppColors.PrimaryBlue else AppColors.TextSecondary,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun ProductTypeSwitch(
    selected: ProductType,
    onSelected: (ProductType) -> Unit
) {
    Row(
        modifier = Modifier
            .width(128.dp)
            .height(34.dp)
            .background(Color(0xFFF2F5FA), RoundedCornerShape(16.dp))
            .padding(3.dp)
    ) {
        ProductTypeItem(
            title = "Вклад",
            selected = selected == ProductType.DEPOSIT,
            onClick = { onSelected(ProductType.DEPOSIT) },
            modifier = Modifier.weight(1f)
        )

        ProductTypeItem(
            title = "Счёт",
            selected = selected == ProductType.SAVINGS_ACCOUNT,
            onClick = { onSelected(ProductType.SAVINGS_ACCOUNT) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ProductTypeItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) AppColors.PrimaryBlue else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (selected) Color.White else Color.Black,
            fontSize = 13.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun InlineNumberField(
    value: String,
    placeholder: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        cursorBrush = SolidColor(AppColors.PrimaryBlue),
        textStyle = LocalTextStyle.current.copy(
            color = if (enabled) Color.Black else AppColors.TextSecondary,
            fontSize = 14.sp,
            textAlign = TextAlign.End
        ),
        modifier = modifier,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (value.isBlank()) {
                    Text(
                        text = placeholder,
                        color = AppColors.TextSecondary,
                        fontSize = 14.sp,
                        textAlign = TextAlign.End
                    )
                }

                innerTextField()
            }
        }
    )
}

@Composable
private fun AddDepositBottomActions(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Button(
            onClick = onSaveClick,
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
                text = "Сохранить вклад",
                fontWeight = FontWeight.SemiBold
            )
        }

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

@Composable
private fun BankPickerDialog(
    banks: List<BankOption>,
    selectedBank: BankOption?,
    onDismiss: () -> Unit,
    onSelected: (BankOption) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val normalizedQuery = normalizeSearchText(query)

    val filteredBanks = remember(query, banks) {
        if (normalizedQuery.isBlank()) {
            banks
        } else {
            banks.filter { bank ->
                val normalizedTitle = normalizeSearchText(bank.title)
                val normalizedId = normalizeSearchText(bank.id)

                normalizedTitle.contains(normalizedQuery) ||
                        normalizedId.contains(normalizedQuery)
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Выберите банк",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Поиск") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.heightIn(max = 320.dp)
                ) {
                    items(
                        items = filteredBanks,
                        key = { it.id }
                    ) { bank ->
                        DialogChoiceRow(
                            title = bank.title,
                            selected = selectedBank?.id == bank.id,
                            onClick = { onSelected(bank) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена", color = AppColors.PrimaryBlue)
            }
        }
    )
}

@Composable
private fun MonthPickerDialog(
    selectedMonth: String,
    onDismiss: () -> Unit,
    onSelected: (Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Выберите срок",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.heightIn(max = 360.dp)
            ) {
                items((1..24).toList()) { month ->
                    DialogChoiceRow(
                        title = monthTitle(month),
                        selected = selectedMonth == month.toString(),
                        onClick = { onSelected(month) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена", color = AppColors.PrimaryBlue)
            }
        }
    )
}

@Composable
private fun InterestPayoutPickerDialog(
    selected: InterestPayoutType?,
    onDismiss: () -> Unit,
    onSelected: (InterestPayoutType) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Выплата процентов",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                InterestPayoutType.values().forEach { item ->
                    DialogChoiceRow(
                        title = item.title,
                        selected = selected == item,
                        onClick = { onSelected(item) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена", color = AppColors.PrimaryBlue)
            }
        }
    )
}

@Composable
private fun DialogChoiceRow(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) AppColors.PrimaryBlueLight else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )

        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = AppColors.PrimaryBlue
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepositDatePickerDialog(
    value: String,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val initialDateMillis = remember(value) {
        parseDateMillis(value) ?: System.currentTimeMillis()
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onDateSelected(formatDateMillis(millis))
                    }
                }
            ) {
                Text("Готово", color = AppColors.PrimaryBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена", color = AppColors.PrimaryBlue)
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
    }
}

@Composable
private fun CommentEditDialog(
    value: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember(value) { mutableStateOf(value) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Комментарий",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                placeholder = {
                    Text("Введите текст")
                },
                shape = RoundedCornerShape(16.dp)
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(text.trim()) }
            ) {
                Text("Готово", color = AppColors.PrimaryBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена", color = AppColors.PrimaryBlue)
            }
        }
    )
}

@Composable
private fun RecommendedModeBlock() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Рекомендации появятся позже",
            color = AppColors.TextSecondary,
            fontSize = 15.sp
        )
    }
}

private fun formatAmountInput(raw: String): String {
    val digits = raw.filter { it.isDigit() }

    if (digits.isBlank()) return ""

    return digits
        .reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()
}

private fun sanitizeRateInput(raw: String): String {
    val normalized = raw
        .replace('.', ',')
        .filter { it.isDigit() || it == ',' }

    val commaIndex = normalized.indexOf(',')

    if (commaIndex == -1) {
        return normalized
    }

    val beforeComma = normalized
        .take(commaIndex)
        .filter { it.isDigit() }

    val afterComma = normalized
        .drop(commaIndex + 1)
        .filter { it.isDigit() }
        .take(2)

    return if (beforeComma.isBlank()) {
        "0,$afterComma"
    } else {
        "$beforeComma,$afterComma"
    }
}

private fun monthTitle(month: Int): String {
    val word = when {
        month % 10 == 1 && month % 100 != 11 -> "месяц"
        month % 10 in 2..4 && month % 100 !in 12..14 -> "месяца"
        else -> "месяцев"
    }

    return "$month $word"
}

private val previewBanks = listOf(
    BankOption("tbank", "Т-Банк"),
    BankOption("sber", "СберБанк"),
    BankOption("vtb", "ВТБ"),
    BankOption("alfa", "Альфа-Банк"),
    BankOption("ozon", "Ozon Банк")
)

private fun previewManualDraft(
    productType: ProductType = ProductType.DEPOSIT
): ManualDepositDraft {
    return ManualDepositDraft(
        selectedBank = BankOption("tbank", "Т-Банк"),
        productType = productType,
        amount = "200 000",
        rate = "18,1",
        termMonths = "9",
        openDate = "12.03.2025",
        interestPayoutType = InterestPayoutType.MONTHLY,
        allowTopUp = true,
        allowPartialWithdrawal = false,
        comment = ""
    )
}

@Preview(
    name = "Добавление вклада — вручную",
    showBackground = true,
    showSystemUi = true,
    locale = "ru"
)
@Composable
private fun AddDepositBottomSheetContentManualPreview() {
    MaterialTheme {
        AddDepositBottomSheetContent(
            uiState = AddDepositBottomSheetUiState(
                isVisible = true,
                selectedMode = AddDepositMode.MANUAL,
                draft = previewManualDraft()
            ),
            onDismiss = {},
            onModeSelected = {},
            onBankSelected = {},
            onProductTypeSelected = {},
            onAmountChange = {},
            onRateChange = {},
            onTermMonthsChange = {},
            onOpenDateChange = {},
            onInterestPayoutTypeSelected = {},
            onTopUpChange = {},
            onPartialWithdrawalChange = {},
            onCommentChange = {},
            onSaveClick = {}
        )
    }
}

@Preview(
    name = "Добавление вклада — пустая форма",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AddDepositBottomSheetContentEmptyPreview() {
    MaterialTheme {
        AddDepositBottomSheetContent(
            uiState = AddDepositBottomSheetUiState(
                isVisible = true,
                selectedMode = AddDepositMode.MANUAL,
                draft = ManualDepositDraft()
            ),
            onDismiss = {},
            onModeSelected = {},
            onBankSelected = {},
            onProductTypeSelected = {},
            onAmountChange = {},
            onRateChange = {},
            onTermMonthsChange = {},
            onOpenDateChange = {},
            onInterestPayoutTypeSelected = {},
            onTopUpChange = {},
            onPartialWithdrawalChange = {},
            onCommentChange = {},
            onSaveClick = {}
        )
    }
}

@Preview(
    name = "Добавление вклада — ошибки",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AddDepositBottomSheetContentErrorsPreview() {
    MaterialTheme {
        AddDepositBottomSheetContent(
            uiState = AddDepositBottomSheetUiState(
                isVisible = true,
                selectedMode = AddDepositMode.MANUAL,
                showValidationErrors = true,
                draft = ManualDepositDraft()
            ),
            onDismiss = {},
            onModeSelected = {},
            onBankSelected = {},
            onProductTypeSelected = {},
            onAmountChange = {},
            onRateChange = {},
            onTermMonthsChange = {},
            onOpenDateChange = {},
            onInterestPayoutTypeSelected = {},
            onTopUpChange = {},
            onPartialWithdrawalChange = {},
            onCommentChange = {},
            onSaveClick = {}
        )
    }
}

@Preview(
    name = "Добавление вклада — рекомендации",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AddDepositBottomSheetContentRecommendedPreview() {
    MaterialTheme {
        AddDepositBottomSheetContent(
            uiState = AddDepositBottomSheetUiState(
                isVisible = true,
                selectedMode = AddDepositMode.RECOMMENDED,
                draft = previewManualDraft()
            ),
            onDismiss = {},
            onModeSelected = {},
            onBankSelected = {},
            onProductTypeSelected = {},
            onAmountChange = {},
            onRateChange = {},
            onTermMonthsChange = {},
            onOpenDateChange = {},
            onInterestPayoutTypeSelected = {},
            onTopUpChange = {},
            onPartialWithdrawalChange = {},
            onCommentChange = {},
            onSaveClick = {}
        )
    }
}

@Preview(
    name = "Кнопки снизу",
    showBackground = true
)
@Composable
private fun AddDepositBottomActionsPreview() {
    MaterialTheme {
        AddDepositBottomActions(
            onSaveClick = {},
            onCancelClick = {}
        )
    }
}

@Preview(
    name = "Диалог выбора банка",
    showBackground = true
)
@Composable
private fun BankPickerDialogPreview() {
    MaterialTheme {
        BankPickerDialog(
            banks = previewBanks,
            selectedBank = previewBanks.first(),
            onDismiss = {},
            onSelected = {}
        )
    }
}

@Preview(
    name = "Диалог выбора срока",
    showBackground = true
)
@Composable
private fun MonthPickerDialogPreview() {
    MaterialTheme {
        MonthPickerDialog(
            selectedMonth = "9",
            onDismiss = {},
            onSelected = {}
        )
    }
}

@Preview(
    name = "Диалог выплаты процентов",
    showBackground = true
)
@Composable
private fun InterestPayoutPickerDialogPreview() {
    MaterialTheme {
        InterestPayoutPickerDialog(
            selected = InterestPayoutType.MONTHLY,
            onDismiss = {},
            onSelected = {}
        )
    }
}

@Preview(
    name = "Диалог комментария",
    showBackground = true
)
@Composable
private fun CommentEditDialogPreview() {
    MaterialTheme {
        CommentEditDialog(
            value = "Текст",
            onDismiss = {},
            onSave = {}
        )
    }
}

@Preview(
    name = "Диалог выбора даты",
    showBackground = true,
    locale = "ru"
)
@Composable
private fun DepositDatePickerDialogPreview() {
    MaterialTheme {
        DepositDatePickerDialog(
            value = "12.03.2025",
            onDismiss = {},
            onDateSelected = {}
        )
    }
}

fun parseDateMillis(value: String): Long? {
    if (value.isBlank()) return null

    return runCatching {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
            isLenient = false
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(value)?.time
    }.getOrNull()
}

private fun formatDateMillis(millis: Long): String {
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.format(Date(millis))
}

private val RussianSearchLocale: Locale = Locale.forLanguageTag("ru-RU")
private fun normalizeSearchText(value: String): String {
    return value
        .lowercase(RussianSearchLocale)
        .replace("ё", "е")
        .replace(" ", "")
        .replace("-", "")
        .trim()
}