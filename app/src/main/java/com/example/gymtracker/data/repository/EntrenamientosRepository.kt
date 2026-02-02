package com.example.gymtracker.data.repository

import com.example.gymtracker.data.local.json.EntrenamientosRealizadosJsonDataSource
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.model.PlanSemanal

/**
 * Repositorio para manejar los entrenamientos realizados de los usuarios.
 *
 * Esta clase actúa como capa intermedia entre la aplicación y el
 * [EntrenamientosRealizadosJsonDataSource], permitiendo obtener y guardar
 * entrenamientos de manera sencilla.
 *
 * Funciones principales:
 * - [obtenerEntrenamientos]: Obtiene la lista de entrenamientos de un usuario.
 * - [guardarEntrenamiento]: Guarda un entrenamiento para un usuario.
 *
 * @property dataSource Instancia de [EntrenamientosRealizadosJsonDataSource] utilizada para acceder a los datos persistidos en JSON.
 */
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