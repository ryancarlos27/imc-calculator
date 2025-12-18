package br.com.ryan.imccalculator.ui.screens

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.ryan.imccalculator.domain.model.ActivityLevel
import br.com.ryan.imccalculator.domain.model.Sex
import br.com.ryan.imccalculator.ui.viewmodel.HomeViewModel
import br.com.ryan.imccalculator.ui.components.BmiBadge



@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(
    onOpenHistory: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenHelp: () -> Unit,
    vm: HomeViewModel = viewModel()
)
 {
    val state by vm.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("IMC Calculator", style = MaterialTheme.typography.headlineSmall)

        // Peso / Altura / Idade
        OutlinedTextField(
            value = state.weightKg,
            onValueChange = vm::onWeightChange,
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.heightCm,
            onValueChange = vm::onHeightChange,
            label = { Text("Altura (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.ageYears,
            onValueChange = vm::onAgeChange,
            label = { Text("Idade (anos)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Sexo (dropdown simples)
        var sexExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = sexExpanded, onExpandedChange = { sexExpanded = !sexExpanded }) {
            OutlinedTextField(
                value = state.sex.label,
                onValueChange = {},
                readOnly = true,
                label = { Text("Sexo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = sexExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = sexExpanded, onDismissRequest = { sexExpanded = false }) {
                Sex.entries.forEach { sex ->
                    DropdownMenuItem(
                        text = { Text(sex.label) },
                        onClick = {
                            vm.onSexChange(sex)
                            sexExpanded = false
                        }
                    )
                }
            }
        }

        // Atividade
        var actExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = actExpanded, onExpandedChange = { actExpanded = !actExpanded }) {
            OutlinedTextField(
                value = state.activity.label,
                onValueChange = {},
                readOnly = true,
                label = { Text("Atividade física") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = actExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = actExpanded, onDismissRequest = { actExpanded = false }) {
                ActivityLevel.entries.forEach { a ->
                    DropdownMenuItem(
                        text = { Text(a.label) },
                        onClick = {
                            vm.onActivityChange(a)
                            actExpanded = false
                        }
                    )
                }
            }
        }

        // Medidas opcionais (para % gordura)
        OutlinedTextField(
            value = state.waistCm,
            onValueChange = vm::onWaistChange,
            label = { Text("Cintura (cm) - opcional") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.neckCm,
            onValueChange = vm::onNeckChange,
            label = { Text("Pescoço (cm) - opcional") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.hipCm,
            onValueChange = vm::onHipChange,
            label = { Text("Quadril (cm) - opcional (mulher)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Erro (se houver)
        state.errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
        state.savedMessage?.let { Text(it) }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.calculate() }) { Text("Calcular") }
            OutlinedButton(onClick = onOpenHistory) { Text("Histórico") }
            OutlinedButton(onClick = onOpenSettings) { Text("Lembretes") }
            OutlinedButton(onClick = onOpenHelp) { Text("Ajuda") }
        }


        // Resultado
        state.result?.let { r ->
            Card(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Resultado", style = MaterialTheme.typography.titleMedium)

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("IMC: ${"%.1f".format(r.bmi.bmi)}", style = MaterialTheme.typography.titleLarge)
                        BmiBadge(bmi = r.bmi.bmi)
                    }

                    Text("Classificação: ${r.bmi.category.label}")
                    Text(r.bmi.category.interpretation)

                    Divider()

                    Text("TMB (Mifflin-St Jeor): ${"%.0f".format(r.bmr)} kcal/dia")
                    Text("Necessidade diária (TMB x atividade): ${"%.0f".format(r.dailyCalories)} kcal/dia")

                    r.idealWeightKg?.let {
                        Text("Peso ideal (Devine): ${"%.1f".format(it)} kg")
                    } ?: Text("Peso ideal: informe uma altura válida.")

                    if (r.bodyFatPercent != null) {
                        Text("Gordura corporal (US Navy): ${"%.1f".format(r.bodyFatPercent)}%")
                    } else {
                        Text("Gordura corporal: informe cintura e pescoço (e quadril para mulher).")
                    }
                }
            }
        }
    }
}
