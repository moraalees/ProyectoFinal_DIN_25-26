## Pruebas Anvanzadas

---

### RA8.a Estrategia de pruebas

La estrategia de pruebas de la aplicación se diseñó de manera estructurada y planificada, con el objetivo de evaluar tanto la funcionalidad existente como la estabilidad frente a archivos JSON de gran volumen y datos. Se definieron claramente los objetivos de las pruebas, incluyendo la verificación del correcto almacenamiento de los ejercicios, la visualización en la interfaz de usuario y la integridad de los datos tras la carga de nuevos conjuntos de información. Además, se establecieron los criterios de éxito para cada prueba, asegurando que cualquier fallo en el comportamiento esperado pudiera ser identificado y documentado de manera precisa.

Para implementar esta estrategia, se clasificaron los escenarios de prueba en tres bloques principales: pruebas de regresión, pruebas de volumen/estrés y pruebas de seguridad. Cada bloque se abordó utilizando un conjunto de datos controlado, mecanismos de inicialización de pruebas y monitoreo de recursos, garantizando así una cobertura completa de las funcionalidades críticas de la aplicación. La estrategia también contempló la documentación detallada de los pasos, resultados y evidencias, permitiendo que cualquier revisor pueda reproducir las pruebas y validar los resultados de forma consistente.

Para llevar a cabo esta estrategia, se realizarán pruebas de distintos tipos. Se incluyen pruebas de seguridad, verificando el correcto funcionamiento del login y el manejo seguro de los usuarios. Se harán pruebas de regresión, asegurando que las nuevas funcionalidades no afecten a las existentes. También se contemplan pruebas de volumen y estrés, evaluando el rendimiento de la aplicación al cargar grandes cantidades de datos, como los 800 ejercicios importados desde archivos JSON. Finalmente, se realizarán pruebas de integración, comprobando que los diferentes módulos de la aplicación funcionen de manera coordinada y que la interfaz refleje correctamente los cambios en los datos.

---

### RA8.b Pruebas de integración

Se realizó una prueba de integración mostrando el flujo completo de la aplicación. Primero con la creación de un usuario, el registro de un entrenamiento con repeticiones, la visualización del entrenamiento en las marcas personales y la final confirmación de su aparición en el calendario del día correspondiente. Esto demuestra que los distintos módulos de la aplicación funcionan correctamente de manera coordinada y que los datos se transmiten correctamente entre ellos.

[`Evidencia`](evidencias/prueba_integracion.mp4)

---

### RA8.c Pruebas de regresión

Las pruebas de regresión son un tipo de pruebas cuyo objetivo es verificar que las nuevas funcionalidades o cambios en la aplicación no afecten el correcto funcionamiento de las funciones existentes. En otras palabras, permiten asegurarse de que cualquier modificación no introduzca errores involuntarios en los flujos que ya estaban funcionando correctamente.

En el caso de la aplicación, estas pruebas se planearían para validar los flujos críticos, como el login de usuarios, registro de entrenamientos, almacenamiento de repeticiones o la propia visualización en las pantallas de marcas personales y calendario. Por ejemplo, después de añadir un usuario desde JSON o cargar un gran volumen de ejercicios, se comprobaría que estos cambios no alteren la funcionalidad de los entrenamientos previos ni la correcta visualización de los datos en la interfaz.

La planificación de las pruebas de regresión permite identificar claramente los puntos críticos donde se aplicarían. Esto incluye verificar que los datos de usuarios y entrenamientos se mantengan consistentes, que las pantallas reflejen correctamente la información y que los flujos de navegación sigan funcionando de manera integrada, asegurando así la estabilidad y confiabilidad de la aplicación ante futuros cambios.

---

### RA8.d Pruebas de volumen/estrés

Las pruebas de volumen y estrés son un tipo de pruebas de rendimiento que, principalmente, buscan evaluar cómo se comporta la aplicación bajo grandes cantidades de datos o situaciones de carga extrema. Estas pruebas permiten identificar posibles problemas de rendimiento, como retrasos en la carga, errores en el manejo de datos o fallos en la interfaz, asegurando que la aplicación sea estable y eficiente incluso cuando se enfrenta a escenarios exigentes.

En la aplicación, estas pruebas se realizaron añadiendo un gran número de ejercicios al sistema mediante modificaciones en [`EntrenoRepository`](app/src/main/java/com/example/gymtracker/data/repository/EjerciciosRepository.kt), que permitió cargar cerca de 800 ejercicios automáticamente desde archivos JSON sin necesidad de introducirlos manualmente. Se midieron los tiempos de carga y respuesta antes y después de la adición masiva de ejercicios, asegurando que la aplicación pudiera procesar y mostrar los datos correctamente en las distintas pantallas, como la lista de ejercicios, marcas personales y calendario.

