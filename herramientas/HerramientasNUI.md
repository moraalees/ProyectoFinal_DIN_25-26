## Herramientas NUI

El desarrollo de `Interfaces Naturales de Usuario (NUI)` requiere de un conjunto de herramientas de software especializadas que permitan la transición de 
una interacción basada en comandos y código explícitos a una basada en movimientos humanos intuitivos. En el entorno Android, estas herramientas actúan como 
capas de abstracción que procesan datos de los sensores para transformarlos en eventos de control. Algunas herramientas NUI son:

---

### 1. Google ML Kit y MediaPipe: Motores de Visión e Inferencia
La implementación de NUI basadas en visión, como gestos o detección facial y corporal, se sustenta en `Google ML Kit y MediaPipe`. `ML Kit` ofrece una
API de alto nivel para tareas de inferencia en el dispositivo, lo que es llamado `Edge Computing`, garantizando la privacidad del usuario. Por otra parte, 
`MediaPipe` destaca como un framework de código abierto capaz de gestionar pipelines de procesamiento multimodal. Estas dos herramientas son críticas para 
la justificación técnica, ya que permiten la segmentación de imágenes y el seguimiento de puntos clave en tiempo real sin requerir una conexión constante a la 
nube, optimizando el consumo de batería y la velocidad de respuesta.

---

### 2. ARCore: Sensorización Espacial y Contextual
Para la interacción en espacios tridimensionales, la herramienta más conocida es `ARCore`. Esta SDK permite que el sistema comprenda el entorno mediante
tres capacidades fundamentales. Primero, el seguimiento del movimiento, `COM`, que se compone por la detección de planos y superficies y la estimación de la luz.
La justificación de su uso como herramienta NUI reside en su capacidad para anclar objetos digitales en el mundo físico, permitiendo que la interacción no
se limite solo a la superficie de la pantalla, sino que se extienda a las longitudes mundo real.

---

### 3. Android Sensor API: Abstracción del Hardware
`Android Sensor API` constituye la herramienta fundamental para las NUI basadas en el movimiento del dispositivo. Esto se consigue gracias a sensores 
como el acelerómetro o el giroscopio. Usando estos sensores la API permite detectar gestos complejos, como sacudir o inclinar el dispositivo.
Su integración es vital para la creación de interfaces que respondan al movimiento del usuario.

---

### 4. Speech Recognizer y Text-to-Speech (TTS)
Para la interacción por voz, se emplean las herramientas de `Speech-to-Text (STT)` y TTS integradas en los `Google Play Services`.
Estas, utilizan modelos de procesamiento de lenguaje natural (NLP) para convertir cualquier onda sonora en cadenas de texto procesables por la lógica
de la aplicación. Estas herramientas se basan en su capacidad de adaptación a diferentes idiomas y dialectos, permitiendo una interacción inclusiva y eficiente.
