#### NOTA: El propósito de este repositorio es archivar el proyecto de fin de ciclo realizado de septiembre a diciembre de 2024 tal y como se entregó. En caso de actualizar cualquiera de las versiones estas se almacenarán en un repositorio específico para la misma. A continuación se muestra el documento de presentación original, cambiando solo los enlaces necesarios.

# Proyecto fin de ciclo

## Descripción

El proyecto se trata de una aplicación con 2 versiones (Android y Windows + Linux) para crear tests de forma sencilla y resolverlos. 

Dichos tests se pueden guardar como un fichero xml liviano para compartir con otros usuarios por cualquier medio o subir a una base de datos para que esté disponible para cualquier usuario con acceso a la misma.

Ambas versiones de la aplicación son compatibles entre sí, pudiendo cargar en Android un test creado en Windows/Linux y viceversa.

Se hace especial hincapié en la capacidad de personalizar los tests, procurando proporcionar la mayor libertad posible a los usuarios para que modifiquen diversos parámetros tanto a nivel de pregunta individual, como el tipo de respuesta (única o múltiple) y el número de respuestas; y a nivel general del test, la puntuación sumada y restada por cada respuesta correcta, incorrecta y parcialmente correcta, la puntuación mínima de aprobado o el tiempo máximo para realizar el test (pudiendo ser ilimitado).
Con esto se pretende que el usuario pueda crear un test completamente a su medida.

## Instalación / Puesta en marcha

### Aplicación
La versión de Android solamente requiere instalar [la apk](https://github.com/giladamuzfranciscojavier/test-app/releases/download/v1.0.0/TestAppAndroid.apk).

[La versión de Java](https://github.com/giladamuzfranciscojavier/test-app/releases/download/v1.0.0/TestAppPC.jar) es completamente portable, aunque es preciso tener en cuenta que el fichero de ajustes se guarda en el directorio de trabajo (en la inmensa mayoría de casos coincide con el directorio del jar). La versión actual está compilada para la última versión de java hasta la fecha (23.0.1).

### Base de datos
Suponiendo que exista una instalación correctamente configurada de MySQL Server en un sistema Windows, esté incluída en la variable PATH y se disponga de permisos de root, para la base de datos solo es necesario ejecutar [el script proporcionado](src/Base%20de%20Datos/) en el mismo directorio que el script SQL e introducir la información solicitada durante el proceso.



## Uso

Ambas versiones tienen dos modos principales: creación y resolución.

El modo creación permite configurar un test personalizado y guardarlo como un documento xml que podrá guardarse de forma local o subir a una base de datos.

El modo resolución genera un test en base a un documento xml generado en el modo creación u obtenido de otro usuario.

También disponen de un panel de opciones, permitiendo cambiar los colores mostrados para respuestas correctas, incorrectas y parcialmente correctas y la tipografía por defecto de la aplicación (solo en la versión de ordenador debido a limitaciones de Android).

Los usuarios podrán indicar una base de datos a la que conectarse con las credenciales de la misma.
Si dicho usuario figura como admin (basado en sus permisos) podrá borrar tests de la base de datos. Cualquier otro usuario (incluído el usuario anónimo creado por defecto) solo podrá acceder a los tests disponibles y subir los suyos.

## Sobre el autor

Desarrollador de aplicaciones multiplataforma especializado en Java y Kotlin y con experiencia en bases de datos relacionales y otras formas de acceso y manejo de datos.

La principal razón para elegir este proyecto fue la falta de aplicaciones dedicadas a los tests que no dependan de servicios web y su uso no se limite a una sola plataforma. 

Además, uno de los pilares fundamentales es proporcionar la mayor libertad de personalización posible a los usuarios, a diferencia de la inmensa mayoría de alternativas que, en el mejor de los casos, se limitan a ofrecer plantillas fijas.

Contacto: franciscojaviergilada.x2@gmail.com


## Índice

1. Anteproyecto
    * 1.1. [Idea](doc/1_idea.md)
2. [Diseño](doc/2_diseño.md)
3. Implementación
    * 3.1 [Aplicaciones](https://github.com/giladamuzfranciscojavier/test-app/releases/tag/v1.0.0)
    * 3.2 [Manual](doc/Manual.pdf)


## Guía de contribución

[Lista de posibles mejoras y nuevas funcionalidades](CONTRIBUTING.MD)
