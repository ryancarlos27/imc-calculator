package br.com.ryan.imccalculator.ui.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import br.com.ryan.imccalculator.data.local.MeasurementEntity
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun exportHistoryCsv(context: Context, list: List<MeasurementEntity>) {
    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    val header = listOf(
        "id","timestamp","data_hora",
        "peso_kg","altura_cm","idade","sexo","atividade",
        "cintura_cm","pescoco_cm","quadril_cm",
        "imc","imc_classificacao","tmb","kcal_dia","peso_ideal","gordura_pct"
    ).joinToString(",")

    val lines = list.map { m ->
        listOf(
            m.id.toString(),
            m.timestamp.toString(),
            df.format(Date(m.timestamp)),
            m.weightKg.toString(),
            m.heightCm.toString(),
            m.ageYears.toString(),
            m.sex,
            m.activity,
            m.waistCm?.toString() ?: "",
            m.neckCm?.toString() ?: "",
            m.hipCm?.toString() ?: "",
            m.bmi.toString(),
            m.bmiCategoryLabel,
            m.bmr.toString(),
            m.dailyCalories.toString(),
            m.idealWeightKg?.toString() ?: "",
            m.bodyFatPercent?.toString() ?: ""
        ).joinToString(",")
    }

    val csv = buildString {
        appendLine(header)
        lines.forEach { appendLine(it) }
    }

    val file = File(context.cacheDir, "historico_imc.csv")
    file.writeText(csv, Charsets.UTF_8)

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/csv"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Exportar histórico"))
}
