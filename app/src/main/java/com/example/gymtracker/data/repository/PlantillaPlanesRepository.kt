package com.example.gymtracker.data.repository

import com.example.gymtracker.model.DiaDescanso
import com.example.gymtracker.model.PlantillaDia
import com.example.gymtracker.model.PlantillaPlanSemanal
import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.Experiencia
import com.example.gymtracker.model.enum_classes.TipoRutina

object PlantillaPlanesRepository {

    fun obtenerPlan(
        enfoque: Enfoque,
        experiencia: Experiencia
    ): PlantillaPlanSemanal {
        return plantillas.firstOrNull {
            it.enfoque == enfoque && it.experiencia == experiencia
        } ?: error("No existe plan para $enfoque con experiencia $experiencia")
    }


    private val planHipertrofiaSinExperiencia = PlantillaPlanSemanal(
        enfoque = Enfoque.HIPERTROFIA,
        experiencia = Experiencia.NADA,
        dias = listOf(
            PlantillaDia(
                nombre = "Día 1 Full Body",
                ejerciciosIds = listOf(5, 42, 28, 3)
            ),
            PlantillaDia(
                nombre = "Día 2 Full Body",
                ejerciciosIds = listOf(7, 37, 15, 59)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 3 Full Body",
                ejerciciosIds = listOf(9, 45, 19, 57)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 4 Full Body",
                ejerciciosIds = listOf(1, 39, 20, 55)
            ),
            DiaDescanso(
                motivo = "Descanso"
            )
        ),
        tipoRutina = TipoRutina.FULL_BODY
    )

