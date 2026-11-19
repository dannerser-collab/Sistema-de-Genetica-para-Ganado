package servicios;

import estructuras.GrafoPedigri;
import modelo.Bovino;
import modelo.Evento;
import modelo.TipoEvento;

/**
 * Clase encargada de precargar datos de prueba realistas en el sistema.
 * 
 * Contiene un hato estructurado de 16 bovinos distribuidos en 3 generaciones:
 * - Generación 1: Fundadores puros (Bisabuelos / Abuelos).
 * - Generación 2: Reproductores y matrices intermedias.
 * - Generación 3: Ejemplares jóvenes con diferentes grados de consanguinidad o pureza.
 * 
 * Incluye casos de prueba clave:
 * 1. Hermanos completos (Centauro y Pandora).
 * 2. Medio hermanos por vía paterna (Centauro y Valquiria).
 * 3. Cruce consanguíneo (Gladiador, hijo de hermanos completos).
 * 4. Ejemplares externos libres de parentesco (Apollo, Sultán, Pegaso, Zeus) para probar el Top 3.
 * 5. Historial clínico y productivo precargado con pesajes, vacunas y partos.
 */
public class DatosIniciales {

    public static void cargarDatos(GrafoPedigri grafo) {
        if (grafo == null) return;

        // =========================================================================
        // GENERACIÓN 1: FUNDADORES (Sin padres registrados en el hato)
        // =========================================================================
        Bovino f1 = new Bovino("TORO-F1", "Titan", "Nelore", 'M', 980.0, 92.5, null, null);
        f1.agregarEvento(new Evento("2021-01-10", TipoEvento.VACUNACION, "Vacuna Antiaftosa Oleosa A+O"));
        f1.agregarEvento(new Evento("2021-06-15", TipoEvento.PESAJE, "Pesaje de control reproductivo", 940.0));
        f1.agregarEvento(new Evento("2022-03-20", TipoEvento.PESAJE, "Pesaje adulto pico de servicio", 980.0));

        Bovino f2 = new Bovino("VACA-F1", "Atenea", "Nelore", 'H', 650.0, 88.0, null, null);
        f2.agregarEvento(new Evento("2021-02-14", TipoEvento.VACUNACION, "Vacuna Cepa 19 Brucelosis"));
        f2.agregarEvento(new Evento("2022-04-10", TipoEvento.PARTO, "Parto natural de ternero macho (Centauro)", 38.0));

        Bovino f3 = new Bovino("TORO-F2", "Baron", "Brahman", 'M', 950.0, 90.0, null, null);
        f3.agregarEvento(new Evento("2021-03-05", TipoEvento.VACUNACION, "Vacuna Carbunco Bacteridiano"));
        f3.agregarEvento(new Evento("2022-05-12", TipoEvento.PESAJE, "Pesaje de semental", 950.0));

        Bovino f4 = new Bovino("VACA-F2", "Diana", "Brahman", 'H', 620.0, 86.5, null, null);
        f4.agregarEvento(new Evento("2021-03-05", TipoEvento.VACUNACION, "Vacuna Antiaftosa"));

        Bovino f5 = new Bovino("TORO-F3", "Zeus", "Girolando", 'M', 920.0, 94.0, null, null);
        f5.agregarEvento(new Evento("2021-05-18", TipoEvento.CONTROL_REPRODUCTIVO, "Evaluación Andrológica Aprobada (Vigor 5/5)"));

        Bovino f6 = new Bovino("VACA-F3", "Afrodita", "Girolando", 'H', 610.0, 89.0, null, null);
        f6.agregarEvento(new Evento("2021-06-22", TipoEvento.CONTROL_REPRODUCTIVO, "Palpación positiva preñez confirmada"));

        // =========================================================================
        // GENERACIÓN 2: PROGENITORES INTERMEDIOS (Hijos de G1)
        // =========================================================================
        // Centauro y Pandora son hermanos completos (ambos hijos de Titan y Atenea)
        Bovino g2a = new Bovino("TORO-G2A", "Centauro", "Nelore", 'M', 890.0, 95.0, "TORO-F1", "VACA-F1");
        g2a.agregarEvento(new Evento("2022-05-10", TipoEvento.PESAJE, "Pesaje al destete", 240.0));
        g2a.agregarEvento(new Evento("2023-08-15", TipoEvento.PESAJE, "Pesaje a los 18 meses", 480.0));
        g2a.agregarEvento(new Evento("2024-02-20", TipoEvento.CONTROL_REPRODUCTIVO, "Examen de semen congelable"));

        Bovino g2b = new Bovino("VACA-G2A", "Pandora", "Nelore", 'H', 640.0, 91.0, "TORO-F1", "VACA-F1");
        g2b.agregarEvento(new Evento("2022-06-18", TipoEvento.VACUNACION, "Vacuna Triple Bovina"));
        g2b.agregarEvento(new Evento("2023-11-05", TipoEvento.PARTO, "Parto de ternero Gladiador", 41.0));

        // Valquiria es medio hermana de Centauro (comparte padre Titan con madre externa)
        Bovino g2c = new Bovino("VACA-G2B", "Valquiria", "Nelore", 'H', 630.0, 87.5, "TORO-F1", null);
        g2c.agregarEvento(new Evento("2022-07-02", TipoEvento.PESAJE, "Pesaje desarrollo corporal", 460.0));
        g2c.agregarEvento(new Evento("2024-01-12", TipoEvento.PARTO, "Parto de ternera Estrella", 36.5));

        // Thor y Freya (Línea Brahman)
        Bovino g2d = new Bovino("TORO-G2B", "Thor", "Brahman", 'M', 910.0, 93.0, "TORO-F2", "VACA-F2");
        g2d.agregarEvento(new Evento("2022-08-14", TipoEvento.VACUNACION, "Vacuna Antirrábica Bovina"));

        Bovino g2e = new Bovino("VACA-G2C", "Freya", "Brahman", 'H', 600.0, 85.0, "TORO-F2", "VACA-F2");
        g2e.agregarEvento(new Evento("2023-01-20", TipoEvento.TRATAMIENTO, "Tratamiento desparasitante Ivermectina"));

        // Apollo (Línea Girolando élite - Sin parentesco con Nelore ni Brahman)
        Bovino g2f = new Bovino("TORO-G2C", "Apollo", "Girolando", 'M', 870.0, 96.5, "TORO-F3", "VACA-F3");
        g2f.agregarEvento(new Evento("2022-09-10", TipoEvento.CONTROL_REPRODUCTIVO, "Prueba de progenie lechera sobresaliente"));
        g2f.agregarEvento(new Evento("2024-03-01", TipoEvento.PESAJE, "Pesaje actual", 870.0));

        // =========================================================================
        // GENERACIÓN 3: EJEMPLARES JÓVENES (Nacidos en el hato)
        // =========================================================================
        // Estrella: Hija de Centauro y Valquiria (Cruce entre medio hermanos -> Consanguinidad moderada)
        Bovino g3a = new Bovino("VACA-G3A", "Estrella", "Nelore", 'H', 540.0, 93.5, "TORO-G2A", "VACA-G2B");
        g3a.agregarEvento(new Evento("2024-01-12", TipoEvento.PESAJE, "Pesaje al nacimiento", 36.5));
        g3a.agregarEvento(new Evento("2024-07-15", TipoEvento.PESAJE, "Pesaje al destete", 215.0));
        g3a.agregarEvento(new Evento("2025-02-10", TipoEvento.VACUNACION, "Vacunación Aftosa y Mancha"));
        g3a.agregarEvento(new Evento("2025-06-20", TipoEvento.PESAJE, "Pesaje de novilla de levante", 540.0));

        // Gladiador: Hijo de Centauro y Pandora (Hermanos completos -> Consanguinidad crítica 25%)
        Bovino g3b = new Bovino("TORO-G3A", "Gladiador", "Nelore", 'M', 820.0, 91.5, "TORO-G2A", "VACA-G2A");
        g3b.agregarEvento(new Evento("2023-11-05", TipoEvento.PESAJE, "Pesaje al nacimiento", 41.0));
        g3b.agregarEvento(new Evento("2024-11-10", TipoEvento.PESAJE, "Pesaje al año", 420.0));

        // Bella: Hija de Thor y Freya
        Bovino g3c = new Bovino("VACA-G3B", "Bella", "Brahman", 'H', 520.0, 89.0, "TORO-G2B", "VACA-G2C");
        g3c.agregarEvento(new Evento("2024-02-18", TipoEvento.VACUNACION, "Vacuna Clostridial 8 Vías"));

        // Sultán: Toro élite Brahman libre de consanguinidad con vacas Nelore
        Bovino g3d = new Bovino("TORO-G3B", "Sultan", "Brahman", 'M', 850.0, 97.0, "TORO-G2B", null);
        g3d.agregarEvento(new Evento("2023-12-01", TipoEvento.PESAJE, "Pesaje de selección genética", 850.0));
        g3d.agregarEvento(new Evento("2024-05-15", TipoEvento.CONTROL_REPRODUCTIVO, "Calificación fenotípica 98/100"));

        // Pegaso: Toro Nelore externo de alto valor genético (fundador de nuevo linaje)
        Bovino g3e = new Bovino("TORO-G3C", "Pegaso", "Nelore", 'M', 830.0, 94.5, null, null);
        g3e.agregarEvento(new Evento("2024-04-10", TipoEvento.VACUNACION, "Certificado de cuarentena e inmunización"));
        g3e.agregarEvento(new Evento("2024-08-20", TipoEvento.PESAJE, "Pesaje de incorporación al hato", 830.0));

        // Luna: Novilla Girolando F1 cruzada
        Bovino g3f = new Bovino("VACA-G3C", "Luna", "Girolando", 'H', 490.0, 92.0, "TORO-G2C", null);
        g3f.agregarEvento(new Evento("2024-06-12", TipoEvento.PESAJE, "Pesaje desarrollo lechero", 490.0));

        // =========================================================================
        // INSERCIÓN DE TODOS LOS EJEMPLARES EN EL GRAFO DE PEDIGRÍ
        // =========================================================================
        grafo.agregarBovino(f1);
        grafo.agregarBovino(f2);
        grafo.agregarBovino(f3);
        grafo.agregarBovino(f4);
        grafo.agregarBovino(f5);
        grafo.agregarBovino(f6);

        grafo.agregarBovino(g2a);
        grafo.agregarBovino(g2b);
        grafo.agregarBovino(g2c);
        grafo.agregarBovino(g2d);
        grafo.agregarBovino(g2e);
        grafo.agregarBovino(g2f);

        grafo.agregarBovino(g3a);
        grafo.agregarBovino(g3b);
        grafo.agregarBovino(g3c);
        grafo.agregarBovino(g3d);
        grafo.agregarBovino(g3e);
        grafo.agregarBovino(g3f);

        // =========================================================================
        // CONECTAR ARISTAS GENEALÓGICAS (Padres -> Hijos)
        // =========================================================================
        grafo.conectarGenealogia();
    }
}
