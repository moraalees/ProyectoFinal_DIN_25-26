package com.example.gymtracker.data.repository

import com.example.gymtracker.model.DiaDescanso
import com.example.gymtracker.model.EjercicioBase
import com.example.gymtracker.model.EjercicioPlan
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.model.enum_classes.Experiencia
import com.example.gymtracker.model.ParametrosEntreno
import com.example.gymtracker.model.PlanSemanal
import com.example.gymtracker.model.PlantillaDia
import com.example.gymtracker.model.PlantillaPlanSemanal
import com.example.gymtracker.model.UsuarioGimnasio
import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.TipoPeso
import kotlin.math.roundToInt


object EntrenoRepository {
    fun generarPlanSemanal(perfil: UsuarioGimnasio): PlanSemanal {
        val plantilla = PlantillaPlanesRepository.obtenerPlan(
            enfoque = perfil.enfoque,
            experiencia = perfil.experiencia
        )

        return generarDesdePlantilla(perfil, plantilla)
    }

    fun generarPlanSemanalDesdePlantilla(
        perfil: UsuarioGimnasio,
        plantilla: PlantillaPlanSemanal
    ): PlanSemanal {
        return generarDesdePlantilla(perfil, plantilla)
    }

    private fun generarDesdePlantilla(
        perfil: UsuarioGimnasio,
        plantilla: PlantillaPlanSemanal
    ): PlanSemanal {

        val catalogoEjercicios = EjerciciosRepository.obtenerEjerciciosPrincipales()
        val parametros = calcularParametros(perfil)

        val diasPlan = plantilla.dias.map { diaPlantilla ->
            when (diaPlantilla) {

                is PlantillaDia -> {
                    val ejerciciosDia = diaPlantilla.ejerciciosIds.mapNotNull { id ->
                        catalogoEjercicios.firstOrNull { it.id == id }
                    }

                    val ejerciciosPlan = ejerciciosDia.map { ejercicio ->
                        EjercicioPlan(
                            ejercicio = ejercicio,
                            series = parametros.series,
                            repeticiones = parametros.repeticiones,
                            pesoEstimado = estimarPeso(ejercicio, perfil)
                        )
                    }.toMutableList()

                    Entreno(
                        nombre = diaPlantilla.nombre,
                        ejercicios = ejerciciosPlan,
                        enfoque = perfil.enfoque,
                        esEntreno = true
                    )
                }

                is DiaDescanso -> {
                    Entreno(
                        nombre = diaPlantilla.motivo,
                        ejercicios = mutableListOf(),
                        enfoque = perfil.enfoque,
                        esEntreno = false
                    )
                }
            }
        }.toMutableList()

        return PlanSemanal(
            nombre = "Plan de ${perfil.enfoque} - (Experiencia: ${perfil.experiencia})".lowercase()
                .replace("_", " ")
                .split(" ")
                .joinToString(" ") {
                    it.replaceFirstChar { c -> c.uppercase() }
                },
            dias = diasPlan
        )
    }

    private fun calcularParametros(perfil: UsuarioGimnasio): ParametrosEntreno {
        return when (perfil.enfoque) {
            Enfoque.HIPERTROFIA -> when (perfil.experiencia) {
                Experiencia.NADA -> ParametrosEntreno(3, 10..12, 2.0)
                Experiencia.MINIMA -> ParametrosEntreno(3, 8..10, 2.0)
                Experiencia.INTERMEDIA -> ParametrosEntreno(4, 6..8, 3.0)
                Experiencia.AVANZADA -> ParametrosEntreno(4, 5..8, 3.0)
            }

            Enfoque.DEFINICION -> when (perfil.experiencia) {
                Experiencia.NADA -> ParametrosEntreno(3, 12..15, 1.0)
                Experiencia.MINIMA -> ParametrosEntreno(3, 10..12, 1.0)
                Experiencia.INTERMEDIA -> ParametrosEntreno(4, 8..12, 1.5)
                Experiencia.AVANZADA -> ParametrosEntreno(4, 8..12, 1.5)
            }

            Enfoque.PERDER_PESO -> ParametrosEntreno(2, 15..20, 0.5)
        }
    }

    private fun estimarPeso(ejercicio: EjercicioBase, perfil: UsuarioGimnasio): Double? {
        return when (ejercicio.tipoPeso) {
            TipoPeso.PROPIO_PESO -> null
            TipoPeso.MANCUERNAS -> when (perfil.experiencia) {
                Experiencia.NADA -> (0.1 * perfil.peso).roundToInt().toDouble()
                Experiencia.MINIMA -> (0.2 * perfil.peso).roundToInt().toDouble()
                Experiencia.INTERMEDIA -> (0.3 * perfil.peso).roundToInt().toDouble()
                Experiencia.AVANZADA -> (0.4 * perfil.peso).roundToInt().toDouble()
            }

            TipoPeso.BARRA -> when (perfil.experiencia) {
                Experiencia.NADA -> (0.25 * perfil.peso).roundToInt().toDouble()
                Experiencia.MINIMA -> (0.4 * perfil.peso).roundToInt().toDouble()
                Experiencia.INTERMEDIA -> (0.6 * perfil.peso).roundToInt().toDouble()
                Experiencia.AVANZADA -> (0.8 * perfil.peso).roundToInt().toDouble()
            }

            TipoPeso.POLEA -> when (perfil.experiencia) {
                Experiencia.NADA -> (0.1 * perfil.peso).roundToInt().toDouble()
                Experiencia.MINIMA -> (0.2 * perfil.peso).roundToInt().toDouble()
                Experiencia.INTERMEDIA -> (0.3 * perfil.peso).roundToInt().toDouble()
                Experiencia.AVANZADA -> (0.4 * perfil.peso).roundToInt().toDouble()
            }

            TipoPeso.MAQUINA -> when (perfil.experiencia) {
                Experiencia.NADA -> (0.25 * perfil.peso).roundToInt().toDouble()
                Experiencia.MINIMA -> (0.40 * perfil.peso).roundToInt().toDouble()
                Experiencia.INTERMEDIA -> (0.60 * perfil.peso).roundToInt().toDouble()
                Experiencia.AVANZADA -> (0.80 * perfil.peso).roundToInt().toDouble()
            }
        }
    }
}

