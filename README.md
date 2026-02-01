# GymTracker

---

## Descripción

**GymTracker** es una aplicación, diseñada con el framework JetpackCompose, que trata de simular ser una guía para principiantes en mundo de las pesas y el fitness. Al contrario que otras muchas aplicaciones que 
podemos encontrar en cualquier tienda virtual de teléfono, esta presenta varias innovaciones y características que fomentan que una persona principiante no abandone en sus primeros días.

---

## Motivación

Hoy en día es bastante común querer hacer deporte y llevar un estilo de vida más activo. Sin embargo, no siempre es fácil saber por dónde empezar o qué es realmente efectivo. En redes sociales circula mucha 
información falsa o poco fiable, lo que genera confusión y expectativas irreales. A esto se suma la falta de disciplina y constancia, ya que muchas personas abandonan al no ver resultados rápidos.

Además, el progreso en el deporte suele ser lento y requiere paciencia, algo que cuesta mantener cuando no se tienen referencias claras de mejora. Sin una guía adecuada, es fácil desmotivarse, entrenar sin 
rumbo o incluso lesionarse. Por eso, muchas veces el problema no es la falta de ganas, sino la falta de una herramienta que ayude a entrenar de forma ordenada y realista.

**Gymtracker** nace precisamente para resolver esto. Te ayuda a empezar según tu perfil y nivel, generando una rutina automáticamente adaptada a ti. Además, te permite registrar tus marcas y entrenamientos, 
haciendo visible tu progreso y motivándote a ser constante. Ver tus mejoras con el tiempo te anima a superarte y a mantener el hábito, convirtiendo el deporte en algo sostenible y motivador.

---

## Estructura del proyecto

Con el fin de poder organizar las clases y funciones de mi aplicación, he decidido seguir una cierta estructura de paquetes:

- [`Directorio Data`](app/src/main/java/com/example/gymtracker/data): Actúa como la capa más externa de la aplicación. Esta capa sabe detalles técnicos debido a las carpetas y archivos que albergan en su interior, porque son clases que manejan rutas y formatos de la aplicación. Se compone por:
  - [`Carpeta Local.Json`](app/src/main/java/com/example/gymtracker/data/local/json): Contiene los Data Source locales. En sus clases se definen las rutas de donde salen los datos.
  - [`Carpeta Repository`](app/src/main/java/com/example/gymtracker/data/repository): Como su nombre indica, contiene los repositorios que usan los Data Source que son llamados desde los View Model de las pantallas. Estos guardan, actualizan o borran datos en los JSON de los Data Source. Son un intermediario entre la lógica y los datos de la aplicación.
- [`Directorio Model`](app/src/main/java/com/example/gymtracker/model): Se encarga de definir la estructura de los datos de la aplicación, albergando las clases de la aplicación.
  - [`Carpeta Enum_Classes`](app/src/main/java/com/example/gymtracker/model/enum_classes): Contiene casi todas las enum class de la aplicación.
- [`Directorio UI`](app/src/main/java/com/example/gymtracker/ui): Encargado de la presentación e interacción con el usuario. Alberga todo lo que el usuario ve, toca y cómo se mueve por la aplicación.
  - [`Carpeta Components`](app/src/main/java/com/example/gymtracker/ui/components): Contiene componentes reutilizables de la interfaz a lo largo de la aplicación. Piezas genéricas que se repiten en el programa para evitar duplicar código.
  - [`Carpeta Controllers`](app/src/main/java/com/example/gymtracker/ui/controllers): Agrupa los controladores de estado de la UI. En este caso, solo alberga la sesión activa del usuario. Se encargan de coordinar la lógica necesaria para que la interfaz sepa qué debe mostrar y en qué momento.
  - [`Carpeta Navigation`](app/src/main/java/com/example/gymtracker/ui/navigation): Define la estructura de navegación de la aplicación. Especifica cómo y cuándo se pasa de una pantalla a otra. Alberga rutas como una enum class, destinos y el flujo del programa.
  - [`Carpeta Screens`](app/src/main/java/com/example/gymtracker/ui/screens): Aquí se encuentra muchísimas carpetas por cada pantalla del programa junto, en la mayoría de los casos, sus View Model. Las pantallas albergan componentes y representan una vista concreta al usuario.
  - [`Carpeta Theme`](app/src/main/java/com/example/gymtracker/ui/theme): Gestiona la identidad visual de la aplicación.
  - [`Carpeta Utils`](app/src/main/java/com/example/gymtracker/ui/utils): Contiene utilidades específicas de la UI, como en este caso, el manejo de notificaciones cuando estás haciendo un entreno.

