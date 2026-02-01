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
2. En esta nueva pantalla, el usuario rellena sus datos físicos como Edad o Altura. Según sus datos, se generará una rutina automáticamente gracias al método de [`FormularioViewModel.guardarPerfil()`]. Aquí, se llama al [`RepositorioUsuarioGimnasio`](app/src/main/java/com/example/gymtracker/data/repository/UsuarioGimnasioRepository.kt) y gracias al objeto [`EntrenoRepository`](app/src/main/java/com/example/gymtracker/data/repository/EntrenoRepository.kt), dependiendo del perfil de gimnasio que tenga el usuario, genera la rutina adecuada. Una vez se ha creado, el usuario será redirigidoo a la [`PantallaPrincipal`](app/src/main/java/com/example/gymtracker/ui/screens/home/PantallaPrincipal.kt).
3. Aquí, el usuario podrá navegar a varios sitios de la aplicación, gracias al [`TopAppBar`](app/src/main/java/com/example/gymtracker/ui/navigation/MiTopBar.kt) o a la rutina activa de su perfil de gimnasio.
- [`PantallaDatosUsuario`](app/src/main/java/com/example/gymtracker/ui/screens/userdata/PantallaDatosUsuario.kt): Podrá navegar a una pantalla en donde se muestran o bien sus datos personales, como su Enfoque o Peso, o bien sus credenciales, como correo o contraseña.
- [`PantallaEjercicios`](app/src/main/java/com/example/gymtracker/ui/screens/exercises/PantallaEjercicios.kt): En esta pantalla se muestran todos los ejercicios registrados por defecto o añadidos por el usuario en la aplicación. Todos los ejercicios son obtenidos gracias a [`EjerciciosRepository`](app/src/main/java/com/example/gymtracker/data/repository/EjerciciosRepository.kt), que posee una lista privada con los ejercicios registrados. Debido a varias características del programa, he creado 2 listas diferentes. La primera posee los ejercicios por defecto, mientras que la otra, gracias al método del repositorio `anadirEjercicio`, añade ejercicios creados por el usuario. Además, esta pantalla posee un [`BottomAppBar`](app/src/main/java/com/example/gymtracker/ui/navigation/MiBottomBar.kt), que permite navegar a otras vistas de los ejercicios del sistema. En estas vistas se mostrarán o bien clasificados por TipoPeso, o bien por Músculo que ejercita.
- [`PantallaRutinas`](app/src/main/java/com/example/gymtracker/ui/screens/routines/PantallaRutinas.kt): Se muestran las rutinas que el usuario tiene registradas en su perfil. Podrá añadir una rutina eligiendo los días en los que le toca descanso o entreno. Estos últimos no tendrán ejercicios asignados como en la rutina autogenerada. El usuario podrá establecer una como activa, para entrenarla, podrá modificar los días y ejercicios de esta, o bien borrarla. Si la establece como activa, aparecerá en la [`PantallaPrincipal`](app/src/main/java/com/example/gymtracker/ui/screens/home/PantallaPrincipal.kt). Si la borra, se eliminará de la pantalla. Si la modifica, navegará a otra pantalla en donde podrá elegir el día y, si lo prefiere como día de descanso, cambiarlo a entreno, o viceversa. Si es un día de entreno, podrá añadir ejercicios, también de los suyos propios, no solo los de por defecto. Cuando añada los ejercicios, deberá elegir repeticiones, nº se series y peso a levantar en el ejercicio, como una guía.
- [`PantallaMarcas`](app/src/main/java/com/example/gymtracker/ui/screens/records/PantallaMarcas.kt): Aquí se mostrarán todas las marcas personales del usuario y cuándo las consiguió. Como son un número indeterminado de ejercicios, ezxiste un buscador.
- [`PantallaCalendario`](app/src/main/java/com/example/gymtracker/ui/screens/calendar/PantallaCalendario.kt): Similar a la pantalla de marcas, aquí se guardan los entrenos del usuario y cuándo los hizo. Se muestran los datos del entreno que realizó, como cuántos y cuáles ejercicios entrenó, las series, repeticiones... Como futura impkementación, se plantea implementar un panel de estadísticas con todos los entrenos, como cuál fue el ejercicio más entrenado en X intervalo de tiempo, cuántas veces entrenó...
- [`PantallaMarcasRecords`](app/src/main/java/com/example/gymtracker/ui/screens/records/PantallaMarcasRecords.kt): En esta pantalla se albergan las mejores marcas de todos los usuarios registrados en la aplicación. Un usuario, para registrar su marca, debe escoger un ejercicio, introducir los datos de su serie y adjuntar un vídeo de demostración. Tras todo esto, solicitará al administrador aparecer en el podio del ejercicio. Podrá observar las marcas de los usuarios que estén en el top 1. Gracias a esto, nace una competitividad sana entre los usuarios para mejorar.
- [`PantallaInicioEntreno`](app/src/main/java/com/example/gymtracker/ui/screens/actual_training/PantallaInicioEntreno.kt): Empezará un entreno. Se mostrarán los ejercicios del día que sea según la rutina activa y pedirá por pantalla un número de repeticiones y un peso. Tras introducirlo, aparecerá un contador de 2 minutos de descanso entre series. El usuario podrá alargar ese tiempo en tramos de 10 segundos o terminar el contador. Aparecerán notificaciones gracias a [`Notificaciones`](app/src/main/java/com/example/gymtracker/ui/utils/Notificaciones.kt). Seguirá así hasta terminar el entreno. Cuando se termine, se guardará el entreno en el día del calendario.
4. El usuario podrá cerrar su sesión si desea entrar en otra cuenta o de otra forma, en modo administrador.
5. Si el usuario es administrador, no podrá navegar a las pantallas ya mencionadas, ya que solo tiene disponible [`PantallaAdmin`](app/src/main/java/com/example/gymtracker/ui/screens/admin/PantallaAdmin.kt). Esta pantalla presenta dos botones. El primero, navega a [`PantallaManejoMarcas`](app/src/main/java/com/example/gymtracker/ui/screens/admin/PantallaManejoMarcas.kt) donde se muestran las solicitudes de los usuarios a ser podio de un ejercicio concreto. El administrador verá los vídeos y dictaminará si entra o no al podio. El otro botón sirve para cerrar la sesión.

