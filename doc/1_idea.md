# Idea

* ### Qué tipo de proxecto vas levar a cabo? Vas realizar únicamente a planificación e deseño ou crearás un entregable?

El proyecto será un prototipo entregable

* ### En que consiste o teu proxecto? Cal é o propósito principal do mesmo?

Aplicación para crear y resolver tests con versiones para Android, Windows y Linux (los dos últimos sistemas compartirán la misma versión, por lo que se referirá a ambos como _ordenador_).

##### NOTA IMPORTANTE: si bien técnicamente cada versión podría considerarse una aplicación independiente, ambas compartirán la misma lógica de negocio y la funcionalidad de ambas será prácticamente idéntica, por lo que a partir de ahora se referirá a ambas como una sola aplicación por economía del lenguaje

* ### A quen vai destinada a aplicación? (Cómo é o contexto social do cliente ou sector da empresa á que está dirixido).

La aplicación estará dirigida a cualquier persona que quiera crear sus propios tests de forma sencilla y personalizada y compartirlos con otros usuarios para que los resuelvan.

A su vez, ofrece a los usuarios la posibilidad de resolver tests de temáticas y características diversas en una sola aplicación, además de poder personalizar ciertas características de la misma como colores y tipografías.

* ### Cal é a necesidade ou necesidades que se pretenden cubrir ou satisfacer? O desenvolvemento deste proxecto, abre unha oportunidade de negocio? É posible comercializalo? Como?

Estudio, preparación para exámenes y ocio (cuestionarios sobre amigos, celebridades, personajes de ficción e incluso tests "de broma" que no tengan sentido pero sean divertidos).

* ### Existen na actualidade aplicacións que tenten dar resposta a esa(s) necesidade(s)? En que medida o conseguen? 

Sí, [esta aplicación](https://play.google.com/store/apps/details?id=appinventor.ai_bulone6868.Test_oposicion_v1_4_copiar&hl=es) disponible en la Play Store de Google, que si bien cuenta con alguna de las características planteadas e incluso algunas que no lo están, como estadísticas, formato de exámen en papel y dictado por voz; carece de muchas de las características a implementar en este proyecto, como una versión para ordenador y mayor profundidad de personalización.

* ### Qué obxectivos ten o teu proxecto? Qué requisitos básicos debe cumprir?


#### Imprescindible

El objetivo principal es desarrollar una aplicación funcional con dos modalidades delimitadas: _modo creación_ y _modo resolución_.

El requisito mínimo imprescindible del _modo creación_ es poder documento XML en base a la entrada del usuario creador y exportarlo a un fichero.

El requisito mínimo imprescindible del _modo resolución_ es leer un documento XML creado y exportado anteriormente en el _modo resolución_ y, en base al mismo, generar un test que resolverá el usuario.

Por supuesto cabe destacar, aunque ya estuviera aclarado, que esto aplica para ambas versiones y, además, deben ser compatibles entre sí (un test creado en ordenador debe poder realizarse en Android, y viceversa.

#### Importante

A continuación se enumeran los requisitos de mayor prioridad, pero que no son necesariamente _vitales_ para el correcto funcionamiento de la aplicación.

El _modo creación_ permitirá personalizar la mayoría de aspectos del test (que evidentemente deberán quedar reflejados en el _modo resolución_), incluyendo, pero no necesariamente limitado a: 

	- Número de preguntas.
    - Número de respuestas de cada pregunta.
    - Tipo de pregunta (respuesta única o múltiple)
    - Cuanto puntúa/resta cada respuesta correcta, incorrecta y parcialmente correcta.
    - Tiempo máximo (o ausencia del mismo).
    - Puntuación de aprobado.

Además de poder exportar e importar ficheros, también será posible guardar/cargar tests de una base de datos. La información de conexión será configurable desde la propia aplicación (no estará incrustada en el código).

Se incluirá un menú de opciones que permita a los usuarios personalizar aspectos de la aplicación como la tipografía empleada y los colores mostrados para determinados aspectos, como las respuestas correctas, incorrectas y parcialmente correctas.

* ### Qué tecnoloxías tes pensado empregar para levalo a cabo?

#### Lenguajes de Programación

La lógica de negocio de la aplicación estará codificada en Java, mientras que el lenguaje empleado para la lógica de presentación variará según la plataforma, siendo también Java para ordenador y Kotlin para Android. 
La razón para elegir Java, además de la familiaridad con el lenguaje, es que es multiplataforma, pudiendo ejecutar el mismo fichero jar ejecutable en sistemas Windows y Linux (aunque se baraja la posibilidad de envolverlo en un fichero exe para Windows, ya que el comportamiento de los ficheros jar ejecutables en windows tiende a ser inconsistente).
La razón para emplear Kotlin es aún más sencilla: es el estándar favorecido por Android. Además, es compatible con Java, por lo que es posible utilizar la misma lógica de negocio.

#### Entornos de Desarrollo

Para desarrollar la lógica de negocio de la aplicación y la lógica de presentación para ordenador se empleará el IDE NetBeans, ya que cuenta con un editor de formularios Java Swing incorporado; mientras que para desarrollar la lógica de presentación para Android se utilizará el IDE Android Studio, ya que se trata de la opción respaldada por Google para el desarrollo para aplicaciones para Android.

#### Documentos XML

Para analizar los documentos XML se utilizará la API DOM para Java, ya que permite la lectura y escritura de documentos, a diferencia del analizador SAX, que solo permite operaciones de lectura.

#### Base de Datos

Si bien la opción más directa sería utilizar eXist, ya que tiene una funcionalidad más que suficiente para una base de datos empleada como repositorio de documentos XML, un SGBD relacional (por ejemplo MySQL) ofrece posibilidades interesantes, como la posibilidad de implementar un sistema básico de moderación y gestión de usuarios integrado en la propia aplicación. 