---

## Flujo del programa

1. Un usuario se registra en la aplicación mediante la [`PantallaRegistro`](app/src/main/java/com/example/gymtracker/ui/screens/register/PantallaRegistro.kt). El usuario introduce sus credenciales, y tras una comprobación de estas y una comprobación del View Model, al pulsar el botón de registro, se llama a la acción `registroExitoso`, que redirecta a la [`PantallaFormulario`](app/src/main/java/com/example/gymtracker/ui/screens/form/PantallaFormulario.kt).
2. En esta nueva pantalla, el usuario rellena sus datos físicos como Edad o Altura. Según sus datos, se generará una rutina automáticamente gracias al método de [`FormularioViewModel.guardarPerfil()`](https://github.com/moraalees/ProyectoFinal_DIN_25-26/blob/b683a7a23fb1802979d958040408d792c6c73226/app/src/main/java/com/example/gymtracker/ui/screens/form/FormularioViewModel.kt#L16C5-L44C6). Aquí, se llama al [`RepositorioUsuarioGimnasio`](app/src/main/java/com/example/gymtracker/data/repository/UsuarioGimnasioRepository.kt) y gracias al objeto [`EntrenoRepository`](app/src/main/java/com/example/gymtracker/data/repository/EntrenoRepository.kt), dependiendo del perfil de gimnasio que tenga el usuario, genera la rutina adecuada. Una vez se ha creado, el usuario será redirigidoo a la [`PantallaPrincipal`](app/src/main/java/com/example/gymtracker/ui/screens/home/PantallaPrincipal.kt).
3. Aquí, el usuario podrá navegar a varios sitios de la aplicación, gracias al [`TopAppBar`](app/src/main/java/com/example/gymtracker/ui/navigation/MiTopBar.kt) o a la rutina activa de su perfil de gimnasio.
- [`PantallaDatosUsuario`](app/src/main/java/com/example/gymtracker/ui/screens/userdata/PantallaDatosUsuario.kt): Podrá navegar a una pantalla en donde se muestran o bien sus datos personales, como su Enfoque o Peso, o bien sus credenciales, como correo o contraseña.
- [`PantallaEjercicios`](app/src/main/java/com/example/gymtracker/ui/screens/exercises/PantallaEjercicios.kt): En esta pantalla se muestran todos los ejercicios registrados por defecto o añadidos por el usuario en la aplicación. Todos los ejercicios son obtenidos gracias a [`EjerciciosRepository`](app/src/main/java/com/example/gymtracker/data/repository/EjerciciosRepository.kt), que posee una lista privada con los ejercicios registrados. Debido a varias características del programa, he creado 2 listas diferentes. La primera posee los ejercicios por defecto, mientras que la otra, gracias al método del repositorio `anadirEjercicio`, añade ejercicios creados por el usuario. Además, esta pantalla posee un [`BottomAppBar`](app/src/main/java/com/example/gymtracker/ui/navigation/MiBottomBar.kt), que permite navegar a otras vistas de los ejercicios del sistema. En estas vistas se mostrarán o bien clasificados por TipoPeso, o bien por Músculo que ejercita.
- [`PantallaRutinas`](app/src/main/java/com/example/gymtracker/ui/screens/routines/PantallaRutinas.kt): Se muestran las rutinas que el usuario tiene registradas en su perfil. Podrá añadir una rutina eligiendo los días en los que le toca descanso o entreno. Estos últimos no tendrán ejercicios asignados como en la rutina autogenerada. El usuario podrá establecer una como activa, para entrenarla, podrá modificar los días y ejercicios de esta, o bien borrarla. Si la establece como activa, aparecerá en la [`PantallaPrincipal`](app/src/main/java/com/example/gymtracker/ui/screens/home/PantallaPrincipal.kt). Si la borra, se eliminará de la pantalla. Si la modifica, navegará a otra pantalla en donde podrá elegir el día y, si lo prefiere como día de descanso, cambiarlo a entreno, o viceversa. Si es un día de entreno, podrá añadir ejercicios, también de los suyos propios, no solo los de por defecto. Cuando añada los ejercicios, deberá elegir repeticiones, nº se series y peso a levantar en el ejercicio, como una guía.
- 
