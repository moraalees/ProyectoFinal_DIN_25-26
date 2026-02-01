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
- [`Directorio UI`](app/src/main/java/com/example/gymtracker/ui):

---

## Características

