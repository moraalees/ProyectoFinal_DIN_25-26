package com.example.gymtracker.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gymtracker.data.local.json.UsuarioJsonDataSource
import com.example.gymtracker.data.repository.EntrenamientosRepository
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.ui.controllers.ControladorSesion
import com.example.gymtracker.ui.screens.actual_training.EntrenamientoViewModel
import com.example.gymtracker.ui.screens.actual_training.PantallaInicioEntreno
import com.example.gymtracker.ui.screens.admin.PantallaAdmin
import com.example.gymtracker.ui.screens.admin.PantallaManejoMarcas
import com.example.gymtracker.ui.screens.calendar.PantallaCalendario
import com.example.gymtracker.ui.screens.exercises.PantallaEjercicios
import com.example.gymtracker.ui.screens.exercises.PantallaEjerciciosPorMusculo
import com.example.gymtracker.ui.screens.exercises.PantallaEjerciciosPorTipoPeso
import com.example.gymtracker.ui.screens.form.FormularioViewModel
import com.example.gymtracker.ui.screens.form.PantallaFormulario
import com.example.gymtracker.ui.screens.home.HomeViewModel
import com.example.gymtracker.ui.screens.home.PantallaPrincipal
import com.example.gymtracker.ui.screens.login.LoginViewModel
import com.example.gymtracker.ui.screens.login.PantallaLogin
import com.example.gymtracker.ui.screens.records.PantallaMarcas
import com.example.gymtracker.ui.screens.records.PantallaMarcasRecords
import com.example.gymtracker.ui.screens.register.PantallaRegistro
import com.example.gymtracker.ui.screens.register.RegistroViewModel
import com.example.gymtracker.ui.screens.routines.PantallaRutinas
import com.example.gymtracker.ui.screens.routines.RutinasViewModel
import com.example.gymtracker.ui.screens.training.EntrenoEspecificoViewModel
import com.example.gymtracker.ui.screens.training.PantallaEntrenoEspecifico
import com.example.gymtracker.ui.screens.training.PantallaEntrenos
import com.example.gymtracker.ui.screens.userdata.DatosViewModel
import com.example.gymtracker.ui.screens.userdata.PantallaDatosUsuario
import kotlinx.coroutines.launch

