package br.com.ryan.imccalculator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.ryan.imccalculator.data.local.AppDatabase
import br.com.ryan.imccalculator.data.repository.MeasurementRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = MeasurementRepository(AppDatabase.get(application).measurementDao())
    val history = repo.observeAll()
}
