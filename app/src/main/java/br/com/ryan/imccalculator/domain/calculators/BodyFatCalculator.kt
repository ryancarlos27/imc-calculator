package br.com.ryan.imccalculator.domain.calculators

import br.com.ryan.imccalculator.domain.model.Sex
import kotlin.math.log10

object BodyFatCalculator {

    /**
     * US Navy (medidas em cm):
     * Homem:
     * %G = 495 / (1.0324 - 0.19077*log10(cintura - pescoco) + 0.15456*log10(altura)) - 450
     *
     * Mulher:
     * %G = 495 / (1.29579 - 0.35004*log10(cintura + quadril - pescoco) + 0.22100*log10(altura)) - 450
     */
    fun usNavy(
        sex: Sex,
        heightCm: Double,
        neckCm: Double,
        waistCm: Double,
        hipCm: Double? // obrigatório só para mulher
    ): Double? {
        if (heightCm <= 0 || neckCm <= 0 || waistCm <= 0) return null

        return if (sex == Sex.MALE) {
            val x = waistCm - neckCm
            if (x <= 0) return null
            495.0 / (1.0324 - 0.19077 * log10(x) + 0.15456 * log10(heightCm)) - 450.0
        } else {
            val h = hipCm ?: return null
            val x = waistCm + h - neckCm
            if (x <= 0) return null
            495.0 / (1.29579 - 0.35004 * log10(x) + 0.22100 * log10(heightCm)) - 450.0
        }
    }
}
