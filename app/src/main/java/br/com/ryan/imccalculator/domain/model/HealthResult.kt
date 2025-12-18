package br.com.ryan.imccalculator.domain.model

data class HealthResult(
    val bmi: BmiResult,
    val bmr: Double,               // TMB
    val dailyCalories: Double,     // TMB * fator atividade
    val idealWeightKg: Double?,    // pode ser null (se faltar dado)
    val bodyFatPercent: Double?    // pode ser null (se faltar dado)
)
