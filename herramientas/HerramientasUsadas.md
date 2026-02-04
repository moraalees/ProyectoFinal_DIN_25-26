El desarrollo de la aplicacion se basa en torno a `Jetpack Compose`, la herramienta principal para la creación de interfaces mediante funciones y clases. 
Este framework permite descomponer la aplicación en unidades lógicas y visuales independientes denominadas `Composables`. Al estructurar la aplicación de esta manera, se facilita la reutilización de elementos 
como selectores de fecha, tarjetas de ejercicios o rejillas de datos, asegurando que cada componente guarde su propio estado y su representación visual de forma autónoma.

La estética y la interacción se rigen por los estándares de `Material Design 3`, que otorga una biblioteca de componentes predefinidos y personalizables, como los `Card`, `Button` o `TextField`. 
Estos componentes se integran con `Material Icons Extended` para la simbología visual. También está presente `Coil`, un componente especializado en la gestión de recursos de imagen. 
Esta combinación garantiza una interfaz coherente y profesional, donde los componentes gráficos se adaptan dinámicamente a los datos que la lógica de negocio otorga.

La combinación de todas estas herramientas se realiza a través de `Navigation Compose`, herramienta que gestiona el ciclo de vida de los componentes de pantalla y la navegación entre estos. 
El uso de `Compose BOM` asegura la compatibilidad de versiones entre todas las herramientas de componentes. Por último, las capacidades de `Preview`,
funcionalidad que ayudó al desarrollo de ciertos componentes y pantallas del entorno de desarrollo, permiten la verificación técnica de cada componente de forma individual.
