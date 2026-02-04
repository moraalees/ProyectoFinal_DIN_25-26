## Guías de Ayuda al Usuario

---

### RA6.a Identificación de sistemas de generación de ayudas

Los sistemas de ayuda buscan asistir al usuario de forma clara, fomentando la autonomía y reduciendo la curva de aprendizaje a los más novatos. Se planean implementar en la aplicación:
- Ayudas contextuales en la interfaz, como pequeñas explicaciones y descripciones junto a los botones, menús y secciones, que se mostrarán solo cuando el usuario interactúe con ellos.
- Mensajes de error y validación, unas indicaciones precisas que guían al usuario en caso de introducir datos incorrectos o fuera de rango.

---

### RA6.b Generación de ayudas en formatos habituales

Se plantea generar ayudas en distintos formatos para adaptarse a la diversidad de usuarios y contextos de uso:
- Pop-ups dentro de la aplicación, proporcionando información inmediata y localizada.

Como ayuda ya presente en dicha aplicación y su repositorio, encontramos:
- Archivos `Markdown` que permitan leer e informarse gracias a la documentación en línea.
- Un vídeo explicativo que ayude al usuario a encontrar la parte que no comprende y al ver al administrador resolver su problema pueda proseguir.

---

### RA6.c Generación de ayudas sensibles al contexto

La ayuda se integrará de forma contextual y dinámica dentro de la aplicación para maximizar su utilidad:
- La información sobre cada función aparecerá únicamente cuando el usuario esté en el contexto adecuado, evitando saturar la interfaz con datos innecesarios.

La ayuda que ya está implementada en el programa son:
- Los mensajes de error y advertencias se adaptarán al tipo de acción o dato ingresado, proporcionando instrucciones específicas sobre cómo corregir la situación.

Se prevé implementar sugerencias dinámicas basadas en el historial de uso y progreso del usuario, de forma que la ayuda se personalice y mejore la eficiencia en el entrenamiento. 
Este enfoque asegura que las ayudas no solo sean presentes, sino también pertinentes y efectivas para cada situación concreta.

---

### RA6.d Documentación de la estructura de la información persistente

Se documentará de manera detallada la estructura de los datos que la aplicación almacena para garantizar su correcta gestión y mantenimiento:
- JSON de entrenamientos, con descripciones de cada campo, tipo de datos, formato esperado y ejemplos de uso.
- Preferencias de usuario, con una explicación de cómo se guardan configuraciones locales, como unidades de medida, colores de interfaz, notificaciones, etc.
- Historial y estadísticas, otorgando una descripción de los cálculos, totales y agregaciones utilizados para generar reportes y métricas de rendimiento.

La documentación se organizará de manera clara y profesional, permitiendo que otros desarrolladores puedan comprender y modificar la estructura de los datos sin riesgo de errores.

Casi todo esto ya viene implementado, gracias a la documentación del propio código o las explicaciones de los archivos `Markdown`.

---

### RA6.f Manual técnico de instalación y configuración

Se elaborará un manual técnico completo y profesional, que permitirá a otro desarrollador o administrador instalar, configurar y mantener la aplicación. En este manual se incluirán:
- Requisitos del sistema y dependencias necesarias para ejecutar la app correctamente.
- Pasos detallados para instalar la aplicación desde APK o AAB, incluyendo la firma digital y permisos requeridos (ya explicados en RA7).
- Configuración inicial de la aplicación, incluyendo parámetros de usuario y ajustes predeterminados (ya abordados en RA7).
- Explicación de la estructura de carpetas y archivos relevantes, así como recomendaciones para la gestión de copias de seguridad.

El manual será claro, ordenado y accesible, asegurando que cualquier persona interesada pueda reproducir la instalación y configuración sin dificultades.

---

### RA6.g Confección de tutoriales

Se planifica la creación de tutoriales completos y profesionales destinados a guiar al usuario en la utilización de la aplicación:
- Vídeos explicativos cortos, mostrando funciones clave de la aplicación y procedimientos frecuentes.
- Guías interactivas dentro de la app, con indicadores visuales que orienten al usuario sobre la ubicación de botones y opciones.
- Ejemplos prácticos que permitan al usuario entender cómo aplicar los datos generados para mejorar su rendimiento.

Ya anteriormente mencionado, existe en la documentación un tutorial en el que se puede observar y acudir en caso de no entender una funcionalidad.

Los tutoriales estarán diseñados para ser claros, útiles y accesibles, contribuyendo a que la experiencia de uso sea completa y profesional, independientemente del nivel de conocimientos previos del usuario.
