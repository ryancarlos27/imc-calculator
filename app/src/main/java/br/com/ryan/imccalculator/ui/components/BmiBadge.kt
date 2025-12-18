package br.com.ryan.imccalculator.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

private data class BmiVisual(
    val label: String,
    val color: Color,
    val icon: @Composable () -> Unit
)

@Composable
fun BmiBadge(
    bmi: Double,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme

    val visual = when {
        bmi < 18.5 -> BmiVisual(
            label = "Abaixo do peso",
            color = scheme.secondary,
            icon = { Icon(Icons.Outlined.Info, contentDescription = null) }
        )
        bmi < 25.0 -> BmiVisual(
            label = "Normal",
            color = scheme.primary,
            icon = { Icon(Icons.Outlined.CheckCircle, contentDescription = null) }
        )
        bmi < 30.0 -> BmiVisual(
            label = "Sobrepeso",
            color = scheme.tertiary,
            icon = { Icon(Icons.Outlined.WarningAmber, contentDescription = null) }
        )
        bmi < 35.0 -> BmiVisual(
            label = "Obesidade I",
            color = scheme.error,
            icon = { Icon(Icons.Outlined.ErrorOutline, contentDescription = null) }
        )
        bmi < 40.0 -> BmiVisual(
            label = "Obesidade II",
            color = scheme.error,
            icon = { Icon(Icons.Outlined.ErrorOutline, contentDescription = null) }
        )
        else -> BmiVisual(
            label = "Obesidade III",
            color = scheme.error,
            icon = { Icon(Icons.Outlined.ErrorOutline, contentDescription = null) }
        )
    }

    AssistChip(
        onClick = { /* sem ação */ },
        enabled = false,
        label = { Text(visual.label) },
        leadingIcon = visual.icon,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = visual.color.copy(alpha = 0.12f),
            labelColor = visual.color,
            leadingIconContentColor = visual.color,
            disabledContainerColor = visual.color.copy(alpha = 0.12f),
            disabledLabelColor = visual.color,
            disabledLeadingIconContentColor = visual.color
        ),
        modifier = modifier
    )
}
