## Empaquetado de la aplicación

### RA7.a Empaquetado de la aplicación

Para empaquetar la aplicación, se generará un `Android App Bundle` (AAB) en modo release desde Android Studio utilizando `Gradle`. Este paquete será generado de manera que pueda distribuirse en 
Google Play, asegurando que cada dispositivo reciba solo los recursos necesarios y optimizando el tamaño final.

---

### RA7.b Personalización del instalador

Se planifica personalizar el instalador de la aplicación incluyendo un icono propio, nombre identificativo y pantalla de inicio. Además, se configurarán los valores de versión 
(*versionName* y *versionCode*) para que la futura instalación y actualización de la app se gestionen correctamente.

---

### RA7.c Paquete generado desde el entorno de desarrollo

El paquete se generará directamente desde Android Studio, aprovechando las capacidades de `Gradle` para compilar y construir la app de forma automatizada. 
Este proceso se planifica de manera que el resultado sea un paquete funcional y libre de errores.

---

### RA7.d Uso de herramientas externas

Se prevé el uso de herramientas externas como `Gradle` para la construcción, `MPAndroidChart` para visualización de datos y librerías como `iText` o `PDFBox` para generar informes PDF. 
Estas herramientas serán integradas y utilizadas adecuadamente para mejorar la funcionalidad de la aplicación.

---

### RA7.e Firma digital de la aplicación

Se implementará la firma digital de la aplicación mediante un keystore propio generado en Android Studio. Este keystore será utilizado durante el proceso de construcción en modo release, 
garantizando la autenticidad e integridad del paquete y permitiendo futuras actualizaciones en canales oficiales de distribución.

---

### RA7.f Instalación desatendida

La instalación de la aplicación se planifica como desatendida, sin requerir configuración inicial por parte del usuario. Tras instalarse, la app podrá ser utilizada inmediatamente sin necesidad 
de registros previos ni configuraciones complejas.

---

### RA7.g Proceso de desinstalación

Se implementará un proceso de desinstalación que asegure la eliminación de todos los datos locales y que no queden procesos activos. La aplicación seguirá las buenas prácticas del sistema 
operativo Android para garantizar una desinstalación limpia.

---

### RA7.h Canales de distribución

La aplicación será distribuida principalmente a través de Google Play mediante el AAB generado. Adicionalmente, se contempla la instalación directa del paquete APK para pruebas internas o 
distribución privada, permitiendo flexibilidad en los distintos escenarios de uso profesional y pruebas de la aplicación.
