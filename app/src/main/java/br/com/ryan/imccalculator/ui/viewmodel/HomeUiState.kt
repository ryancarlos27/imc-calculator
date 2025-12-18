package br.com.ryan.imccalculator.ui.viewmodel

import br.com.ryan.imccalculator.domain.model.ActivityLevel
import br.com.ryan.imccalculator.domain.model.HealthResult
import br.com.ryan.imccalculator.domain.model.Sex

data class HomeUiState(
    val weightKg: String = "",
    val heightCm: String = "",
    val ageYears: String = "",
    val sex: Sex = Sex.MALE,
    val activity: ActivityLevel = ActivityLevel.SEDENTARY,
    val waistCm: String = "",
    val neckCm: String = "",
    val hipCm: String = "",

    val errorMessage: String? = null,
    val result: HealthResult? = null,
    val savedMessage: String? = null

)
