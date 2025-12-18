package br.com.ryan.imccalculator.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurements")
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,

    val weightKg: Double,
    val heightCm: Double,
    val ageYears: Int,
    val sex: String,
    val activity: String,

    val waistCm: Double?,
    val neckCm: Double?,
    val hipCm: Double?,

    val bmi: Double,
    val bmiCategory: String,
    val bmiCategoryLabel: String,
    val bmiInterpretation: String,

    val bmr: Double,
    val dailyCalories: Double,
    val idealWeightKg: Double?,
    val bodyFatPercent: Double?
)