    private val planHipertrofiaPrincipiante = PlantillaPlanSemanal(
        enfoque = Enfoque.HIPERTROFIA,
        experiencia = Experiencia.MINIMA,
        dias = listOf(
            PlantillaDia(
                nombre = "Día 1 Torso",
                ejerciciosIds = listOf(11, 9, 46, 43, 21)
            ),
            PlantillaDia(
                nombre = "Día 2 Pierna",
                ejerciciosIds = listOf(50, 3, 59, 61, 55)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 3 Torso",
                ejerciciosIds = listOf(4, 47, 42, 28, 16)
            ),
            PlantillaDia(
                nombre = "Día 4 Pierna",
                ejerciciosIds = listOf(51, 52, 56, 62, 69)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
        ),
        tipoRutina = TipoRutina.UPPER_LOWER
    )

    private val planHipertrofiaAvanzado = PlantillaPlanSemanal(
        enfoque = Enfoque.HIPERTROFIA,
        experiencia = Experiencia.INTERMEDIA,
        dias = listOf(
            PlantillaDia("Día 1 PPL (Pecho / Tríceps / Hombro)", listOf(4, 7, 31, 28, 14, 15)),
            PlantillaDia("Día 2 PPL (Espalda / Bíceps)", listOf(46, 38, 40, 34, 20, 24)),
            DiaDescanso("Descanso"),
            PlantillaDia("Día 3 PPL (Pierna)", listOf(53, 50, 55, 58, 62)),
            PlantillaDia("Día 4 PPL (Pecho / Espalda)", listOf(5, 12, 44, 45, 8, 37)),
            DiaDescanso("Descanso"),
            DiaDescanso("Descanso")
        ),
        tipoRutina = TipoRutina.PUSH_PULL_LEGS
    )

    private val planHipertrofiaExperto = PlantillaPlanSemanal(
        enfoque = Enfoque.HIPERTROFIA,
        experiencia = Experiencia.AVANZADA,
        dias = listOf(
            PlantillaDia("Día 1 Pecho / Tríceps", listOf(6, 5, 10, 13, 17, 18)),
            PlantillaDia("Día 2 Pierna", listOf(53, 51, 56, 48, 60, 62)),
            PlantillaDia("Día 3 Espalda / Bíceps", listOf(2, 38, 41, 45, 21, 26, 64)),
            DiaDescanso("Descanso"),
            PlantillaDia("Día 4 Pierna", listOf(50, 57, 52, 66, 61, 59)),
            PlantillaDia("Día 5 Hombro / Core", listOf(31, 29, 30, 35, 67, 68)),
            DiaDescanso("Descanso")
        ),
        tipoRutina = TipoRutina.ALTA_FRECUENCIA
    )

    private val planDefinicionSinExperiencia = PlantillaPlanSemanal(
        enfoque = Enfoque.DEFINICION,
        experiencia = Experiencia.NADA,
        dias = listOf(
            PlantillaDia("Día 1 Full Body", listOf(11, 46, 50, 52, 69)),
            DiaDescanso("Descanso"),
            PlantillaDia("Día 2 Full Body", listOf(9, 39, 54, 56, 68)),
            DiaDescanso("Descanso"),
            PlantillaDia("Día 3 Full Body", listOf(12, 47, 59, 61, 67)),
            DiaDescanso("Descanso"),
            DiaDescanso("Descanso")
        ),
        tipoRutina = TipoRutina.FULL_BODY
    )

    private val planDefinicionPrincipiante = PlantillaPlanSemanal(
        enfoque = Enfoque.DEFINICION,
        experiencia = Experiencia.MINIMA,
        dias = listOf(
            PlantillaDia(
                nombre = "Día 1 Pecho", listOf(5, 8, 11, 1)),
            PlantillaDia("Día 2 Espalda / Hombro", listOf(46, 40, 32, 28, 34)),
            DiaDescanso("Descanso"),
            PlantillaDia("Día 3 Pierna", listOf(50, 52, 55, 62, 59)),
            PlantillaDia("Día 4 Brazos", listOf(20, 27, 15, 19, 63)),
            DiaDescanso("Descanso"),
            DiaDescanso("Descanso")
        ),
        tipoRutina = TipoRutina.DIVISION_GRUPOS_MUSCULARES
    )

    private val planDefinicionAvanzado = PlantillaPlanSemanal(
        enfoque = Enfoque.DEFINICION,
        experiencia = Experiencia.INTERMEDIA,
        dias = listOf(
            PlantillaDia(
                nombre = "Día 1 PPL (Pecho / Tríceps / Hombro)",
                ejerciciosIds = listOf(4, 31, 8, 28, 15)
            ),
            PlantillaDia(
                nombre = "Día 2 PPL (Espalda / Bíceps)",
                ejerciciosIds = listOf(46, 40, 34, 20, 24)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 3 PPL (Pierna)",
                ejerciciosIds = listOf(53, 66, 50, 55, 62)
            ),
            PlantillaDia(
                nombre = "Día 4 PPL (Pecho / Espalda)",
                ejerciciosIds = listOf(7, 44, 2, 9, 45)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            DiaDescanso(
                motivo = "Descanso"
            )
        ),
        tipoRutina = TipoRutina.PUSH_PULL_LEGS
    )

    private val planDefinicionExperto = PlantillaPlanSemanal(
        enfoque = Enfoque.DEFINICION,
        experiencia = Experiencia.AVANZADA,
        dias = listOf(
            PlantillaDia(
                nombre = "Día 1 Pecho / Espalda ",
                ejerciciosIds = listOf(4, 2, 7, 38, 8)
            ),
            PlantillaDia(
                nombre = "Día 2 Pierna",
                ejerciciosIds = listOf(51, 50, 52, 55, 62)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 3 Hombro / Pierna",
                ejerciciosIds = listOf(31, 66, 29, 58, 34)
            ),
            PlantillaDia(
                nombre = "Día 4 Pecho / Espalda",
                ejerciciosIds = listOf(11, 44, 9, 45, 47)
            ),
            PlantillaDia(
                nombre = "Día 5 Brazo / Abdominales",
                ejerciciosIds = listOf(21, 14, 24, 17, 67, 68)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
        ),
        tipoRutina = TipoRutina.ALTA_FRECUENCIA
    )

    private val planPerderPesoSinExperiencia = PlantillaPlanSemanal(
        enfoque = Enfoque.PERDER_PESO,
        experiencia = Experiencia.NADA,
        dias = listOf(
            PlantillaDia(
                nombre = "Día 1 Full Body",
                ejerciciosIds = listOf(50, 11, 46, 69)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 2 Full Body",
                ejerciciosIds = listOf(52, 9, 39, 68)
            ),
            PlantillaDia(
                nombre = "Día 3 Full Body",
                ejerciciosIds = listOf(54, 32, 47, 70)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 4 Full Body",
                ejerciciosIds = listOf(55, 1, 41, 59)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
        ),
        tipoRutina = TipoRutina.FULL_BODY
    )

    private val planPerderPesoPrincipiante = PlantillaPlanSemanal(
        enfoque = Enfoque.PERDER_PESO,
        experiencia = Experiencia.MINIMA,
        dias = listOf(
            PlantillaDia(
                nombre = "Día 1 Pecho",
                ejerciciosIds = listOf(5, 12, 9, 1)
            ),
            PlantillaDia(
                nombre = "Día 2 Espalda / Brazo",
                ejerciciosIds = listOf(46, 39, 20, 15)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 3 Pierna",
                ejerciciosIds = listOf(50, 57, 55, 62)
            ),
            PlantillaDia(
                nombre = "Día 4 Hombro / Abdominales",
                ejerciciosIds = listOf(32, 28, 67, 68)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            DiaDescanso(
                motivo = "Descanso"
            )
        ),
        tipoRutina = TipoRutina.DIVISION_GRUPOS_MUSCULARES
    )

    private val planPerderPesoAvanzado = PlantillaPlanSemanal(
        enfoque = Enfoque.PERDER_PESO,
        experiencia = Experiencia.INTERMEDIA,
        dias = listOf(
            PlantillaDia(
                nombre = "Día 1 Torso",
                ejerciciosIds = listOf(4, 44, 31, 46, 15)
            ),
            PlantillaDia(
                nombre = "Día 2 Pierna",
                ejerciciosIds = listOf(53, 66, 50, 62, 68)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 3 Torso",
                ejerciciosIds = listOf(2, 7, 40, 28, 20)
            ),
            PlantillaDia(
                nombre = "Día 4 Pierna",
                ejerciciosIds = listOf(51, 56, 58, 61, 67)
            ),
            PlantillaDia(
                nombre = "Día 5 Torso",
                ejerciciosIds = listOf(11, 41, 34, 13, 24)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
        ),
        tipoRutina = TipoRutina.UPPER_LOWER
    )

    private val planPerderPesoExperto = PlantillaPlanSemanal(
        enfoque = Enfoque.PERDER_PESO,
        experiencia = Experiencia.AVANZADA,
        dias = listOf(
            PlantillaDia(
                nombre = "Día 1 PPL (Pecho / Tríceps / Hombro)",
                ejerciciosIds = listOf(6, 31, 8, 28, 13, 15)
            ),
            PlantillaDia(
                nombre = "Día 2 PPL (Espalda / Bíceps)",
                ejerciciosIds = listOf(44, 46, 34, 26, 24, 45)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 3 PPL (Pierna)",
                ejerciciosIds = listOf(53, 66, 57, 55, 62)
            ),
            DiaDescanso(
                motivo = "Descanso"
            ),
            PlantillaDia(
                nombre = "Día 4 PPL (Pecho / Espalda)",
                ejerciciosIds = listOf(5, 38, 11, 42, 67)
            ),
            DiaDescanso(
                motivo = "Descanso"
            )
        ),
        tipoRutina = TipoRutina.PUSH_PULL_LEGS
    )


    private val plantillas = listOf(
        planHipertrofiaSinExperiencia,
        planHipertrofiaPrincipiante,
        planHipertrofiaAvanzado,
        planHipertrofiaExperto,
        planDefinicionSinExperiencia,
        planDefinicionPrincipiante,
        planDefinicionAvanzado,
        planDefinicionExperto,
        planPerderPesoSinExperiencia,
        planPerderPesoPrincipiante,
        planPerderPesoAvanzado,
        planPerderPesoExperto
    )
}