package com.example.financetracker.feature.deposit.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financetracker.R
import com.example.financetracker.core.ui.AppDimens
import com.example.financetracker.core.ui.AppDimens.CardCornerRadius
import com.example.financetracker.core.ui.AppDimens.ScreenTopPadding
import com.example.financetracker.core.ui.AppDimens.SectionSpacingSmall
import com.example.financetracker.feature.deposit.domain.Deposit
import com.example.financetracker.feature.deposit.domain.toDays
import com.example.financetracker.feature.deposit.domain.toMoney
import com.example.financetracker.feature.deposit.domain.toMonths
import com.example.financetracker.feature.deposit.domain.toRate

@Composable
fun DepositScreen(
    onSetupRecommendationsClick: () -> Unit,
    viewModel: DepositViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppDimens.ScreenHorizontalPadding),
        contentPadding = PaddingValues(
            top = ScreenTopPadding,
            bottom = 24.dp
        )
    ) {
        item {
            DepositHeader(
                onSetupRecommendationsClick = onSetupRecommendationsClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(SectionSpacingSmall))
        }

        item {
            DepositSummaryCard(
                totalAmount = uiState.totalAmount,
                averageRate = uiState.averageRate,
                expectedIncome = uiState.expectedIncome
            )
        }

        item {
            Spacer(modifier = Modifier.height(SectionSpacingSmall))
        }

        item {
            DepositSectionHeader(
                onSortSelected = viewModel::onSortSelected
            )
        }

        item {
            Spacer(modifier = Modifier.height(SectionSpacingSmall))
        }

        item {
            AddDepositButton(
                onClick = viewModel::onAddDepositClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(SectionSpacingSmall))
        }

        itemsIndexed(
            items = uiState.deposits,
            key = { _, item -> item.id }
        ) { index, item ->
            DepositItem(
                item = item,
                onClick = { viewModel.onDepositClick(item) },
                bankLogo = painterResource(R.drawable.bank_icon)
            )

            if (index != uiState.deposits.lastIndex) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DepositScreenPreview() {
    DepositScreen(
        onSetupRecommendationsClick = {}
    )
}

@Composable
private fun DepositHeader(
    onSetupRecommendationsClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Вклады",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        RecommendationCtaButton(
            onClick = onSetupRecommendationsClick
        )
    }
}

@Composable
private fun PreviewContainer(
    content: @Composable () -> Unit
) {
    MaterialTheme {
        Surface(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DepositsHeaderPreview() {
    PreviewContainer {
        DepositHeader(
            onSetupRecommendationsClick = {}
        )
    }
}

@Composable
private fun RecommendationCtaButton(
    onClick: () -> Unit
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF16C8F6),
            Color(0xFF3E8BFF),
            Color(0xFF8A3FFC)
        )
    )

    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(999.dp),
        color = Color.Transparent,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .background(gradient, RoundedCornerShape(999.dp))
                .padding(horizontal = 18.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Настроить рекомендации",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun DepositSummaryCard(
    totalAmount: String,
    averageRate: String,
    expectedIncome: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CardCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            SummaryMetric(
                icon = Icons.Outlined.Wallet,
                iconContainerColor = Color(0xFFE8F7EF),
                iconTint = Color(0xFF1FA463),
                title = "Общая сумма",
                value = totalAmount,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            SummaryMetric(
                icon = Icons.Outlined.Percent,
                iconContainerColor = Color(0xFFF0EAFE),
                iconTint = Color(0xFF7A4DFF),
                title = "Средняя ставка",
                value = averageRate,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            SummaryMetric(
                icon = Icons.AutoMirrored.Outlined.ShowChart,
                iconContainerColor = Color(0xFFEAF4FF),
                iconTint = Color(0xFF2B7FFF),
                title = "Доход",
                value = expectedIncome,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DepositsSummaryCardPreview() {
    PreviewContainer {
        DepositSummaryCard(
            totalAmount = "540 000 ₽",
            averageRate = "16,8%",
            expectedIncome = "78 400 ₽"
        )
    }
}

@Composable
private fun SummaryMetric(
    icon: ImageVector,
    iconContainerColor: Color,
    iconTint: Color,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconContainerColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
private fun DepositSectionHeader(
    onSortSelected: (DepositSortType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Мои вклады",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        SortButton(
            onSortSelected = onSortSelected
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DepositsSectionHeaderPreview() {
    PreviewContainer {
        DepositSectionHeader(
            onSortSelected = {}
        )
    }
}

@Composable
private fun SortButton(
    onSortSelected: (DepositSortType) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box {
        Surface(
            modifier = Modifier.clickable { expanded = true },
            shape = RoundedCornerShape(18.dp),
            tonalElevation = 1.dp,
            shadowElevation = 1.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.FilterList,
                    contentDescription = "Сортировка"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DepositSortType.entries.forEach { sortType ->
                DropdownMenuItem(
                    text = { Text(sortType.title) },
                    onClick = {
                        expanded = false
                        onSortSelected(sortType)
                    }
                )
            }
        }
    }
}

@Composable
private fun AddDepositButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF2459FF)
        ),
        contentPadding = PaddingValues(
            horizontal = 14.dp,
            vertical = 8.dp
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Добавить вклад",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DepositItem(
    item: Deposit,
    onClick: () -> Unit,
    bankLogo: Painter
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = bankLogo,
                    contentDescription = item.bankName,
                    modifier = Modifier.size(28.dp),
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.bankName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = item.productName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${item.amount.toMoney()} • ${item.term.toMonths()} • ${item.daysLeft.toDays()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = item.rate.toRate(),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF139A4A),
                fontWeight = FontWeight.Bold
            )
        }
    }
}