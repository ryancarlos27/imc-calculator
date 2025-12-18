package br.com.ryan.imccalculator.data.repository

import br.com.ryan.imccalculator.data.local.MeasurementDao
import br.com.ryan.imccalculator.data.local.MeasurementEntity
import kotlinx.coroutines.flow.Flow

class MeasurementRepository(private val dao: MeasurementDao) {
    fun observeAll(): Flow<List<MeasurementEntity>> = dao.observeAll()
    fun observeById(id: Long): Flow<MeasurementEntity?> = dao.observeById(id)
    suspend fun insert(entity: MeasurementEntity): Long = dao.insert(entity)
}
