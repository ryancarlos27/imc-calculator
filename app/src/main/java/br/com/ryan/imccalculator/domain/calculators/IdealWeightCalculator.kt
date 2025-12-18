package br.com.ryan.imccalculator.domain.calculators

import br.com.ryan.imccalculator.domain.model.Sex

object IdealWeightCalculator {

    /**
     * Devine:
     * Homem: 50 + 2.3*(alturaEmPol - 60)
     * Mulher: 45.5 + 2.3*(alturaEmPol - 60)
     * (altura em polegadas, 60 pol = 152.4 cm)
     */
    fun devine(sex: Sex, heightCm: Double): Double {
        val heightIn = heightCm / 2.54
        val diff = (heightIn - 60.0)
        val base = if (sex == Sex.MALE) 50.0 else 45.5
        return base + 2.3 * diff
    }
}
