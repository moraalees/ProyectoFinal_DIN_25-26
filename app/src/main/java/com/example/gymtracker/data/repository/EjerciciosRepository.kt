package com.example.gymtracker.data.repository

import android.content.Context
import com.example.gymtracker.model.EjercicioBase
import com.example.gymtracker.model.enum_classes.Musculo
import com.example.gymtracker.model.enum_classes.TipoPeso
import com.example.gymtracker.R
import com.example.gymtracker.data.local.json.EjerciciosJsonDataSource
import com.google.gson.Gson

/**
 * Repositorio de ejercicios que gestiona la lista de ejercicios por defecto y los guardados por usuario.
 *
 * Este objeto permite:
 * - Inicializar los ejercicios de un usuario, cargando los guardados o creando la lista por defecto.
 * - Obtener todos los ejercicios disponibles para un usuario.
 * - Obtener la lista de ejercicios iniciales (principales) sin modificaciones.
 * - Añadir un nuevo ejercicio y actualizar los datos guardados.
 *
 * Funciones principales:
 * - [inicializar]: Inicializa la lista de ejercicios de un usuario, cargando los guardados o usando los predeterminados.
 * - [obtenerTodosLosEjercicios]: Devuelve todos los ejercicios del usuario, incluidos los añadidos manualmente.
 * - [obtenerEjerciciosPrincipales]: Devuelve la lista de ejercicios iniciales por defecto.
 * - [anadirEjercicio]: Añade un ejercicio a la lista del usuario y lo guarda en el JSON correspondiente.
 */
object EjerciciosRepository {

    // private const val MODO_PRUEBAS = true - Estó se usó para hacer pruebas

