package br.com.ryan.imccalculator.domain.model

enum class ActivityLevel(val label: String, val multiplier: Double) {
    SEDENTARY("Sedentário", 1.2),
    LIGHT("Leve", 1.375),
    MODERATE("Moderado", 1.55),
    INTENSE("Intenso", 1.725)
}
