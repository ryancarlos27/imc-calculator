package br.com.ryan.imccalculator.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.ryan.imccalculator.data.local.MeasurementEntity
import br.com.ryan.imccalculator.ui.viewmodel.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.platform.LocalContext
import br.com.ryan.imccalculator.ui.util.exportHistoryCsv
import br.com.ryan.imccalculator.ui.components.BmiBadge



@Composable
fun HistoryScreen(
    onBack: () -> Unit,
    onOpenDetail: (Long) -> Unit,
    vm: HistoryViewModel = viewModel()
) {
    val list by vm.history.collectAsState(initial = emptyList())
    val df = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")) }
    val context = LocalContext.current

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Histórico", style = MaterialTheme.typography.headlineSmall)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { exportHistoryCsv(context, list) },
                    enabled = list.isNotEmpty()
                ) { Text("Exportar CSV") }

                OutlinedButton(onClick = onBack) { Text("Voltar") }
            }
        }

        Spacer(Modifier.height(12.dp))

        if (list.isEmpty()) {
            Text("Nenhuma medição salva ainda.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(list, key = { it.id }) { item ->
                    HistoryItem(item, df) { onOpenDetail(item.id) }
                }
            }
        }
    }
}

@Composable
private fun HistoryItem(
    item: MeasurementEntity,
    df: SimpleDateFormat,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(df.format(Date(item.timestamp)), style = MaterialTheme.typography.labelLarge)
                BmiBadge(bmi = item.bmi)
            }

            Text("IMC: ${"%.1f".format(item.bmi)} — ${item.bmiCategoryLabel}")
            Text("Peso: ${item.weightKg} kg | Altura: ${item.heightCm} cm")
        }
    }
}
