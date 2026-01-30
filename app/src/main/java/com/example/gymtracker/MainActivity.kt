package com.example.gymtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.gymtracker.data.local.json.EntrenamientosRealizadosJsonDataSource
import com.example.gymtracker.data.local.json.GuardadoJson
import com.example.gymtracker.data.local.json.UsuarioGimnasioJsonDataSource
import com.example.gymtracker.data.local.json.UsuarioJsonDataSource
import com.example.gymtracker.data.repository.EntrenamientosRepository
import com.example.gymtracker.data.repository.EntrenoRepository
import com.example.gymtracker.data.repository.UsuarioGimnasioRepository
import com.example.gymtracker.data.repository.UsuarioRepository
import com.example.gymtracker.ui.controllers.ControladorSesion
import com.example.gymtracker.ui.navigation.NavegadorPrincipal
import com.example.gymtracker.ui.navigation.Rutas
import com.example.gymtracker.ui.screens.actual_training.EntrenamientoViewModel
import com.example.gymtracker.ui.screens.form.FormularioViewModel
import com.example.gymtracker.ui.screens.form.PantallaFormulario
import com.example.gymtracker.ui.screens.home.HomeViewModel
import com.example.gymtracker.ui.screens.home.PantallaPrincipal
import com.example.gymtracker.ui.screens.login.LoginViewModel
import com.example.gymtracker.ui.screens.register.PantallaRegistro
import com.example.gymtracker.ui.screens.register.RegistroViewModel
import com.example.gymtracker.ui.screens.routines.RutinasViewModel
import com.example.gymtracker.ui.screens.training.EntrenoEspecificoViewModel
import com.example.gymtracker.ui.screens.userdata.DatosViewModel
import com.example.gymtracker.ui.utils.SolicitarPermisoNotificaciones
import com.example.gymtracker.ui.utils.crearCanalNotificaciones
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        crearCanalNotificaciones(this)

        val storage = GuardadoJson(applicationContext)
        val gson = Gson()

        val entrenamientosDataSource =
            EntrenamientosRealizadosJsonDataSource(applicationContext, gson)
        val usuarioDataSource = UsuarioJsonDataSource(storage, gson)
        val usuarioRepository = UsuarioRepository(usuarioDataSource)
        val loginViewModel = LoginViewModel(usuarioRepository, applicationContext)
        val registerViewModel = RegistroViewModel(usuarioRepository, applicationContext)

        val usuarioGimnasioDataSource = UsuarioGimnasioJsonDataSource(storage, gson)
        val usuarioGimnasioRepository = UsuarioGimnasioRepository(usuarioGimnasioDataSource)
        val entrenoRepository = EntrenamientosRepository(entrenamientosDataSource)
        val formularioViewModel = FormularioViewModel(usuarioGimnasioRepository)
        val homeViewModel = HomeViewModel(usuarioGimnasioRepository)
        val datosViewModel = DatosViewModel(usuarioRepository, usuarioGimnasioRepository)
        val rutinasViewModel = RutinasViewModel(usuarioGimnasioRepository)
        val entrenoEspecificoViewModel = EntrenoEspecificoViewModel(usuarioGimnasioRepository)
        val entrenamientoViewModel = EntrenamientoViewModel(entrenoRepository)



        setContent {
            SolicitarPermisoNotificaciones()

            val context = LocalContext.current

            NavegadorPrincipal(
                loginViewModel = loginViewModel,
                registerViewModel = registerViewModel,
                formularioViewModel = formularioViewModel,
                homeViewModel = homeViewModel,
                datosViewModel = datosViewModel,
                rutinasViewModel = rutinasViewModel,
                entrenamientoViewModel = entrenamientoViewModel,
                entrenamientoEspecificoViewModel = entrenoEspecificoViewModel,
                usuarioDataSource = usuarioDataSource,
                entrenamientosRepository = entrenoRepository,
                context = context
            )
        }
    }
}
