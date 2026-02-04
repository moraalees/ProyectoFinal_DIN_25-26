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

`GymTracker` posee varias funcionalidades que no se encuentran en la mayoría de aplicaciones convenciales. Estas características fomentan el continuo entrenamiento de los usuarios y les motiva a seguir:

- Calendario: Muestra un calendario en donde se observan los entrenos que el usuario y cuándo se hicieron:
  - La interfaz se basa en un calendario dinámico que utiliza la función `generarDiasCalendario` para construir una cuadrícula mensual completa, incluyendo días de meses adyacentes para mantener la estética de semanas enteras. Mediante el uso de estados de Jetpack Compose como `mesActual` y la función `derivedStateOf`, la pantalla se redibuja automáticamente cada vez que el usuario navega entre meses, permitiendo explorar el historial de actividad de forma fluida y visualmente organizada.
  - Al seleccionar un día específico, la aplicación filtra la lista de entrenamientos cargados para mostrar el detalle de los ejercicios realizados en esa fecha en la parte inferior de la pantalla. Si el entrenamiento ya tiene registros, muestra el peso y las repeticiones reales de cada serie individual. De lo contrario, presenta los objetivos de peso y repeticiones estimados. Esta información se organiza en tarjetas que separan visualmente cada ejercicio para facilitar una lectura rápida del progreso físico.
  - La persistencia de datos se gestiona mediante una arquitectura de tres capas, [`ViewModel`](app/src/main/java/com/example/gymtracker/ui/screens/actual_training/EntrenamientoViewModel.kt), [`Repositorio`](app/src/main/java/com/example/gymtracker/data/repository/EntrenamientosRepository.kt) y [`DataSource`](app/src/main/java/com/example/gymtracker/data/local/json/EntrenamientosRealizadosJsonDataSource.kt). Esta estructura utiliza archivos locales en formato JSON para el almacenamiento. Para garantizar la privacidad y la integridad de la información, cada usuario tiene su propio archivo identificado por su ID único, llamado `entrenamientos_ID.json`. Gracias a la librería Gson, los objetos de la aplicación se convierten automáticamente en texto para almacenarse en la memoria interna del dispositivo, asegurando que los datos sobrevivan incluso si se cierra la aplicación o se apaga el móvil.
  - El flujo de datos comienza con una carga automática al abrir la pantalla, donde el [`ViewModel`](app/src/main/java/com/example/gymtracker/ui/screens/actual_training/EntrenamientoViewModel.kt) recupera la información del repositorio para marcar los días con actividad en el calendario. Cuando el usuario registra o modifica un entrenamiento, el sistema actualiza la lista en memoria y sobrescribe el archivo JSON correspondiente de manera transparente para el usuario. Este ciclo garantiza que la interfaz siempre refleje el estado más reciente de los entrenamientos, manteniendo una sincronización constante entre lo que se ve en pantalla y lo que está guardado en el disco.
  
- Marcas globales: Un usuario puede subir su marca personal y el administrador decidirá si es válida o no para que esté presente en la aplicación:
  - El usuario interactúa con [`PantallaMarcasRecords`](app/src/main/java/com/example/gymtracker/ui/screens/records/PantallaMarcasRecords.kt) y sube un vídeo. [`RecordsViewModel`](app/src/main/java/com/example/gymtracker/ui/screens/records/RecordsViewModel.kt) invoca el método `attemptSubmit`, que delega en [`RecordsRepository`](app/src/main/java/com/example/gymtracker/data/repository/RecordsRepository.kt) la creación de una solicitud. En esta fase, el [`RecordsLocalesDataSource`](app/src/main/java/com/example/gymtracker/data/local/json/RecordsLocalesDataSource.kt) realiza dos tareas críticas, como guardar físicamente el archivo .mp4 en una carpeta temporal llamada `requests_videos` y actualiza el archivo `requests.json` con los datos de la marca, dejando la solicitud en estado pendiente.
  - Cuando el administrador accede a [`PantallaManejoMarcas`](app/src/main/java/com/example/gymtracker/ui/screens/admin/PantallaManejoMarcas.kt), donde [`SolicitudesRecordViewModel`](app/src/main/java/com/example/gymtracker/ui/screens/records/SolicitudesRecordViewModel.kt) recupera esta lista de espera. Si el administrador decide aprobar la marca, se ejecuta el método `adminAcceptRequest` en el repositorio. Allí, el vídeo es promocionado de la carpeta temporal a la carpeta permanente de vídeos mediante el método `moveRequestVideoToVideos`, y se actualiza el ranking oficial en el archivo `records.json`. Este proceso es totalmente transparente para el usuario, quien solo verá su marca publicada una vez que el administrador haya verificado su solicitud.
  - La lógica de competición se gestiona mediante un filtrado en donde solo aparecen las 3 mejores marcas registradas y aceptadas por el administrador. Al aceptar un nuevo récord, el repositorio utiliza un comparador personalizado llamado `compareSubmissions` que ordena la lista priorizando primero el peso y, en caso de empate, las repeticiones. Si la nueva marca entra entre las 3 mejores, el sistema añade la entrada y expulsa automáticamente al cuarto lugar. Para optimizar el almacenamiento del dispositivo, el repositorio invoca a `deleteVideo` para borrar el archivo multimedia de cualquier marca que haya sido desplazada fuera del podio, manteniendo así el sistema limpio y eficiente.
  - Finalmente, la robustez del sistema está presente en su arquitectura de tres capas. El ViewModel gestiona el estado de la UI y los hilos de ejecución, gracias a `Dispatchers.IO`. El Repository aplica las reglas de negocio y comparaciones. Por último, el DataSource se encarga de la persistencia real usando la librería Gson para serializar los objetos a JSON. Esta estructura desacoplada permite que, aunque el usuario cierre la aplicación durante una carga, los archivos internos permanezcan íntegros y listos para ser recuperados en la próxima sesión.

