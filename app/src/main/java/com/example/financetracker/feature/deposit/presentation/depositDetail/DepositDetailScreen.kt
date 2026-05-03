package com.example.financetracker.feature.deposit.presentation.depositDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.feature.deposit.domain.model.ProductType
import com.example.financetracker.feature.deposit.presentation.adddeposit.AddDepositBottomSheet
import com.example.financetracker.feature.deposit.presentation.adddeposit.AddDepositBottomSheetViewModel
import com.example.financetracker.feature.deposit.presentation.main.DepositViewModel
import com.example.financetracker.ui.theme.AppColors.Divider
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.feature.deposit.domain.model.Deposit
import com.example.financetracker.feature.deposit.domain.model.InterestPayoutType
import com.example.financetracker.feature.deposit.domain.util.toMoney
import com.example.financetracker.feature.deposit.domain.util.toMonths
import com.example.financetracker.feature.deposit.domain.util.toRate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DepositDetailScreen(
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    viewModel: DepositDetailViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8FA))
    ) {
        DepositDetailContent(
            state = state,
            onBackClick = onBackClick,
            onDeleteClick = onDeleteClick,
            onEditClick = onEditClick,
            onIncomePeriodSelected = viewModel::onIncomePeriodSelected
        )
    }
}

@Composable
private fun DepositDetailContent(
    state: DepositDetailState,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onIncomePeriodSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val deposit = state.deposit

    if (deposit == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8FA)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8FA))
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                DepositDetailTopBar(
                    bankName = deposit.bankName,
                    productType = deposit.productType.label,
                    rate = deposit.rate,
                    onBackClick = onBackClick,
                    onDeleteClick = onDeleteClick
                )
            }

            item {
                DepositAmountHeroCard(
                    amount = deposit.amount.toMoney(),
                    productType = deposit.productType.label
                )
            }

            item {
                DepositMainInfoCard(
                    openedDate = deposit.openedAtMillis.toDateText(),
                    term = deposit.termMonths.toMonths(),
                    rate = deposit.rate.toRate(),
                    payoutType = deposit.payoutType.title,
                    currentEarnings = state.currentEarningsText
                )
            }

            item {
                ApproximateIncomeCard(
                    selectedPeriod = state.selectedIncomePeriodMonths,
                    periods = state.availableIncomePeriods,
                    income = state.approximateIncomeText,
                    onPeriodSelected = onIncomePeriodSelected
                )
            }

            item {
                ProductInfoCard(
                    allowTopUp = "Доступно", // взять у депозита
                    allowPartialWithdrawal = "Недоступно", // взять у депозита
                    comment = "Открыт в приложении банка" // взять у депозита
                )
            }

            item {
                BankInfoCard(
                    bankName = deposit.bankName,
                    description = "",//инфа о банке
                    supportInfo = ""
                )
            }
        }

        Button(
            onClick = onEditClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .padding(bottom = 14.dp)
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1478FF),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Редактировать",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DepositDetailTopBar(
    bankName: String,
    productType: String,
    rate: Double,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад",
                tint = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bankName,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "$productType • ${rate.toRate()}",
                fontSize = 18.sp,
                color = Color(0xFF6B7280),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(48.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Удалить вклад",
                tint = Color(0xFFFF2D2D)
            )
        }
    }
}

@Composable
private fun DepositAmountHeroCard(
    amount: String,
    productType: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8FF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 26.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .background(Color(0xFFF1EAF7), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountBalance, //иконка банка
                    contentDescription = null,
                    tint = Color(0xFF1296F3),
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.width(22.dp))

            Column {
                Text(
                    text = if (productType == "Вклад") {
                        "Сумма вклада"
                    } else {
                        "Сумма счёта"
                    },
                    fontSize = 18.sp,
                    color = Color(0xFF6B7280)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = amount,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun DepositMainInfoCard(
    openedDate: String,
    term: String,
    rate: String,
    payoutType: String,
    currentEarnings: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
        ) {
            DepositDetailInfoRow(
                icon = Icons.Outlined.CalendarMonth,
                iconTint = Color(0xFF7B61FF),
                iconBackground = Color(0xFFF0EAFF),
                title = "Дата открытия",
                value = openedDate
            )

            DepositCardDivider()

            DepositDetailInfoRow(
                icon = Icons.Outlined.Schedule,
                iconTint = Color(0xFF7B61FF),
                iconBackground = Color(0xFFF0EAFF),
                title = "Срок",
                value = term
            )

            DepositCardDivider()

            DepositDetailInfoRow(
                icon = Icons.Outlined.Percent,
                iconTint = Color(0xFF7B61FF),
                iconBackground = Color(0xFFF0EAFF),
                title = "Ставка",
                value = rate
            )

            DepositCardDivider()

            DepositDetailInfoRow(
                icon = Icons.Outlined.Payments,
                iconTint = Color(0xFF1478FF),
                iconBackground = Color(0xFFEAF4FF),
                title = "Выплата процентов",
                value = payoutType
            )

            DepositCardDivider()

            DepositDetailInfoRow(
                icon = Icons.AutoMirrored.Outlined.ShowChart,
                iconTint = Color(0xFF159947),
                iconBackground = Color(0xFFE8F7EF),
                title = "Текущий заработок",
                value = currentEarnings
            )
        }
    }
}

@Composable
private fun DepositDetailInfoRow(
    icon: ImageVector,
    iconTint: Color,
    iconBackground: Color,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(iconBackground, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFF687083),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun DepositCardDivider() {
    Divider(
        color = Color(0xFFE6E8EF),
        thickness = 1.dp
    )
}

@Composable
private fun ApproximateIncomeCard(
    selectedPeriod: Int,
    periods: List<Int>,
    income: String,
    onPeriodSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "Примерный доход",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD8DCE8),
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                periods.forEachIndexed { index, period ->
                    val selected = selectedPeriod == period

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(
                                if (selected) Color(0xFF1478FF) else Color.Transparent
                            )
                            .clickable { onPeriodSelected(period) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$period мес",
                            color = if (selected) Color.White else Color(0xFF1478FF),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (index != periods.lastIndex) {
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp),
                            color = Color(0xFFD8DCE8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = income,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "За выбранный период",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 15.sp,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ProductInfoCard(
    allowTopUp: String,
    allowPartialWithdrawal: String,
    comment: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "О продукте",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProductInfoRow(
                icon = Icons.Default.Add,
                iconTint = Color(0xFF159947),
                iconBackground = Color(0xFFE8F7EF),
                title = "Пополнение",
                value = allowTopUp,
                valueColor = Color(0xFF159947)
            )

            Spacer(modifier = Modifier.height(10.dp))

            ProductInfoRow(
                icon = Icons.Default.Remove,
                iconTint = Color(0xFFFF3B30),
                iconBackground = Color(0xFFFFEAEA),
                title = "Частичное снятие",
                value = allowPartialWithdrawal,
                valueColor = Color(0xFFFF3B30)
            )

            Spacer(modifier = Modifier.height(10.dp))

            ProductInfoRow(
                icon = Icons.Outlined.ChatBubble,
                iconTint = Color(0xFF1478FF),
                iconBackground = Color(0xFFEAF4FF),
                title = "Комментарий",
                value = comment,
                valueColor = Color(0xFF687083)
            )
        }
    }
}

@Composable
private fun ProductInfoRow(
    icon: ImageVector,
    iconTint: Color,
    iconBackground: Color,
    title: String,
    value: String,
    valueColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(iconBackground, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF687083),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            color = valueColor,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.widthIn(max = 180.dp)
        )
    }
}

@Composable
private fun BankInfoCard(
    bankName: String,
    description: String,
    supportInfo: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "Информация о банке",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFFEAF4FF), RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountBalance,
                        contentDescription = null,
                        tint = Color(0xFF1478FF),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = bankName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                fontSize = 15.sp,
                lineHeight = 21.sp,
                color = Color(0xFF687083)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3F7FF), RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Text(
                    text = supportInfo,
                    fontSize = 14.sp,
                    color = Color(0xFF1478FF)
                )
            }
        }
    }
}