    private val ejerciciosIniciales = listOf(
        EjercicioBase(1, "Flexiones", "Baja el cuerpo en línea recta doblando los codos a 45 grados hasta que el pecho roce el suelo y luego empuja hacia arriba manteniendo el abdomen firme.",
            TipoPeso.PROPIO_PESO, listOf(Musculo.PECHO_SUPERIOR, Musculo.TRICEPS_CABEZA_LATERAL),
            R.drawable.flexiones),
        EjercicioBase(2, "Dominadas", "Cuelga de una barra con las manos algo más abiertas que los hombros, contrae el abdomen y sube el cuerpo llevando el pecho hacia la barra hasta que tu barbilla la supere, luego baja de forma controlada.",
            TipoPeso.PROPIO_PESO, listOf(Musculo.DORSAL_ANCHO, Musculo.BICEPS_CABEZA_LARGA),
            R.drawable.dominadas),
        EjercicioBase(3, "Burpees", "Desde una posición de pie, baja a una sentadilla para apoyar las manos en el suelo, lanza los pies hacia atrás para quedar en plancha, realiza una flexión, vuelve de un salto a la posición de sentadilla y finaliza con un salto explosivo hacia arriba extendiendo los brazos.",
            TipoPeso.PROPIO_PESO, listOf(Musculo.CUADRICEPS, Musculo.PECHO_SUPERIOR),
            R.drawable.burpees),
        EjercicioBase(4, "Press Banca", "Acuéstate en el banco con los pies apoyados en el suelo, baja la barra de forma controlada hasta la parte media del pecho manteniendo los codos en un ángulo de 45 grados y empújala hacia arriba con fuerza hasta extender los brazos completamente.",
            TipoPeso.BARRA, listOf(Musculo.PECHO_SUPERIOR, Musculo.TRICEPS_CABEZA_LATERAL),
            R.drawable.press_banca),
        EjercicioBase(5, "Press Plano", "Acuéstate en el banco y sostén las mancuernas sobre el pecho con los brazos extendidos; bájalas de forma controlada hacia los costados hasta que los codos superen ligeramente la línea del torso y luego empújalas de nuevo hacia arriba hasta que se encuentren al centro.",
            TipoPeso.MANCUERNAS, listOf(Musculo.PECHO_SUPERIOR, Musculo.TRICEPS_CABEZA_LATERAL),
            R.drawable.press_plano),
        EjercicioBase(6, "Press Inclinado", "Recuéstate en un banco inclinado entre 30 y 45 grados, baja la barra de forma controlada hasta la parte superior del pecho y empújala verticalmente hacia arriba hasta extender los brazos, manteniendo siempre los codos ligeramente hacia adentro para proteger los hombros.",
            TipoPeso.BARRA, listOf(Musculo.PECHO_SUPERIOR, Musculo.DELTOIDES_ANTERIOR),
            R.drawable.press_inclinado_barra),
        EjercicioBase(7, "Press Inclinado", "Recuéstate en un banco inclinado entre 30 y 45 grados y baja las mancuernas de forma controlada hacia los laterales de la parte superior del pecho, luego empújalas hacia arriba en línea recta hasta que casi se toquen, manteniendo un ángulo de 45 grados en los codos.",
            TipoPeso.MANCUERNAS, listOf(Musculo.PECHO_SUPERIOR, Musculo.DELTOIDES_ANTERIOR),
            R.drawable.press_inclinado_mancuernas),
        EjercicioBase(8, "Apertura en Polea", "Colócate en el centro de las poleas con los cables a la altura del pecho, sujeta los agarres con los codos ligeramente flexionados y junta las manos frente a ti mediante un movimiento circular, apretando el pectoral al final antes de regresar lentamente a la posición inicial.",
            TipoPeso.POLEA, listOf(Musculo.PECHO_SUPERIOR, Musculo.PECHO_INFERIOR),
            R.drawable.apertura_polea),
        EjercicioBase(9, "Pec Deck", "Siéntate con la espalda bien apoyada, sujeta los agarres manteniendo una ligera flexión en los codos y junta las manos frente a ti mediante un movimiento circular, apretando el pecho al centro antes de regresar lentamente a la posición inicial sin perder la tensión.",
            TipoPeso.MAQUINA, listOf(Musculo.PECHO_SUPERIOR, Musculo.PECHO_INFERIOR),
            R.drawable.pec_deck),
        EjercicioBase(10, "Apertura con Mancuernas", "Acuéstate en un banco plano y baja las mancuernas hacia los lados con los codos ligeramente flexionados, dibujando un arco amplio hasta que sientas el estiramiento en el pecho, y luego vuelve a subirlas como si estuvieras dando un abrazo.",
            TipoPeso.MANCUERNAS, listOf(Musculo.PECHO_SUPERIOR, Musculo.PECHO_INFERIOR),
            R.drawable.apertura_mancuernas),
        EjercicioBase(11, "Chest Press", "Siéntate con la espalda apoyada y los pies firmes, sujeta los agarres a la altura del pecho y empuja hacia adelante extendiendo los brazos por completo, luego regresa de forma lenta y controlada evitando que las pesas choquen para mantener la tensión.",
            TipoPeso.MAQUINA, listOf(Musculo.PECHO_SUPERIOR, Musculo.TRICEPS_CABEZA_LATERAL),
            R.drawable.press_pecho_maquina),
        EjercicioBase(12, "Press Inclinado en Máquina/Smith", "Coloca el banco inclinado bajo la barra, acuéstate y empuja hacia arriba para desbloquear los seguros; baja la barra de forma controlada hasta la parte superior del pecho y vuelve a subir con fuerza manteniendo los codos en un ángulo de 45 grados.",
            TipoPeso.MAQUINA, listOf(Musculo.PECHO_SUPERIOR, Musculo.DELTOIDES_ANTERIOR),
            R.drawable.press_banca_inclinado_smith),
        EjercicioBase(13, "Fondos", "Sujétate de las barras paralelas con los brazos extendidos, inclina el torso ligeramente hacia adelante y baja el cuerpo doblando los codos hasta que formen un ángulo de 90 grados, luego empuja con fuerza hacia arriba hasta volver a la posición inicial.",
            TipoPeso.PROPIO_PESO, listOf(Musculo.PECHO_INFERIOR, Musculo.TRICEPS_CABEZA_LARGA),
            R.drawable.fondos),
        EjercicioBase(14, "Press Francés", "Acuéstate en un banco y sostén la barra Z sobre tu frente con los brazos extendidos; baja la barra doblando solo los codos hacia tu frente de forma controlada y luego vuelve a extender los brazos hacia arriba manteniendo los codos cerrados y fijos.",
            TipoPeso.BARRA, listOf(Musculo.TRICEPS_CABEZA_LARGA, Musculo.TRICEPS_CABEZA_LATERAL),
            R.drawable.press_frances),
        EjercicioBase(15, "Extensión de tríceps en Polea", "Sujeta la barra con las palmas hacia abajo, mantén los codos pegados a los costados y empuja el peso hacia el suelo extendiendo los brazos por completo hasta que tus manos lleguen a los muslos,",
            TipoPeso.POLEA, listOf(Musculo.TRICEPS_CABEZA_MEDIAL, Musculo.TRICEPS_CABEZA_LATERAL),
            R.drawable.extension_triceps_polea),
        EjercicioBase(16, "Extensión de tríceps Unilateral en Polea", "Sujeta el agarre con una sola mano y mantén el codo pegado al costado; extiende el brazo hacia el suelo hasta estirarlo por completo y regresa de forma lenta, manteniendo el cuerpo firme para evitar balanceos.",
            TipoPeso.POLEA,listOf(Musculo.TRICEPS_CABEZA_MEDIAL, Musculo.TRICEPS_CABEZA_LATERAL),
            R.drawable.extension_triceps_unilateral_polea),
        EjercicioBase(17, "Overhead Triceps Extension", "Sujeta la polea baja, dale la espalda y extiende el brazo hacia arriba, manteniendo el",
            TipoPeso.POLEA, listOf(Musculo.TRICEPS_CABEZA_LARGA, Musculo.TRICEPS_CABEZA_LATERAL),
            R.drawable.overhead_triceps_polea),
        EjercicioBase(18, "Overhead Triceps Extension", "Sostén una mancuerna con ambas manos por encima de la cabeza y bájala por detrás de la nuca doblando los codos de forma controlada, luego extiende los brazos hacia arriba hasta estirarlos por completo manteniendo los codos cerrados y cerca de las orejas.",
            TipoPeso.MANCUERNAS, listOf(Musculo.TRICEPS_CABEZA_LARGA, Musculo.TRICEPS_CABEZA_LATERAL),
            R.drawable.overhead_triceps_mancuerna),
        EjercicioBase(19, "Máquina de Fondos", "Siéntate en la máquina, sujeta los agarres laterales y empuja hacia abajo hasta extender los brazos por completo, manteniendo el pecho erguido y los hombros hacia atrás para aislar el tríceps.",
            TipoPeso.MAQUINA, listOf(Musculo.PECHO_INFERIOR, Musculo.TRICEPS_CABEZA_LARGA),
            R.drawable.fondos_maquina),
        EjercicioBase(20, "Curl de Bíceps", "Ponte de pie con los pies a la anchura de los hombros, sujeta la barra o mancuernas con las palmas hacia adelante y sube el peso hacia los hombros flexionando los codos, manteniendo los brazos pegados al cuerpo y sin balancear el torso.",
            TipoPeso.MANCUERNAS, listOf(Musculo.BICEPS_CABEZA_LARGA, Musculo.BICEPS_CABEZA_CORTA),
            R.drawable.curl_biceps),
        EjercicioBase(21, "Curl de Bíceps Barra Z", "Sujeta la barra Z, pega los codos a los costados y sube el peso flexionando los brazos sin balancearte. Baja lento y estira por completo.",
            TipoPeso.BARRA, listOf(Musculo.BICEPS_CABEZA_LARGA, Musculo.BICEPS_CABEZA_CORTA),
            R.drawable.curl_biceps_barra_z),
        EjercicioBase(22, "Curl Scott", "Apoya los brazos en el cojín, sujeta los agarres y sube el peso hacia tus hombros apretando el bíceps. Baja lento hasta estirar casi por completo sin bloquear los codos.",
            TipoPeso.MAQUINA,listOf(Musculo.BICEPS_CABEZA_CORTA),
            R.drawable.scott_maquina),
        EjercicioBase(23, "Curl Scott Barra Z", "Apoya los brazos en el banco, sujeta la barra Z por el agarre interno y sube el peso hacia tu cara. Baja de forma controlada hasta estirar casi todo el brazo.",
            TipoPeso.BARRA, listOf(Musculo.BICEPS_CABEZA_LARGA),
            R.drawable.curl_scott_barra),
        EjercicioBase(24, "Curl Martillo", "Sujeta las mancuernas con las palmas enfrentadas (agarre neutro). Sube el peso manteniendo los codos fijos y baja lento hasta estirar el brazo.",
            TipoPeso.MANCUERNAS, listOf(Musculo.BICEPS_CABEZA_LARGA, Musculo.BRAQUIORRADIAL),
            R.drawable.curl_martillo_mancuernas),
        EjercicioBase(25, "Curl Martillo", "Sujeta la barra romana (la que tiene agarres transversales) por las asas paralelas. Sube el peso manteniendo las palmas enfrentadas y los codos fijos, luego baja lento.",
            TipoPeso.BARRA,listOf(Musculo.BICEPS_CABEZA_LARGA, Musculo.BRAQUIORRADIAL),
            R.drawable.curl_martillo_barra),
        EjercicioBase(26, "Curl de Bíceps Inclinado", "Siéntate en un banco inclinado con 45º, deja colgar los brazos con las palmas al frente y sube las mancuernas sin despegar la espalda del respaldo. Baja lento sintiendo el estiramiento.",
            TipoPeso.MANCUERNAS,listOf(Musculo.BICEPS_CABEZA_LARGA),
            R.drawable.biceps_inclinado),
        EjercicioBase(27, "Curl de Bíceps en Polea Baja", "Frente a la polea, sujeta la barra con las palmas hacia arriba. Mantén los codos pegados al cuerpo y sube el peso hacia el pecho. Baja lento manteniendo la tensión constante.",
            TipoPeso.POLEA,listOf(Musculo.BICEPS_CABEZA_LARGA, Musculo.BICEPS_CABEZA_CORTA),
            R.drawable.polea_biceps),
        EjercicioBase(28, "Elevaciones Laterales", "Sujeta las mancuernas a los costados, eleva los brazos hacia afuera hasta la altura de los hombros con una ligera flexión en los codos y baja lento.",
            TipoPeso.MANCUERNAS,listOf(Musculo.DELTOIDES_LATERAL, Musculo.DELTOIDES_ANTERIOR),
            R.drawable.elevaciones_laterales),
        EjercicioBase(29, "Elevaciones Laterales en Polea (Anterior)", "Ponte al lado de la polea y coge por delante de tu cuerpo el cable con una mano. Eleva el brazo hacia afuera hasta la altura del hombro. Baja lento manteniendo la tensión.",
            TipoPeso.POLEA,listOf(Musculo.DELTOIDES_LATERAL, Musculo.DELTOIDES_ANTERIOR),
            R.drawable.elevacion_polea_anterior),
        EjercicioBase(30, "Elevaciones Laterales en Polea (Posterior)", "Ponte al lado de la polea y coge por detrás de tu cuerpo el cable con una mano. Eleva el brazo hacia afuera hasta la altura del hombro. Baja lento manteniendo la tensión.",
            TipoPeso.POLEA,listOf(Musculo.DELTOIDES_LATERAL, Musculo.DELTOIDES_POSTERIOR),
            R.drawable.elevacion_polea_posterior),
        EjercicioBase(31, "Press Militar", "Siéntate en un banco inclinado 75º y sujeta las mancuernas a la altura de los hombros y empújalas hacia el techo hasta estirar los brazos. Baja lento hasta que las pesas rocen tus hombros.",
            TipoPeso.MANCUERNAS,listOf(Musculo.DELTOIDES_ANTERIOR, Musculo.TRICEPS_CABEZA_LARGA),
            R.drawable.press_militar_mancuernas),
        EjercicioBase(32, "Press Militar", "Siéntate en la máquina con la espalda apoyada, sujeta los agarres a la altura de las orejas y empuja hacia arriba hasta extender los brazos. Baja de forma controlada hasta que tus manos queden alineadas con tu mandíbula.",
            TipoPeso.MAQUINA,listOf(Musculo.DELTOIDES_ANTERIOR, Musculo.TRICEPS_CABEZA_LARGA),
            R.drawable.press_militar_maquina),
        EjercicioBase(33, "Elevaciones Frontales", "Sujeta las mancuernas frente a los muslos, eleva los brazos hacia adelante hasta la altura de los ojos sin balancear el cuerpo y baja de forma controlada.",
            TipoPeso.MANCUERNAS,listOf(Musculo.DELTOIDES_ANTERIOR),
            R.drawable.elevacion_frontal),
        EjercicioBase(34, "Face Pull", "Sujeta la cuerda de la polea alta con ambas manos, da un paso atrás y tira hacia tu frente abriendo los codos hacia afuera. Aprieta la parte trasera del hombro y baja lento.",
            TipoPeso.POLEA,listOf(Musculo.DELTOIDES_POSTERIOR),
            R.drawable.face_pull),
        EjercicioBase(35, "Remo", "Siéntate con la espalda recta y apoya el pecho en el pad. Sujeta los agarres, tira de ellos hacia tu torso llevando los codos hacia atrás y aprieta las escápulas. Regresa lento sin soltar el peso de golpe.",
            TipoPeso.MAQUINA,listOf(Musculo.DELTOIDES_POSTERIOR, Musculo.TRAPECIO),
            R.drawable.remo_maquina),
        EjercicioBase(36, "Reverse Fly", "Inclínate hacia adelante con la espalda recta, casi paralela al suelo. Sujeta las mancuernas colgando y elévalas hacia los lados como si fueran alas, apretando la parte trasera del hombro. Baja lento.",
            TipoPeso.MANCUERNAS,listOf(Musculo.DELTOIDES_POSTERIOR),
            R.drawable.reverse_fly_mancuernas),
        EjercicioBase(37, "Remo en T", "Apoya el pecho en el pad, sujeta los agarres y tira de la barra hacia tu torso llevando los codos hacia atrás. Aprieta la espalda al final y baja lento de forma controlada.",
            TipoPeso.MAQUINA,listOf(Musculo.DORSAL_ANCHO, Musculo.DELTOIDES_POSTERIOR),
            R.drawable.remo_t_maquina),
        EjercicioBase(38, "Remo en T", "Coloca un extremo de la barra en una esquina o soporte (landmine). Sujeta el otro extremo con un agarre en V debajo de los discos, inclina el torso con la espalda recta y tira de la barra hacia tu abdomen. Baja lento extendiendo los brazos.",
            TipoPeso.BARRA,listOf(Musculo.DORSAL_ANCHO, Musculo.DELTOIDES_POSTERIOR),
            R.drawable.remo_t_barra),
        EjercicioBase(39, "Remo Unilateral", "Siéntate de lado o de frente según la máquina, sujeta un solo agarre y tira hacia tu cadera manteniendo el torso estable. Aprieta el dorsal al final del movimiento y regresa estirando el brazo por completo de forma controlada.",
            TipoPeso.MAQUINA,listOf(Musculo.DORSAL_ANCHO, Musculo.DELTOIDES_POSTERIOR),
            R.drawable.remo_unilateral_maquina),
        EjercicioBase(40, "Remo con Mancuernas", "Inclina el torso hacia adelante con la espalda recta (casi paralela al suelo) y las rodillas ligeramente flexionadas. Sujeta una mancuerna en cada mano con los brazos colgando y tira de ambas simultáneamente hacia tus caderas, llevando los codos hacia atrás. Baja lento extendiendo los brazos por completo.",
            TipoPeso.MANCUERNAS,listOf(Musculo.DORSAL_ANCHO, Musculo.DELTOIDES_POSTERIOR),
            R.drawable.remo_mancuernas),
        EjercicioBase(41, "Remo Gironda Agarre Neutro", "Siéntate en la máquina de remo bajo con los pies apoyados y las rodillas ligeramente flexionadas. Sujeta el manillar en V (agarre neutro), mantén la espalda recta y tira del agarre hacia la parte baja de tu abdomen, llevando los codos hacia atrás y apretando las escápulas. Regresa lento sin dejar que el peso te encorve.",
            TipoPeso.POLEA,listOf(Musculo.DORSAL_ANCHO, Musculo.BICEPS_CABEZA_LARGA),
            R.drawable.gironda_neutro),
        EjercicioBase(42, "Remo Gironda Agarre Prono", "Siéntate en la máquina de remo bajo con los pies apoyados. Sujeta una barra recta con las palmas hacia abajo (agarre prono) y más abiertas que el ancho de tus hombros. Tira de la barra hacia la parte alta de tu abdomen o la base del pecho, manteniendo los codos hacia afuera. Regresa lento controlando el peso.",
            TipoPeso.POLEA,listOf(Musculo.DORSAL_ANCHO, Musculo.TRAPECIO),
            R.drawable.gironda_neutro),
        EjercicioBase(43, "Remo con Mancuernas Unilateral", "Apóyate con una mano y una rodilla en un banco plano, manteniendo la espalda paralela al suelo. Con la otra mano, sujeta la mancuerna y tírala hacia tu cadera, llevando el codo bien atrás. Baja lento hasta estirar el brazo.",
            TipoPeso.MANCUERNAS,listOf(Musculo.DORSAL_MEDIO, Musculo.TRAPECIO),
            R.drawable.remo_unilateral_mancuernas),
        EjercicioBase(44, "Remo con Barra", "Para el remo con barra, inclina el torso con la espalda recta y tira de la barra hacia tu ombligo apretando las escápulas. Baja de forma controlada sin balancear el cuerpo para maximizar el trabajo en el dorsal y proteger la zona lumbar.",
            TipoPeso.BARRA,listOf(Musculo.DORSAL_MEDIO, Musculo.TRAPECIO),
            R.drawable.remo_barra),
        EjercicioBase(45, "Pullover", "Siéntate de espaldas a la polea alta, sujeta la barra o cuerda por encima de tu cabeza y tira de ella hacia adelante y abajo, manteniendo los brazos casi rectos. Concéntrate en el estiramiento y la contracción del dorsal, evitando encorvar la espalda durante el recorrido.",
            TipoPeso.POLEA,listOf(Musculo.DORSAL_ANCHO),
            R.drawable.pullover_polea),
        EjercicioBase(46, "Jalón al Pecho", "Siéntate en la máquina apoyando bien las rodillas y sujeta la barra con un agarre más ancho que tus hombros. Tira de ella hacia la parte superior del pecho llevando los codos hacia abajo y atrás, apretando los dorsales, y regresa lento controlando el peso.",
            TipoPeso.POLEA,listOf(Musculo.DORSAL_ANCHO, Musculo.BICEPS_CABEZA_LARGA),
            R.drawable.jalon_pecho),
        EjercicioBase(47, "Jalón al Pecho Neutro", "Siéntate en la máquina y sujeta el agarre en V o un manillar de manos enfrentadas (palmas mirándose). Tira del peso hacia la parte superior del pecho manteniendo el torso ligeramente inclinado hacia atrás y los codos pegados al cuerpo para enfatizar el trabajo en el dorsal.",
            TipoPeso.POLEA,listOf(Musculo.DORSAL_ANCHO, Musculo.BICEPS_CABEZA_LARGA),
            R.drawable.jalon_pecho_cerrado),
        EjercicioBase(48, "Peso Muerto Rumano", "Sujeta la barra con las manos al ancho de los hombros y baja lentamente llevándola pegada a tus piernas mientras empujas la cadera hacia atrás, manteniendo la espalda recta y las rodillas casi extendidas. Detente al sentir el estiramiento máximo en los isquiotibiales y vuelve a subir apretando los glúteos sin llegar a bloquear las rodillas.",
            TipoPeso.BARRA,listOf(Musculo.LUMBARES, Musculo.ISQUIOS),
            R.drawable.peso_muerto_rumano),
        EjercicioBase(49, "Peso Muerto con Mancuernas", "Coloca las mancuernas frente a tus muslos y baja el torso flexionando caderas y rodillas simultáneamente, manteniendo la espalda recta y las pesas cerca del cuerpo. Una vez las mancuernas superen la altura de tus espinillas, sube con fuerza empujando el suelo con los talones hasta quedar erguido, apretando los glúteos al finalizar.",
            TipoPeso.MANCUERNAS,listOf(Musculo.LUMBARES, Musculo.ISQUIOS),
            R.drawable.peso_muerto_mancuernas),
        EjercicioBase(50, "Prensa", "Siéntate en la máquina con los pies apoyados al ancho de los hombros y baja la plataforma controladamente hasta que tus rodillas formen un ángulo de 90°, manteniendo siempre la espalda baja pegada al respaldo. Empuja hacia arriba con fuerza sin llegar a bloquear las rodillas por completo para evitar lesiones y mantener la tensión en los cuádriceps.",
            TipoPeso.MAQUINA,listOf(Musculo.CUADRICEPS, Musculo.GLUTEOS),
            R.drawable.prensa),
        EjercicioBase(51, "Hack Squat", "Baja en la máquina de forma controlada hasta que tus muslos estén paralelos a la plataforma, manteniendo la espalda pegada al respaldo. Empuja con fuerza sin bloquear las rodillas para mantener la tensión y proteger la articulación.",
            TipoPeso.MAQUINA,listOf(Musculo.CUADRICEPS, Musculo.GLUTEOS),
            R.drawable.hack_squat),
        EjercicioBase(52, "Extensión de Cuadriceps", "Siéntate en la máquina con la espalda bien apoyada y ajusta el rodillo sobre tus tobillos. Extiende las piernas hacia arriba hasta que queden rectas, apretando los cuádriceps, y baja el peso de forma lenta y controlada sin que llegue a tocar el tope.",
            TipoPeso.MAQUINA,listOf(Musculo.CUADRICEPS),
            R.drawable.extension_cuadriceps),
        EjercicioBase(53, "Sentadilla Libre con Barra", "Coloca la barra sobre tus trapecios, baja flexionando cadera y rodillas con la espalda recta hasta que los muslos estén paralelos al suelo y sube empujando con los talones. Mantén el core firme y evita que las rodillas colapsen hacia adentro.",
            TipoPeso.BARRA,listOf(Musculo.CUADRICEPS, Musculo.ISQUIOS),
            R.drawable.sentadilla_barra_libre),
        EjercicioBase(54, "Sentadilla Smith", "Coloca la barra sobre tus trapecios con los pies un poco adelantados respecto a la barra. Baja flexionando la cadera y rodillas hasta que los muslos estén paralelos al suelo y sube con fuerza, manteniendo la espalda recta contra la guía.",
            TipoPeso.MAQUINA,listOf(Musculo.CUADRICEPS, Musculo.GLUTEOS),
            R.drawable.sentadilla_smith),
        EjercicioBase(55, "Curl Femoral", "Túmbate boca abajo en la máquina con el rodillo sobre tus talones y flexiona las piernas llevando el peso hacia tus glúteos. Baja de forma lenta y controlada, manteniendo la cadera pegada al banco en todo momento para aislar los isquiotibiales.",
            TipoPeso.MAQUINA,listOf(Musculo.ISQUIOS),
            R.drawable.curl_femoral),
        EjercicioBase(56, "Curl Femoral Sentado", "Siéntate con la espalda apoyada y el rodillo sobre tus tobillos; flexiona las piernas hacia abajo apretando los isquiotibiales. Controla la subida evitando que el peso golpee, manteniendo siempre los muslos firmes contra el soporte.",
            TipoPeso.MAQUINA,listOf(Musculo.ISQUIOS),
            R.drawable.curl_femoral_sentado),
        EjercicioBase(57, "Zancadas", "Da un paso largo hacia adelante y baja la cadera hasta que ambas rodillas formen un ángulo de 90°, manteniendo el torso erguido. Empuja con el pie adelantado para volver a la posición inicial, evitando que la rodilla trasera golpee el suelo.",
            TipoPeso.MANCUERNAS,listOf(Musculo.CUADRICEPS, Musculo.ISQUIOS),
            R.drawable.zancada),
        EjercicioBase(58, "Sentadillas Búlgaras", "Apoya un pie en un banco detrás de ti y sujeta las mancuernas a los costados. Baja la cadera flexionando la pierna adelantada hasta que el muslo esté paralelo al suelo y sube con fuerza, manteniendo el torso ligeramente inclinado para enfocar el trabajo en el glúteo y cuádricep.",
            TipoPeso.MANCUERNAS,listOf(Musculo.GLUTEOS, Musculo.CUADRICEPS),
            R.drawable.sentadilla_bulgara),
        EjercicioBase(59, "Abductores en Máquina", "Siéntate en la máquina con las piernas abiertas y las almohadillas en la cara interna de las rodillas; cierra las piernas con fuerza hacia el centro apretando los aductores. Regresa a la posición inicial de forma lenta y controlada para mantener la tensión muscular durante todo el recorrido.",
            TipoPeso.MAQUINA,listOf(Musculo.ABDUCTORES),
            R.drawable.abductor_maquina),
        EjercicioBase(60, "Patada Lateral en Polea", "Coloca la polea a la altura del tobillo y sujeta el agarre con la pierna más cercana a la máquina pasando por detrás de la de apoyo. Desplaza la pierna hacia afuera alejándola del cuerpo con un movimiento controlado, apretando el glúteo medio, y regresa lentamente sin que las placas lleguen a chocar.",
            TipoPeso.POLEA,listOf(Musculo.GLUTEOS, Musculo.ABDUCTORES),
            R.drawable.abductor_polea),
        EjercicioBase(61, "Máquina de Gemelo Sentado", "Sentado con rodillas a 90° y peso sobre los muslos, eleva y baja los talones al máximo para aislar el sóleo, ya que esta flexión desactiva los gemelos.",
            TipoPeso.MAQUINA,listOf(Musculo.SOLEO),
            R.drawable.gemelo_sentado_maquina),
        EjercicioBase(62, "Gemelo de Pie Palanca", "De pie con los hombros bajo las almohadillas, apoya las puntas en el escalón y sube los talones al máximo con las piernas estiradas para enfocar el esfuerzo en los gemelos; baja controladamente hasta sentir un estiramiento profundo.",
            TipoPeso.MAQUINA,listOf(Musculo.GEMELO),
            R.drawable.gemelo_de_pie_maquina),
        EjercicioBase(63, "Curl Invertido", "Siéntate y apoya los antebrazos sobre los muslos con las palmas hacia abajo, dejando las manos libres para subir y bajar las muñecas, trabajando así los extensores del antebrazo.",
            TipoPeso.MANCUERNAS,listOf(Musculo.ANTEBRAZO),
            R.drawable.curl_muneca_mancuernas),
        EjercicioBase(64, "Flexión de muñeca en Banco con barra", "Siéntate frente a un banco y apoya los antebrazos sobre él con las palmas hacia arriba, dejando que las muñecas sobresalgan; deja que la barra ruede hacia la punta de tus dedos para estirar y luego enróllala hacia arriba flexionando las muñecas para trabajar la cara interna del antebrazo.",
            TipoPeso.BARRA,listOf(Musculo.ANTEBRAZO),
            R.drawable.curl_muneca_barra),
        EjercicioBase(65, "Colgarse de la barra", "Sujétate de una barra alta con las manos a la anchura de los hombros y deja caer el peso de tu cuerpo manteniendo los brazos estirados; esto permite descomprimir la columna, estirar los dorsales y fortalecer significativamente tu agarre.",
            TipoPeso.PROPIO_PESO,listOf(Musculo.ANTEBRAZO),
            R.drawable.colgar_barra),
        EjercicioBase(66, "Peso Muerto Rumano", "Sujeta las mancuernas frente a tus muslos y baja lentamente llevándolas pegadas a las piernas mientras empujas la cadera hacia atrás y mantienes la espalda recta. Baja hasta sentir el estiramiento en los isquiotibiales y vuelve a subir apretando los glúteos, manteniendo una ligera flexión en las rodillas durante todo el movimiento.",
            TipoPeso.MANCUERNAS,listOf(Musculo.LUMBARES, Musculo.ISQUIOS),
            R.drawable.peso_muerto_rumano_mancuernas),
        EjercicioBase(67, "Crunch Abdominal Polea", "Ponte de rodillas frente a la polea alta y sujeta la cuerda a los lados de tu cabeza; flexiona el torso hacia abajo intentando llevar los codos hacia las rodillas mediante la contracción del abdomen, manteniendo la cadera inmóvil para no involucrar el psoas.",
            TipoPeso.POLEA,listOf(Musculo.ABDOMINALES, Musculo.LUMBARES),
            R.drawable.crunch_abdominal_polea),
        EjercicioBase(68, "Plancha", "Apóyate en los antebrazos y las puntas de los pies manteniendo el cuerpo en línea recta como una tabla, con el abdomen y los glúteos contraídos para evitar que la cadera caiga o se eleve.",
            TipoPeso.PROPIO_PESO,listOf(Musculo.ABDOMINALES),
            R.drawable.plancha),
        EjercicioBase(69, "Hiperextensión Lumbar", "Ajústate en el banco de modo que la cadera quede libre, sujeta un disco contra tu pecho y baja el torso de forma controlada; después, sube hasta alinear la espalda con las piernas contrayendo lumbares y glúteos, evitando arquear la columna en exceso al final del movimiento.",
            TipoPeso.MAQUINA,listOf(Musculo.LUMBARES),
            R.drawable.maquina_lumbar)
    )