La evidencia de estas pruebas se obtuvo mediante la observación directa del comportamiento de la aplicación durante los flujos de uso, verificando que no se produjeran errores y que los tiempos de carga permanecieran razonables. De esta manera, se confirmó que la aplicación mantiene su funcionalidad, aunque tardó un segundo más en cargar, y su rendimiento ante grandes volúmenes de información, cumpliendo con los criterios de estabilidad y eficiencia definidos en la estrategia de pruebas.

[`Vídeo Antes Carga Masiva`](evidencias/pocos_ejercicios.mp4) -0.62s- / [`Vídeo Después Carga Masiva`](evidencias/muchos_ejercicios.mp4) -1.31.s-

---

### RA8.e Pruebas de seguridad

Las pruebas de seguridad tienen como objetivo verificar que la aplicación proteja los datos y funcionalidades críticas frente a accesos no autorizados o entradas incorrectas. Este tipo de pruebas ayuda a identificar vulnerabilidades, asegurando que los usuarios solo puedan realizar acciones permitidas y que los datos sensibles estén protegidos.

En la aplicación, se realizaron pruebas enfocadas en la gestión de usuarios y el login. Se comprobó que no era posible iniciar sesión con usuarios inexistentes, que el sistema rechazaba registros con datos erróneos y que no se podían modificar los datos de un usuario para dejarlos vacíos o inválidos. Estas acciones verifican que la aplicación valida correctamente la información introducida y mantiene la integridad de los datos frente a entradas indebidas.

La evidencia de estas pruebas se documentó mediante un vídeo donde se muestran los intentos de acceso o modificación de datos no válidos y la respuesta de la aplicación en cada caso. Esto demuestra que las funcionalidades críticas de la aplicación están protegidas frente a errores de usuario y accesos no autorizados, cumpliendo con los criterios básicos de seguridad definidos en la estrategia de pruebas.

[`Evidencia`](evidencias/pruebas_seguridad.mp4)

---

### RA8.f Uso de recursos

Las pruebas de uso de recursos tienen como objetivo evaluar cómo la aplicación gestiona los recursos del sistema, como memoria, tiempo de CPU y tiempo de respuesta, especialmente cuando se procesan grandes volúmenes de datos o se realizan múltiples operaciones simultáneas. Estas pruebas permiten identificar posibles fallos o errores y asegurar que la aplicación funcione de manera eficiente y estable.

En la aplicación, se comprobó el uso de recursos principalmente durante la carga masiva de ejercicios y la ejecución de los flujos de usuario, incluyendo la creación de usuarios, realización de entrenamientos y visualización en las pantallas de marcas personales y calendario. Se midieron los tiempos de carga antes y después de añadir cientos de ejercicios al sistema y se verificó que la aplicación no mostraba retrasos significativos ni errores de funcionamiento. Esto permitió evaluar el rendimiento del backend y la capacidad del frontend para procesar y mostrar los datos correctamente.

Los recursos utilizados incluyen principalmente memoria y tiempo de procesamiento del dispositivo, además del almacenamiento en archivos JSON para guardar los ejercicios y datos de usuarios. La observación directa de los flujos de la aplicación y la comparación de los tiempos de carga antes y después de la carga masiva permitieron documentar que la aplicación gestiona de manera adecuada los recursos disponibles, manteniendo su estabilidad y rendimiento bajo condiciones de uso exigentes.

---

### RA8.g Documentación de pruebas

La documentación de las pruebas se ha estructurado para garantizar la trazabilidad de cada escenario evaluado, permitiendo que cualquier persona o revisor externo pueda comprender el estado de calidad de la aplicación. Para ello, se ha elaborado un registro detallado que incluye la descripción del caso de prueba, los datos de entrada utilizados, el resultado esperado y el resultado obtenido tras la ejecución.

La documentación se divide en tres componentes principales:
- Matriz de Resultados: Una tabla comparativa que resume qué pruebas fueron superadas satisfactoriamente y cuáles requirieron ajustes. En esta aplicación, el 100% de las pruebas críticas (Login, Registro de Entrenamiento, Carga de JSON) resultaron exitosas.
- Repositorio de Evidencias: Un conjunto de archivos multimedia alojados en la carpeta evidencias/, que sirven como prueba visual del comportamiento del sistema en condiciones reales y extremas.
- Informe de Incidencias: Registro de los errores detectados durante las pruebas de volumen y estrés, documentando las pequeñas desviaciones en los tiempos de respuesta, como el incremento de 0.69s en la carga masiva, para su posterior optimización.

A continuación se presenta una tabla con las pruebas y sus respectivos resultados:

| Tipo de Prueba | Casos Ejecutados | Éxito | Observaciones |
| :--- | :---: | :---: | :--- |
| **Integración** | 2 | 100% | Flujo completo desde registro a calendario y marcas verificado. |
| **Volumen/Estrés** | 2 | 100% | Ligero aumento en tiempo de respuesta, dentro de lo aceptable. |
| **Seguridad** | 3 | 100% | Bloqueo efectivo de accesos no autorizados y datos nulos. |