- Rutina autogenerada: Dependiendo de los datos físicos del usuario, le generará una rutina amoldada a su perfil:
  - En [`FormularioViewModel`](app/src/main/java/com/example/gymtracker/ui/screens/form/FormularioViewModel.kt), donde, tras recoger los datos físicos y objetivos del usuario, se crea un perfil de `UsuarioGimnasio`. Al guardar este perfil, el sistema activa automáticamente el método `asignarRutina` del repositorio, asegurando que ningún usuario nuevo se quede sin un plan de entrenamiento adaptado desde el primer segundo.
  - [`EntrenoRepository`](app/src/main/java/com/example/gymtracker/data/repository/EntrenoRepository.kt) actúa como un motor de decisiones inteligente. Este componente utiliza la función `generarPlanSemanal` para consultar un catálogo de plantillas predefinidas que varían según el objetivo y el nivel de experiencia que el usuario haya ingresado. Una vez seleccionada la plantilla adecuada, el repositorio calcula los parámetros de entrenamiento, como las series y repeticiones, gracias a la función `calcularParametros`, que ajustará el volumen de trabajo para que este no sea tan intenso pero tampoco ligero.
  - Lo que realmente hace que el plan sea único para cada persona es el método `estimarPeso`. En lugar de asignar cargas aleatorias, el sistema realiza un cálculo basado en el peso corporal del usuario y el tipo de equipamiento. Por ejemplo, si un usuario principiante realiza un ejercicio con barra, el sistema le asignará un peso estimado del 25% de su peso corporal, mientras que para un usuario avanzado con mancuernas, el cálculo subirá al 40%. Esta lógica permite que la rutina crezca dependiendo de la complexión física del usuario.
  - Finalmente, una vez que el plan semanal está construido con sus ejercicios, series, repeticiones y pesos sugeridos, se guarda en el perfil del usuario y se marca como `rutinaActiva` por defecto. Todo este conjunto de datos se persiste en el archivo `usuarios_gimnasio.json` a través del [`UsuarioGimnasioJsonDataSource`](app/src/main/java/com/example/gymtracker/data/local/json/UsuarioGimnasioJsonDataSource.kt). De este modo, la próxima vez que el usuario abra la aplicación, su entrenamiento personalizado estará listo en la pantalla principal sin que haya tenido que configurar nada manualmente.

---

## Justificación de Diseño y Usabilidad

El diseño de la aplicación y sus vistas se ha centrado en ofrecer una experiencia clara, sencilla y coherente para cualquier persona, priorizando a aquellas que no tienen conocimientos previos sobre el mundo del fitness. La estructura por pantallas independientes permite acceder de forma rápida a cada funcionalidad de la aplicación, reduciendo la confusión y evitando recorridos innecesarios.

La navegación se ha unificado mediante barras superiores e inferiores, facilitando el acceso constante a las secciones principales de la aplicación. Como es coherente, en algunas pantallas solo se puede dar marcha atrás, no navegar a una totalemente diferente, como por ejemplo en el caso de añadir ejercicios a un día específico de una rutina. Además, se ha optado por una generación automática de rutinas en función del perfil del usuario, con el fin de denegar totalmente la toma de decisiones inicial con desconocimiento y evitar la desmotivación al comenzar a usar la aplicación.

El uso de componentes reutilizables permite mantener coherencia visual en toda la interfaz, reforzando la estructura de la aplicación y mejorando la comprensión de las acciones disponibles en cada pantalla.

Además, la información dentro de la aplicación se presenta utilizando distintos formatos visuales según su finalidad, con el objetivo de mejorar la comprensión y la navegación. Los ejercicios, rutinas y marcas se muestran tanto en disposición en fila como en rejilla, dependiendo del tipo de contenido y del contexto de uso.

