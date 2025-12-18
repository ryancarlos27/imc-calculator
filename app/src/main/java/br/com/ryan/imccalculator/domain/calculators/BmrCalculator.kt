package br.com.ryan.imccalculator.domain.calculators

import br.com.ryan.imccalculator.domain.model.Sex

object BmrCalculator {

    /**
     * Mifflin-St Jeor:
     * Homem: 10*w + 6.25*h - 5*a + 5
     * Mulher: 10*w + 6.25*h - 5*a - 161
     * onde w=kg, h=cm, a=anos
     */
    fun mifflinStJeor(sex: Sex, weightKg: Double, heightCm: Double, ageYears: Int): Double {
        val base = 10.0 * weightKg + 6.25 * heightCm - 5.0 * ageYears
        return if (sex == Sex.MALE) base + 5.0 else base - 161.0
    }
}
