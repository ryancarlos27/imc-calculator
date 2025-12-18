package br.com.ryan.imccalculator.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.ryan.imccalculator.reminder.ReminderScheduler

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    var enabled by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            enabled = true
            ReminderScheduler.scheduleDaily(context, hour = 9, minute = 0)
        } else {
            enabled = false
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Lembretes", style = MaterialTheme.typography.headlineSmall)
            OutlinedButton(onClick = onBack) { Text("Voltar") }
        }

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Lembrete diário (09:00)")
                    Switch(
                        checked = enabled,
                        onCheckedChange = { checked ->
                            if (checked) {
                                if (Build.VERSION.SDK_INT >= 33) {
                                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    enabled = true
                                    ReminderScheduler.scheduleDaily(context, 9, 0)
                                }
                            } else {
                                enabled = false
                                ReminderScheduler.cancel(context)
                            }
                        }
                    )
                }

                Button(
                    onClick = { ReminderScheduler.testNow(context) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Testar notificação agora")
                }
            }
        }
    }
}
