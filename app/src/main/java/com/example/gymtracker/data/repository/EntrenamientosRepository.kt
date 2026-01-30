package com.example.gymtracker.data.repository

import com.example.gymtracker.data.local.json.EntrenamientosRealizadosJsonDataSource
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.model.PlanSemanal

class EntrenamientosRepository(
    private val dataSource: EntrenamientosRealizadosJsonDataSource
) {

    fun obtenerEntrenamientos(usuarioId: Int): List<Entreno> {
        return dataSource.obtenerEntrenamientos(usuarioId)
    }

    fun guardarEntrenamiento(usuarioId: Int, entreno: Entreno) {
        dataSource.guardarEntrenamiento(usuarioId, entreno)
    }
}