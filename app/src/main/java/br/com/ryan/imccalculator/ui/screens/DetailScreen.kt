package br.com.ryan.imccalculator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.ryan.imccalculator.ui.viewmodel.DetailViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import br.com.ryan.imccalculator.ui.components.BmiBadge

@Composable
fun DetailScreen(
    id: Long,
    onBack: () -> Unit,
    vm: DetailViewModel = viewModel()
) {
    val flow = remember(id) { vm.observe(id) }
    val item by flow.collectAsState(initial = null)
    val df = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")) }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Detalhe", style = MaterialTheme.typography.headlineSmall)
            OutlinedButton(onClick = onBack) { Text("Voltar") }
        }

        if (item == null) {
            Text("Carregando...")
            return
        }

        val m = item!!

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Data: ${df.format(Date(m.timestamp))}")
                Text("Peso: ${m.weightKg} kg")
                Text("Altura: ${m.heightCm} cm")
                Text("Idade: ${m.ageYears}")
                Text("Sexo: ${m.sex}")
                Text("Atividade: ${m.activity}")

                Divider()

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("IMC: ${"%.1f".format(m.bmi)}")
                    BmiBadge(bmi = m.bmi)
                }
                Text(m.bmiInterpretation)


                Divider()

                Text("TMB: ${"%.0f".format(m.bmr)} kcal/dia")
                Text("Necessidade diária: ${"%.0f".format(m.dailyCalories)} kcal/dia")

                m.idealWeightKg?.let { Text("Peso ideal: ${"%.1f".format(it)} kg") }
                m.bodyFatPercent?.let { Text("Gordura corporal: ${"%.1f".format(it)}%") }
            }
        }
    }
}
