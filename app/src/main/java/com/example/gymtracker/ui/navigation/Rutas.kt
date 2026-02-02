package com.example.gymtracker.ui.navigation

/**
 * Enum que define todas las rutas de navegación dentro de la aplicación.
 *
 * Cada valor representa una pantalla o sección de la app y almacena la ruta
 * utilizada por el NavController para navegar entre pantallas.
 *
 * Rutas disponibles:
 * - LOGIN: Pantalla de inicio de sesión.
 * - REGISTRO: Pantalla de registro de nuevos usuarios.
 * - FORMULARIO: Pantalla para completar o actualizar perfil de usuario.
 * - HOME: Pantalla principal del usuario (resumen y acceso a rutinas).
 * - CREDENCIALES: Pantalla para ver o editar datos del usuario.
 * - EJERCICIOS: Pantalla que muestra todos los ejercicios.
 * - CALENDARIO: Pantalla con calendario de entrenamientos.
 * - RUTINAS: Pantalla que muestra las rutinas del usuario.
 * - EJERCICIOS_MUSCULO: Pantalla de ejercicios filtrados por músculo.
 * - EJERCICIOS_TIPO_PESO: Pantalla de ejercicios filtrados por tipo de peso.
 * - MARCAS: Pantalla que muestra las marcas personales del usuario.
 * - ENTRENOS: Pantalla que muestra los entrenamientos de una rutina.
 * - ENTRENO_ESPECIFICO: Pantalla de detalle de un entrenamiento específico.
 * - INICIO_ENTRENO: Pantalla de inicio de un entrenamiento activo.
 * - ADMIN: Pantalla principal para usuarios administradores.
 * - MARCAS_RECORDS: Pantalla que muestra récords globales de ejercicios.
 * - MANEJO_MARCAS_ADMIN: Pantalla de administración de marcas.
 *
 * @property ruta Cadena que identifica de manera única la ruta para NavController.
 */
enum class Rutas(val ruta: String) {
    LOGIN("login"),
    REGISTRO("registro"),
    FORMULARIO("formulario"),
    HOME("home"),
    CREDENCIALES("datos"),
    EJERCICIOS("ejercicios"),
    CALENDARIO("calendario"),
    RUTINAS("rutinas"),
    EJERCICIOS_MUSCULO("ejericios_musculos"),
    EJERCICIOS_TIPO_PESO("ejercicios_tipo_peso"),
    MARCAS("marcas"),
    ENTRENOS("entrenos"),
    ENTRENO_ESPECIFICO("entreno_especifico"),
    INICIO_ENTRENO("inicio_entreno"),
    ADMIN("admin"),
    MARCAS_RECORDS("marcas_records"),
    MANEJO_MARCAS_ADMIN("manejo_marcas_admin")
}