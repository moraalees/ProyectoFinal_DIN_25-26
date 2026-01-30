package com.example.gymtracker.ui.screens.routines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.EntrenoRepository
import com.example.gymtracker.data.repository.EntrenoRepository.generarPlanSemanalDesdePlantilla
import com.example.gymtracker.data.repository.UsuarioGimnasioRepository
import com.example.gymtracker.model.DiaDescanso
import com.example.gymtracker.model.PlanSemanal
import com.example.gymtracker.model.UsuarioGimnasio
import com.example.gymtracker.model.PlantillaDia
import com.example.gymtracker.model.PlantillaPlanSemanal
import com.example.gymtracker.model.enum_classes.TipoRutina

class RutinasViewModel(
    private val repositorio: UsuarioGimnasioRepository
) : ViewModel() {

    var perfilGimnasio by mutableStateOf<UsuarioGimnasio?>(null)
        private set

    fun cargarPerfil(usuarioId: Int) {
        perfilGimnasio = repositorio.obtenerPerfilPorUsuario(usuarioId)
    }

    fun obtenerPerfil(usuarioId: Int): UsuarioGimnasio? {
        return repositorio.obtenerPerfilPorUsuario(usuarioId)
    }

    fun crearRutinaPersonalizada(
        perfil: UsuarioGimnasio,
        nombre: String,
        diasEntreno: List<Boolean>
    ) {
        val dias = diasEntreno.mapIndexed { index, entreno ->
            if (entreno) PlantillaDia(
                nombre = "Día ${index + 1}",
                ejerciciosIds = emptyList()
            )
            else DiaDescanso()
        }

        val plantilla = PlantillaPlanSemanal(
            enfoque = perfil.enfoque,
            experiencia = perfil.experiencia,
            tipoRutina = TipoRutina.PERSONALIZADA,
            dias = dias
        )

        val plan = EntrenoRepository
            .generarPlanSemanalDesdePlantilla(perfil, plantilla)
            .copy(nombre = nombre)

        perfil.rutinas.add(plan)

        if (perfil.rutinaActiva == null) {
            perfil.rutinaActiva = plan
        }

        repositorio.guardarPerfil(perfil)
    }

    fun asignarRutinaActiva(perfil: UsuarioGimnasio, rutina: PlanSemanal) {
        perfil.rutinaActiva = rutina
        repositorio.guardarPerfil(perfil)
    }

    fun borrarRutina(perfil: UsuarioGimnasio, rutina: PlanSemanal) {
        perfil.rutinas.remove(rutina)
        if (perfil.rutinaActiva == rutina) {
            perfil.rutinaActiva = perfil.rutinas.firstOrNull()
        }
        repositorio.guardarPerfil(perfil)
    }

}