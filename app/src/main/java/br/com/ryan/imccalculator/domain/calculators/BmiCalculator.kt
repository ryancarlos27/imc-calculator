package br.com.ryan.imccalculator.domain.calculators

import br.com.ryan.imccalculator.domain.model.BmiCategory
import br.com.ryan.imccalculator.domain.model.BmiResult
import kotlin.math.roundToInt

object BmiCalculator {

    /**
     * IMC = peso(kg) / (altura(m)^2)
     */
    fun calculate(weightKg: Double, heightCm: Double): BmiResult {
        val heightM = heightCm / 100.0
        val bmi = weightKg / (heightM * heightM)

        val category = when {
            bmi < 18.5 -> BmiCategory.UNDERWEIGHT
            bmi < 25.0 -> BmiCategory.NORMAL
            bmi < 30.0 -> BmiCategory.OVERWEIGHT
            bmi < 35.0 -> BmiCategory.OBESITY_I
            bmi < 40.0 -> BmiCategory.OBESITY_II
            else -> BmiCategory.OBESITY_III
        }

        return BmiResult(bmi = bmi, category = category)
    }

    fun format(bmi: Double): String = ((bmi * 10).roundToInt() / 10.0).toString()
}