---

## Características Principales

---

## Justificación de Diseño y Usabilidad

---

## Herramientas y librerías

Aquí se nombrarán herramientas como JetPack compose, NUI, informes...

---

## Empaquetado de la aplicación

---

## Manual de usuario de la aplicación

### Registro y creación del perfil

Al iniciar la aplicación, el usuario puede registrarse desde la Pantalla de Registro introduciendo sus credenciales. Una vez completado el registro correctamente, la aplicación le redirigirá a un formulario de datos personales, donde deberá introducir información física como la edad, la altura u otros datos relevantes. Con esta información, la aplicación generará automáticamente una rutina de entrenamiento adaptada a su perfil. Tras completar el formulario, el usuario accederá a la Pantalla Principal.

### Pantalla Principal y navegación

Desde la Pantalla Principal, el usuario puede desplazarse por las distintas secciones de la aplicación utilizando la barra superior o accediendo directamente a su rutina activa. Esta pantalla actúa como punto central desde el que se accede al resto de funcionalidades.

### Datos del usuario

En la Pantalla de Datos del Usuario, el usuario puede consultar y revisar su información personal, como su peso o enfoque de entrenamiento, así como sus credenciales, como el correo electrónico o la contraseña.

### Ejercicios

La Pantalla de Ejercicios muestra todos los ejercicios disponibles en la aplicación. Incluye tanto ejercicios predefinidos como ejercicios añadidos por el propio usuario. Además, dispone de una barra inferior que permite filtrar los ejercicios según distintos criterios, como el tipo de peso utilizado o el músculo trabajado.

### Rutinas

En la Pantalla de Rutinas, el usuario puede consultar las rutinas asociadas a su perfil. Desde aquí es posible:
- Crear nuevas rutinas, eligiendo los días de descanso y de entrenamiento.
- Establecer una rutina como activa.
- Modificar rutinas existentes, cambiando días de descanso por días de entrenamiento o ajustando los ejercicios.
- Eliminar rutinas que ya no se deseen.

Cuando se modifica una rutina, el usuario puede añadir ejercicios (tanto propios como predefinidos) y definir parámetros como repeticiones, número de series y peso recomendado.

### Marcas personales

La Pantalla de Marcas muestra las marcas personales alcanzadas por el usuario y la fecha en la que se consiguieron. Debido a la gran cantidad de ejercicios, esta pantalla incluye un buscador para facilitar la localización de marcas concretas.

### Calendario de entrenamientos

En la Pantalla de Calendario, el usuario puede consultar los entrenamientos realizados en cada día. Se muestran detalles como los ejercicios entrenados, las series y las repeticiones. En futuras versiones de la aplicación, se incorporará un panel de estadísticas para analizar el rendimiento a lo largo del tiempo.

### Marcas récord

La Pantalla de Marcas Récord recoge las mejores marcas de todos los usuarios de la aplicación. Para aparecer en el podio de un ejercicio, el usuario debe:
1. Seleccionar el ejercicio.
2. Introducir los datos de su marca.
3. Adjuntar un vídeo como demostración.
4. Enviar la solicitud para su validación.

Estas marcas fomentan una competitividad sana entre los usuarios y sirven como motivación para mejorar.

### Inicio de un entrenamiento

Desde la Pantalla de Inicio de Entrenamiento, el usuario comienza una sesión de entreno basada en su rutina activa. La aplicación muestra los ejercicios del día y solicita los datos realizados (repeticiones y peso). Entre series, se activa un contador de descanso de dos minutos, que el usuario puede ampliar o finalizar manualmente. Durante el entrenamiento, la aplicación envía notificaciones para guiar el proceso. Al finalizar la sesión, el entreno se guarda automáticamente en el calendario.

### Cierre de sesión y modo administrador

El usuario puede cerrar sesión en cualquier momento para acceder con otra cuenta.

Si el usuario accede en modo administrador, solo tendrá disponible la Pantalla de Administración, desde la cual podrá:
- Gestionar las solicitudes de los usuarios para aparecer en el podio de marcas, revisando los vídeos y aceptando o rechazando las solicitudes.
- Cerrar sesión.

---

## Pruebas avanzadas

---

## Vídeo de explicación y de uso