Las vistas en fila se utilizan cuando es necesario mostrar información detallada y fácilmente escaneable, como nombres, datos numéricos o estados del contenido. Por otro lado, las vistas en rejilla permiten una visualización más compacta y visual, facilitando la exploración de múltiples elementos de forma rápida e intuitiva.

Esta combinación de formatos mejora la experiencia de usuario, ya que permite acceder a la información de manera clara, ordenada y adaptada al tipo de interacción, reduciendo el esfuerzo visual y agilizando la toma de decisiones dentro de la aplicación.

### Consideraciones de usabilidad

Se han aplicado principios básicos de usabilidad para garantizar una experiencia fluida y accesible:
- Interfaces simples y limpias, evitando la sobrecarga de información.
- Acciones claras mediante botones visibles y textos descriptivos.
- Reducción de pasos en tareas frecuentes, como iniciar un entrenamiento o consultar rutinas.
- Visualización de mensajes de error claros cuando se introducen datos incorrectos, ayudando al usuario a corregirlos de forma inmediata y evitando acciones inválidas.

Además, la aplicación permite al usuario visualizar su progreso mediante marcas personales y entrenamientos registrados, lo que refuerza la motivación y fomenta la constancia.

### Consideraciones de accesibilidad

Sobre la accesibilidad, se han tenido en cuenta distintos aspectos para facilitar el uso de la aplicación a un mayor número de usuarios:
- Uso de colores con buen contraste para mejorar la legibilidad y la diferenciación de elementos en pantalla.
- Tamaños de texto adecuados y consistentes en todas las vistas de la aplicación.
- Estructura clara de la información, favoreciendo la comprensión visual y la localización rápida de los elementos.
- Notificaciones que ayudan al usuario a seguir los tiempos de descanso durante el entrenamiento.

Además, se ha definido una paleta de colores coherente y funcional. El color principal de la aplicación es un azul oscuro, elegido por su buena combinación tanto con fondos claros como oscuros, especialmente con el negro y el blanco, lo que garantiza un contraste adecuado y reduce la fatiga visual durante el uso prolongado de la aplicación.

Para la gestión de errores, se ha utilizado un color diferenciado y fácilmente reconocible, permitiendo al usuario identificar de forma inmediata los campos o acciones incorrectas. Esta diferenciación cromática ayuda a mejorar la accesibilidad visual y evita confusiones, facilitando la corrección de errores sin necesidad de explicaciones adicionales.

Estas decisiones permiten que la aplicación sea comprensible y usable para usuarios con distintos niveles de experiencia, garantizando una experiencia inclusiva, clara y adaptable.

---

## Herramientas y librerías

### Herramientas NUI

- Acerca de las herramientas de componentes usadas: [`Herramientas Usadas`](herramientas/HerramientasUsadas.md)
- Acerca del `RA2.a Herramientas NUI`: [`Herramientas NUI`](herramientas/HerramientasNUI.md)
- Acerca del `RA2.b Diseño conceptual NUI`: [`Diseño Conceptual NUI`](herramientas/DiseñoConceptualNUI.md)
- Acerca del `RA2.c Interacción por voz`: [`Interacción por voz`](herramientas/InteracciónVoz.md)
- Acerca del `RA2.d Interacción por gesto`: [`Interacción por gesto`](herramientas/InteracciónGesto.md)
- Acerca del `RA2.e Detección facial/corporal`: [`Detección facial o corporal`](herramientas/DetecciónCorporal.md)
- Acerca del `RA2.f Realidad aumentada`: [`Realidad Aumentada`](herramientas/RealidadAumentada.md)

### Informes

En una aplicación de seguimiento deportivo, como lo es `GymTracker`, los datos por sí solos tienen un valor limitado. La verdadera utilidad para el usuario surge cuando esos datos se transforman en informes de progreso.

Los informes son fundamentales porque permiten:
- Visualizar la evolución, identificando tendencias de mejora a largo plazo que no se ven en el día a día.
- La toma de decisiones, porque ayuda al usuario a saber cuándo subir de peso o cuándo cambiar de rutina basándose en datos reales.
- Motivación y retención, al ver gráficas de crecimiento refuerza el compromiso del usuario con la aplicación y con sus objetivos personales.

[`Herramientas para generar informes`](herramientas/HerramientasInformes.md)

Aquí se nombrarán herramientas como JetPack compose, NUI, informes... Sistemas de generación de ayudas.

---

## Empaquetado de la aplicación

---

## Manual de usuario de la aplicación

Crear un manual de usuario es clave para que cualquier persona pueda entender todas las partes de la aplicación.

[`Manual`](ManualUsuario.md)

---

## Pruebas avanzadas

---

## Vídeo de explicación y de uso