    /**
    private val ejerciciosPruebasVolumen = mutableListOf<EjercicioBase>().apply {
        var idActual = 1

        repeat(100) {
            ejerciciosIniciales.forEach { ejercicio ->
                add(
                    ejercicio.copy(
                        id = idActual++,
                        nombre = "${ejercicio.nombre} #$idActual"
                    )
                )
            }
        }
    }

     Estó se usó para hacer pruebas
     */
    private val listaEjerciciosPorDefecto = mutableListOf<EjercicioBase>()

    fun inicializar(context: Context, usuarioId: Int) {
        val dataSource = EjerciciosJsonDataSource(context, Gson())
        val ejerciciosGuardados = dataSource.obtenerEjercicios(usuarioId.toString())

        listaEjerciciosPorDefecto.clear()

        /**
         * BLOQUE AÑADIDO PARA PRUEBAS DE VOLUMEN
         * Este código se ejecuta únicamente si MODO_PRUEBAS = true
         * Permite inicializar la aplicación con un gran número de ejercicios
         * sin sobrescribir los datos existentes del usuario.
         */
        /*
        if (MODO_PRUEBAS) {

            // Fusiona los ejercicios de prueba con los ya existentes,
            // asegurando que los IDs sean únicos y evitando duplicados
            dataSource.fusionarEjercicios(
                usuarioId.toString(),
                ejerciciosPruebasVolumen
            )

            // Carga de nuevo los ejercicios desde almacenamiento interno
            // para trabajar con el estado persistido tras la fusión
            listaEjerciciosPorDefecto.addAll(
                dataSource.obtenerEjercicios(usuarioId.toString())
            )

        }
        */

        if (ejerciciosGuardados.isEmpty()) {
            listaEjerciciosPorDefecto.addAll(ejerciciosIniciales)
            dataSource.guardarEjercicios(
                usuarioId.toString(),
                listaEjerciciosPorDefecto
            )
        } else {
            listaEjerciciosPorDefecto.addAll(ejerciciosGuardados)
        }
    }

    fun obtenerTodosLosEjercicios(): List<EjercicioBase> = listaEjerciciosPorDefecto

    fun obtenerEjerciciosPrincipales() : List<EjercicioBase> = ejerciciosIniciales

    fun anadirEjercicio(context: Context, usuarioId: Int, ejercicio: EjercicioBase) {
        listaEjerciciosPorDefecto.add(ejercicio)
        val dataSource = EjerciciosJsonDataSource(context, Gson())
        dataSource.guardarEjercicios(usuarioId.toString(), listaEjerciciosPorDefecto)
    }
}