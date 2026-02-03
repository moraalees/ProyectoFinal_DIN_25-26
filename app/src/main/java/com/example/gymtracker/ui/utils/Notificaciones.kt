package com.example.gymtracker.ui.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.gymtracker.R

/**
 * Constantes para manejo de notificaciones de descanso.
 */
const val CANAL_DESCANSO = "canal_descanso"
const val ID_NOTIFICACION_DESCANSO = 1001

/**
 * Crea el canal de notificaciones para los descansos de entrenamiento.
 *
 * Solo tiene efecto en Android O (API 26) o superior.
 *
 * @param context Contexto de la aplicación.
 */
fun crearCanalNotificaciones(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val canal = NotificationChannel(
            CANAL_DESCANSO,
            "Descansos de entrenamiento",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notificaciones de tiempo de descanso"
        }

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(canal)
    }
}

/**
 * Muestra una notificación de descanso en progreso con tiempo restante.
 *
 * @param context Contexto de la aplicación.
 * @param segundosRestantes Tiempo restante de descanso en segundos.
 */
fun mostrarNotificacionDescanso(
    context: Context,
    segundosRestantes: Int
) {
    val tienePermiso = tienePermisoNotificaciones(context)
    Log.d("NOTIF", "Permiso notificaciones: $tienePermiso")
    if (!tienePermisoNotificaciones(context)) return

    val minutos = segundosRestantes / 60
    val segundos = segundosRestantes % 60

    val notificacion = NotificationCompat.Builder(context, CANAL_DESCANSO)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle("Descanso en progreso")
        .setContentText("Tiempo restante: %02d:%02d".format(minutos, segundos))
        .setOngoing(true)
        .setOnlyAlertOnce(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    try {
        NotificationManagerCompat.from(context)
            .notify(ID_NOTIFICACION_DESCANSO, notificacion)
    } catch (_: SecurityException) {

    }
}

/**
 * Cancela la notificación de descanso en curso.
 *
 * @param context Contexto de la aplicación.
 */
fun cancelarNotificacionDescanso(context: Context) {
    try {
        NotificationManagerCompat.from(context)
            .cancel(ID_NOTIFICACION_DESCANSO)
    } catch (_: SecurityException) {
    }
}

/**
 * Muestra una notificación indicando que el descanso ha finalizado.
 *
 * @param context Contexto de la aplicación.
 */
fun mostrarNotificacionFinDescanso(context: Context) {
    val tienePermiso = tienePermisoNotificaciones(context)
    Log.d("NOTIF", "Permiso notificaciones: $tienePermiso")

    if (!tienePermisoNotificaciones(context)) return

    val notificacion = NotificationCompat.Builder(context, CANAL_DESCANSO)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle("Descanso terminado")
        .setContentText("¡Vuelve al ejercicio!")
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    try {
        NotificationManagerCompat.from(context)
            .notify(ID_NOTIFICACION_DESCANSO + 1, notificacion)
    } catch (_: SecurityException) {
    }
}

/**
 * Verifica si la aplicación tiene permisos para mostrar notificaciones.
 *
 * @param context Contexto de la aplicación.
 * @return true si se permiten notificaciones, false en caso contrario.
 */
fun tienePermisoNotificaciones(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}