private fun Long.toDateText(): String {
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(this))
}

@Preview(
    name = "Deposit detail content",
    showBackground = true,
    showSystemUi = true,
    locale = "ru"
)
@Composable
private fun DepositDetailContentPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF8F8FA)
        ) {
            DepositDetailContent(
                state = DepositDetailState(
                    deposit = Deposit(
                        id = 1,
                        bankName = "Сбербанк",
                        productType = ProductType.DEPOSIT,
                        rate = 15.0,
                        amount = 200_000,
                        payoutType = InterestPayoutType.MONTHLY,
                        termMonths = 9,
                        openedAtMillis = System.currentTimeMillis()
                    ),
                    availableIncomePeriods = listOf(1, 3, 6, 9),
                    selectedIncomePeriodMonths = 9,
                    approximateIncomeText = "27 150 ₽",
                    currentEarningsText = "22 500 ₽"
                ),
                onBackClick = {},
                onDeleteClick = {},
                onEditClick = {},
                onIncomePeriodSelected = {}
            )
        }
    }
}

@Preview(
    name = "Deposit detail top bar",
    showBackground = true
)
@Composable
private fun DepositDetailTopBarPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8FA))
                .padding(16.dp)
        ) {
            DepositDetailTopBar(
                bankName = "Сбербанк",
                productType = ProductType.DEPOSIT.label,
                rate = 17.0,
                onBackClick = {},
                onDeleteClick = {}
            )
        }
    }
}

@Preview(
    name = "Deposit amount hero card",
    showBackground = true
)
@Composable
private fun DepositAmountHeroCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8FA))
                .padding(16.dp)
        ) {
            DepositAmountHeroCard(
                amount = "200 000 ₽",
                productType = ProductType.DEPOSIT.label
            )
        }
    }
}

@Preview(
    name = "Deposit main info card",
    showBackground = true
)
@Composable
private fun DepositMainInfoCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8FA))
                .padding(16.dp)
        ) {
            DepositMainInfoCard(
                openedDate = "12.03.2025",
                term = "9 мес",
                rate = "15%",
                payoutType = "Ежемесячно",
                currentEarnings = "22 500 ₽"
            )
        }
    }
}

@Preview(
    name = "Approximate income card",
    showBackground = true
)
@Composable
private fun ApproximateIncomeCardPreview() {
    var selectedPeriod by remember { mutableIntStateOf(9) }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8FA))
                .padding(16.dp)
        ) {
            ApproximateIncomeCard(
                selectedPeriod = selectedPeriod,
                periods = listOf(1, 3, 6, 9),
                income = "27 150 ₽",
                onPeriodSelected = { selectedPeriod = it }
            )
        }
    }
}

@Preview(
    name = "Product info card",
    showBackground = true
)
@Composable
private fun ProductInfoCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8FA))
                .padding(16.dp)
        ) {
            ProductInfoCard(
                allowTopUp = "Доступно",
                allowPartialWithdrawal = "Недоступно",
                comment = "Открыт в приложении банка"
            )
        }
    }
}

@Preview(
    name = "Bank info card",
    showBackground = true
)
@Composable
private fun BankInfoCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8FA))
                .padding(16.dp)
        ) {
            BankInfoCard(
                bankName = "Сбербанк",
                description = "Крупнейший банк России.",
                supportInfo = ""
            )
        }
    }
}