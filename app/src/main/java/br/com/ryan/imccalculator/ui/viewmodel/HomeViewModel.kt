package br.com.ryan.imccalculator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.ryan.imccalculator.data.local.AppDatabase
import br.com.ryan.imccalculator.data.local.MeasurementEntity
import br.com.ryan.imccalculator.data.repository.MeasurementRepository
import br.com.ryan.imccalculator.domain.calculators.*
import br.com.ryan.imccalculator.domain.model.HealthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = MeasurementRepository(
        AppDatabase.get(application).measurementDao()
    )

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun onWeightChange(v: String) = _uiState.update { it.copy(weightKg = v, errorMessage = null, savedMessage = null) }
    fun onHeightChange(v: String) = _uiState.update { it.copy(heightCm = v, errorMessage = null, savedMessage = null) }
    fun onAgeChange(v: String) = _uiState.update { it.copy(ageYears = v, errorMessage = null, savedMessage = null) }
    fun onWaistChange(v: String) = _uiState.update { it.copy(waistCm = v, errorMessage = null, savedMessage = null) }
    fun onNeckChange(v: String) = _uiState.update { it.copy(neckCm = v, errorMessage = null, savedMessage = null) }
    fun onHipChange(v: String) = _uiState.update { it.copy(hipCm = v, errorMessage = null, savedMessage = null) }

    fun onSexChange(sex: br.com.ryan.imccalculator.domain.model.Sex) =
        _uiState.update { it.copy(sex = sex, errorMessage = null, savedMessage = null) }

    fun onActivityChange(a: br.com.ryan.imccalculator.domain.model.ActivityLevel) =
        _uiState.update { it.copy(activity = a, errorMessage = null, savedMessage = null) }

    fun calculate() {
        val s = _uiState.value

        val weight = s.weightKg.toDoubleOrNull()
        val height = s.heightCm.toDoubleOrNull()
        val age = s.ageYears.toIntOrNull()

        if (weight == null || height == null || age == null) {
            _uiState.update { it.copy(errorMessage = "Preencha peso, altura e idade com valores válidos.", result = null) }
            return
        }

        if (weight <= 0 || weight > 400) {
            _uiState.update { it.copy(errorMessage = "Peso inválido. Use um valor entre 1 e 400 kg.", result = null) }
            return
        }
        if (height < 50 || height > 250) {
            _uiState.update { it.copy(errorMessage = "Altura inválida. Use um valor entre 50 e 250 cm.", result = null) }
            return
        }
        if (age < 5 || age > 120) {
            _uiState.update { it.copy(errorMessage = "Idade inválida. Use um valor entre 5 e 120 anos.", result = null) }
            return
        }

        val bmi = BmiCalculator.calculate(weightKg = weight, heightCm = height)
        val bmr = BmrCalculator.mifflinStJeor(sex = s.sex, weightKg = weight, heightCm = height, ageYears = age)
        val daily = bmr * s.activity.multiplier
        val ideal = IdealWeightCalculator.devine(sex = s.sex, heightCm = height)

        val waist = s.waistCm.toDoubleOrNull()
        val neck = s.neckCm.toDoubleOrNull()
        val hip = s.hipCm.toDoubleOrNull()

        val bodyFat = if (waist != null && neck != null) {
            BodyFatCalculator.usNavy(
                sex = s.sex,
                heightCm = height,
                neckCm = neck,
                waistCm = waist,
                hipCm = hip
            )
        } else null

        val result = HealthResult(
            bmi = bmi,
            bmr = bmr,
            dailyCalories = daily,
            idealWeightKg = ideal,
            bodyFatPercent = bodyFat
        )

        _uiState.update { it.copy(result = result, errorMessage = null) }

        // salva automaticamente no histórico
        val entity = MeasurementEntity(
            timestamp = System.currentTimeMillis(),
            weightKg = weight,
            heightCm = height,
            ageYears = age,
            sex = s.sex.name,
            activity = s.activity.name,
            waistCm = waist,
            neckCm = neck,
            hipCm = hip,
            bmi = bmi.bmi,
            bmiCategory = bmi.category.name,
            bmiCategoryLabel = bmi.category.label,
            bmiInterpretation = bmi.category.interpretation,
            bmr = bmr,
            dailyCalories = daily,
            idealWeightKg = ideal,
            bodyFatPercent = bodyFat
        )

        viewModelScope.launch {
            repo.insert(entity)
            _uiState.update { it.copy(savedMessage = "Medição salva no histórico.") }
        }
    }
}
