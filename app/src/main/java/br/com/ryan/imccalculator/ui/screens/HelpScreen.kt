package br.com.ryan.imccalculator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HelpScreen(onBack: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Ajuda e fórmulas", style = MaterialTheme.typography.headlineSmall)
            OutlinedButton(onClick = onBack) { Text("Voltar") }
        }

        Spacer(Modifier.height(12.dp))

        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("IMC (Índice de Massa Corporal)", style = MaterialTheme.typography.titleMedium)
                    Text("Fórmula: IMC = peso(kg) / (altura(m)²)")
                    Text("Interpretação (OMS):")
                    Text("• < 18,5: Abaixo do peso")
                    Text("• 18,5 – 24,9: Normal")
                    Text("• 25 – 29,9: Sobrepeso")
                    Text("• 30 – 34,9: Obesidade I")
                    Text("• 35 – 39,9: Obesidade II")
                    Text("• ≥ 40: Obesidade III")
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("TMB (Taxa Metabólica Basal)", style = MaterialTheme.typography.titleMedium)
                    Text("Usamos Mifflin-St Jeor (kcal/dia):")
                    Text("Homem: 10*w + 6,25*h − 5*a + 5")
                    Text("Mulher: 10*w + 6,25*h − 5*a − 161")
                    Text("Onde: w=peso(kg), h=altura(cm), a=idade(anos)")
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Necessidade calórica diária", style = MaterialTheme.typography.titleMedium)
                    Text("Fórmula: kcal/dia = TMB × fator de atividade")
                    Text("Fatores usados:")
                    Text("• Sedentário: 1,2")
                    Text("• Leve: 1,375")
                    Text("• Moderado: 1,55")
                    Text("• Intenso: 1,725")
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Peso ideal (Devine)", style = MaterialTheme.typography.titleMedium)
                    Text("Altura em polegadas (pol): altura(cm)/2,54")
                    Text("Homem: 50 + 2,3 × (pol − 60)")
                    Text("Mulher: 45,5 + 2,3 × (pol − 60)")
                    Text("Obs.: 60 pol = 152,4 cm")
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Gordura corporal (US Navy)", style = MaterialTheme.typography.titleMedium)
                    Text("Medidas em cm. Usa log10().")
                    Text("Homem:")
                    Text("%G = 495 / (1,0324 − 0,19077*log10(cintura − pescoço) + 0,15456*log10(altura)) − 450")
                    Text("Mulher:")
                    Text("%G = 495 / (1,29579 − 0,35004*log10(cintura + quadril − pescoço) + 0,22100*log10(altura)) − 450")
                    Text("Obs.: para mulher o quadril é obrigatório.")
                }
            }
        }
    }
}