@Composable
fun NavegadorPrincipal(
    loginViewModel: LoginViewModel,
    registerViewModel: RegistroViewModel,
    formularioViewModel: FormularioViewModel,
    homeViewModel: HomeViewModel,
    datosViewModel: DatosViewModel,
    rutinasViewModel: RutinasViewModel,
    entrenamientoViewModel: EntrenamientoViewModel,
    entrenamientoEspecificoViewModel: EntrenoEspecificoViewModel,
    usuarioDataSource: UsuarioJsonDataSource,
    entrenamientosRepository: EntrenamientosRepository,
    context: Context = LocalContext.current
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var inicioAplicacion by rememberSaveable { mutableStateOf(Rutas.LOGIN.ruta) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route
    var usuarioActivo by remember { mutableStateOf<Usuario?>(null) }

    LaunchedEffect(Unit) {
        ControladorSesion.cargarSesion(context, usuarioDataSource)
        usuarioActivo = ControladorSesion.usuarioLogueado()

        inicioAplicacion = if (usuarioActivo != null) {
            if (usuarioActivo!!.esAdmin) {
                Rutas.ADMIN.ruta
            } else {
                val perfil = rutinasViewModel.obtenerPerfil(usuarioActivo!!.id)
                if (perfil != null) Rutas.HOME.ruta else Rutas.FORMULARIO.ruta
            }
        } else {
            Rutas.LOGIN.ruta
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Opciones del Programa",
                    modifier = Modifier
                        .padding(16.dp)
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Navegar a Home"
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Rutas.HOME.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Mostrar Ejercicios"
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Rutas.EJERCICIOS.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Mostrar Rutinas"
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Rutas.RUTINAS.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Mostrar Marcas"
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Rutas.MARCAS.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Mostrar Calendario"
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Rutas.CALENDARIO.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Récords Globales"
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Rutas.MARCAS_RECORDS.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Cerrar Sesión"
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            ControladorSesion.cerrarSesion(context)
                            navController.navigate(Rutas.LOGIN.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (rutaActual != Rutas.REGISTRO.ruta &&
                    rutaActual != Rutas.LOGIN.ruta &&
                    rutaActual != Rutas.FORMULARIO.ruta &&
                    rutaActual != Rutas.CREDENCIALES.ruta &&
                    rutaActual != Rutas.ENTRENOS.ruta &&
                    rutaActual != Rutas.ENTRENO_ESPECIFICO.ruta &&
                    rutaActual != Rutas.INICIO_ENTRENO.ruta
                ) {
                    usuarioActivo?.let { usuario ->
                        MiTopBar(
                            usuario = usuario,
                            navegarPantallaCredenciales = {
                                navController.navigate(Rutas.CREDENCIALES.ruta) {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            desplegarMenu = {
                                if (!usuario.esAdmin)
                                scope.launch { drawerState.open() }
                            },
                            mostrarMenu = !usuario.esAdmin
                        )
                    }
                }
            },
            bottomBar = {
                if (
                    rutaActual == Rutas.EJERCICIOS.ruta ||
                    rutaActual == Rutas.EJERCICIOS_MUSCULO.ruta ||
                    rutaActual == Rutas.EJERCICIOS_TIPO_PESO.ruta
                ) {
                    MiBottomBar(navController)
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = inicioAplicacion,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Rutas.LOGIN.ruta) {
                    PantallaLogin(
                        viewModel = loginViewModel,
                        loginExitoso = {
                            val usuario = ControladorSesion.usuarioLogueado()!!
                            usuarioActivo = usuario
                            val ruta = if (usuario.esAdmin) {
                                Rutas.ADMIN.ruta
                            } else {
                                val perfil = rutinasViewModel.obtenerPerfil(usuario.id)
                                if (perfil != null) Rutas.HOME.ruta else Rutas.FORMULARIO.ruta
                            }

                            navController.navigate(ruta) {
                                popUpTo(Rutas.LOGIN.ruta) { inclusive = true }
                            }
                        },
                        pantallaRegistro = { navController.navigate(Rutas.REGISTRO.ruta) },
                        pantallaAdmin = { navController.navigate(Rutas.ADMIN.ruta) }
                    )
                }

                composable(Rutas.REGISTRO.ruta) {
                    PantallaRegistro(
                        viewModel = registerViewModel,
                        registroExitoso = {
                            navController.navigate(Rutas.FORMULARIO.ruta) { popUpTo(Rutas.REGISTRO.ruta) { inclusive = true } }
                        },
                        pantallaLogin = { navController.popBackStack() }
                    )
                }

                composable(Rutas.FORMULARIO.ruta) {
                    PantallaFormulario(
                        onGuardar = { experiencia, enfoque, edad, altura, peso ->
                            val perfilGuardado = formularioViewModel.guardarPerfil(
                                edad = edad,
                                altura = altura,
                                peso = peso,
                                experiencia = experiencia,
                                enfoque = enfoque
                            )

                            if (perfilGuardado != null) {
                                usuarioActivo = ControladorSesion.usuarioLogueado()

                                navController.navigate(Rutas.HOME.ruta) {
                                    popUpTo(Rutas.FORMULARIO.ruta) { inclusive = true }
                                }
                            }
                        }
                    )
                }

                composable(Rutas.HOME.ruta) {
                    val usuario = ControladorSesion.usuarioLogueado()
                    if (usuario != null) {
                        val rutinaActiva = homeViewModel.rutinaActiva

                        PantallaPrincipal(
                            usuario = usuario,
                            viewModel = homeViewModel,
                            iniciarEntreno = {
                                rutinaActiva?.let { rutina ->
                                    navController.currentBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("rutinaId", rutina.id)

                                    navController.navigate(Rutas.INICIO_ENTRENO.ruta)
                                }
                            }
                        )
                    }
                }

                composable(Rutas.CREDENCIALES.ruta){
                    val usuario = ControladorSesion.usuarioLogueado()
                    if (usuario != null){
                        PantallaDatosUsuario(
                            usuario = usuario,
                            volverHome = {
                                if (!usuario.esAdmin){
                                    navController.navigate(Rutas.HOME.ruta) { popUpTo(Rutas.HOME.ruta) { inclusive = true } }
                                } else {
                                    navController.navigate(Rutas.ADMIN.ruta) { popUpTo(Rutas.ADMIN.ruta) { inclusive = true } }
                                }

                            },
                            viewModel = datosViewModel
                        )
                    }
                }

                composable(Rutas.RUTINAS.ruta) {
                    val usuario = ControladorSesion.usuarioLogueado()
                    if (usuario != null) {
                        PantallaRutinas(
                            rutinaViewModel = rutinasViewModel,
                            usuario = usuario,
                            onModificarRutina = { rutinaSeleccionada ->
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("rutinaId", rutinaSeleccionada.id)
                                navController.navigate(Rutas.ENTRENOS.ruta)
                            }
                        )
                    }
                }

                composable(Rutas.EJERCICIOS.ruta){
                    PantallaEjercicios(
                        context = context
                    )
                }

                composable(Rutas.EJERCICIOS_MUSCULO.ruta){
                    PantallaEjerciciosPorMusculo(
                        context = context
                    )
                }

                composable(Rutas.EJERCICIOS_TIPO_PESO.ruta){
                    PantallaEjerciciosPorTipoPeso(
                        context = context
                    )
                }

                composable(Rutas.MARCAS.ruta){
                    val usuario = ControladorSesion.usuarioLogueado()
                    PantallaMarcas(
                        entrenamientosRepository = entrenamientosRepository,
                        usuarioId = usuario?.id

                    )
                }

                composable(Rutas.CALENDARIO.ruta){
                    PantallaCalendario(entrenamientoViewModel = entrenamientoViewModel)
                }

                composable(Rutas.ENTRENOS.ruta) {
                    val usuario = ControladorSesion.usuarioLogueado()
                    if (usuario != null) {
                        val rutinaId = navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<String>("rutinaId")

                        val perfil = rutinasViewModel.obtenerPerfil(usuario.id)
                        val rutina = perfil?.rutinas?.find { it.id == rutinaId }

                        if (rutina != null) {
                            PantallaEntrenos(
                                rutina = rutina,
                                onEntrenoClick = { entrenoSeleccionado, rutina ->
                                    navController.currentBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("entrenoId", entrenoSeleccionado.id)
                                    navController.currentBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("rutinaId", rutina.id)

                                    navController.navigate(Rutas.ENTRENO_ESPECIFICO.ruta)
                                }
                            )
                        }
                    }
                }

                composable(Rutas.ENTRENO_ESPECIFICO.ruta) {
                    val usuario = ControladorSesion.usuarioLogueado()
                    if (usuario != null) {
                        val entrenoId = navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<String>("entrenoId")
                        val rutinaId = navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<String>("rutinaId")

                        val perfil = rutinasViewModel.obtenerPerfil(usuario.id)
                        val rutina = perfil?.rutinas?.find { it.id == rutinaId }
                        val entreno = rutina?.dias?.find { it.id == entrenoId }

                        if (rutina != null && entreno != null) {
                            PantallaEntrenoEspecifico(
                                entreno = entreno,
                                rutinaId = rutina.id,
                                usuario = usuario,
                                viewModel = entrenamientoEspecificoViewModel,
                                pantallaAnterior = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }

                composable(Rutas.INICIO_ENTRENO.ruta) {
                    val usuario = ControladorSesion.usuarioLogueado()
                    if (usuario != null) {
                        val rutinaId = navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<String>("rutinaId")

                        val perfilGimnasio = rutinasViewModel.obtenerPerfil(usuario.id)
                        val rutinaActiva = perfilGimnasio?.rutinas?.find { it.id == rutinaId }

                        if (rutinaActiva != null) {
                            PantallaInicioEntreno(
                                plan = rutinaActiva,
                                viewModel = entrenamientoViewModel,
                                context = context,
                                navegarHome = {
                                    navController.navigate(Rutas.HOME.ruta)
                                }
                            )
                        }
                    }
                }

                composable(Rutas.ADMIN.ruta) {
                    PantallaAdmin(
                        pantallaRecords = {
                            navController.navigate(Rutas.MANEJO_MARCAS_ADMIN.ruta)
                        },
                        cerrarSesion = {
                            scope.launch {
                                ControladorSesion.cerrarSesion(context)
                                navController.navigate(Rutas.LOGIN.ruta) { popUpTo(0) { inclusive = true } }
                            }
                        }
                    )
                }

                composable(Rutas.MARCAS_RECORDS.ruta){
                    PantallaMarcasRecords()
                }

                composable(Rutas.MANEJO_MARCAS_ADMIN.ruta){
                    PantallaManejoMarcas(
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
