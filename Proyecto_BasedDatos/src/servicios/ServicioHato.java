package servicios;

import estructuras.ArbolAncestros;
import estructuras.ColaPrioridadGenetica;
import estructuras.GrafoPedigri;
import estructuras.ListaBovinos;
import estructuras.NodoBovino;
import estructuras.NodoGrafo;
import modelo.Bovino;
import modelo.Evento;
import modelo.ResultadoConsanguinidad;
import modelo.TipoEvento;

/**
 * Servicio Central de Gestión del Hato Bovino.
 * 
 * Orquesta la lógica de negocio integrando las estructuras de datos propias:
 * - GrafoPedigri (DAG genealógico y análisis BFS de consanguinidad).
 * - ArbolAncestros (Árbol binario genealógico y cálculo de pureza racial).
 * - ColaPrioridadGenetica (Max-Heap para recomendación de los mejores reproductores).
 * - ListaEventos (Historial sanitario y productivo enlazado).
 */
public class ServicioHato {
    private GrafoPedigri grafo;

    public ServicioHato() {
        this.grafo = new GrafoPedigri();
        DatosIniciales.cargarDatos(this.grafo);
    }

    public GrafoPedigri getGrafo() {
        return grafo;
    }

    /**
     * Registra un nuevo bovino en el hato y actualiza las conexiones del grafo genealógico.
     */
    public boolean registrarBovino(String idArete, String nombre, String raza, char sexo,
                                   double pesoKg, double indiceGenetico, String idPadre, String idMadre) {
        if (idArete == null || idArete.trim().isEmpty()) {
            System.out.println("[X] Error: El ID de arete no puede estar vacio.");
            return false;
        }

        if (grafo.buscarNodo(idArete) != null) {
            System.out.println("[X] Error: Ya existe un bovino con el ID de arete " + idArete);
            return false;
        }

        sexo = Character.toUpperCase(sexo);
        if (sexo != 'M' && sexo != 'H') {
            System.out.println("[X] Error: El sexo debe ser 'M' (Macho) o 'H' (Hembra).");
            return false;
        }

        Bovino nuevo = new Bovino(idArete, nombre, raza, sexo, pesoKg, indiceGenetico, idPadre, idMadre);
        nuevo.agregarEvento(new Evento("2026-01-01", TipoEvento.PESAJE, "Pesaje inicial de registro", pesoKg));

        grafo.agregarBovino(nuevo);
        grafo.conectarGenealogia();

        System.out.printf("[OK] Bovino [%s] %s registrado exitosamente y conectado al grafo genealogico.\n",
                nuevo.getIdArete(), nuevo.getNombre());
        return true;
    }

    /**
     * Registra un nuevo evento clínico o productivo en el historial del bovino.
     */
    public boolean registrarEvento(String idArete, String fecha, TipoEvento tipo, String descripcion, double valor) {
        NodoGrafo nodo = grafo.buscarNodo(idArete);
        if (nodo == null) {
            System.out.println("[X] Error: No se encontro ningun bovino con el ID: " + idArete);
            return false;
        }

        Bovino b = nodo.getBovino();
        Evento evento = new Evento(fecha, tipo, descripcion, valor);
        b.agregarEvento(evento);

        // Si el evento es pesaje, actualizar el peso actual del bovino
        if (tipo == TipoEvento.PESAJE && valor > 0) {
            b.setPesoKg(valor);
        }

        System.out.println("[OK] Evento registrado en el historial de " + b.getNombre() + ": " + evento.toString());
        return true;
    }

    /**
     * Lista todos los bovinos registrados en el grafo con su información general.
     */
    public void listarTodosLosBovinos() {
        System.out.println("=========================================================================================================");
        System.out.println("                                  CENSO GENERAL DEL HATO BOVINO                                         ");
        System.out.println("=========================================================================================================");
        System.out.printf("%-10s %-16s | %-10s | %-10s | %-10s | %-12s | %-8s | %-8s\n",
                "ID ARETE", "NOMBRE", "RAZA", "SEXO", "PESO (KG)", "ÍNDICE GEN.", "PADRE", "MADRE");
        System.out.println("---------------------------------------------------------------------------------------------------------");

        NodoGrafo actual = grafo.getPrimerNodo();
        int count = 0;
        while (actual != null) {
            Bovino b = actual.getBovino();
            String padre = (b.getIdPadre() != null) ? b.getIdPadre() : "N/D";
            String madre = (b.getIdMadre() != null) ? b.getIdMadre() : "N/D";
            String sexoStr = b.esMacho() ? "Toro (M)" : "Vaca (H)";
            System.out.printf("[%-8s] %-16s | %-10s | %-10s | %8.1f kg | %10.1f/100 | %-8s | %-8s\n",
                    b.getIdArete(), b.getNombre(), b.getRaza(), sexoStr, b.getPesoKg(), b.getIndiceGenetico(), padre, madre);
            actual = actual.getSiguienteEnGrafo();
            count++;
        }
        System.out.println("=========================================================================================================");
        System.out.println("  Total de ejemplares en el hato: " + count);
        System.out.println("=========================================================================================================");
    }

    /**
     * Muestra la ficha técnica detallada y el historial clínico de un bovino.
     */
    public void consultarFichaBovino(String idArete) {
        NodoGrafo nodo = grafo.buscarNodo(idArete);
        if (nodo == null) {
            System.out.println("❌ No se encontró el ejemplar con ID: " + idArete);
            return;
        }

        Bovino b = nodo.getBovino();
        System.out.println("==========================================================================");
        System.out.println("                      FICHA INDIVIDUAL DEL BOVINO                         ");
        System.out.println("==========================================================================");
        System.out.printf("  ID de Arete         : %s\n", b.getIdArete());
        System.out.printf("  Nombre              : %s\n", b.getNombre());
        System.out.printf("  Raza Principal      : %s\n", b.getRaza());
        System.out.printf("  Sexo                : %s\n", b.esMacho() ? "Macho / Toro Reproductor" : "Hembra / Vaca Matriz");
        System.out.printf("  Peso Corporal       : %.1f kg\n", b.getPesoKg());
        System.out.printf("  Índice Genético     : %.1f / 100.0\n", b.getIndiceGenetico());
        System.out.printf("  Progenitor Paterno  : %s\n", (b.getIdPadre() != null) ? b.getIdPadre() : "Fundador (No registrado)");
        System.out.printf("  Progenitor Materno  : %s\n", (b.getIdMadre() != null) ? b.getIdMadre() : "Fundadora (No registrada)");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("  HISTORIAL PRODUCTIVO Y SANITARIO (Lista Enlazada Simple):");
        b.getHistorial().imprimirEventos();
        System.out.println("==========================================================================");
    }

    /**
     * Construye y visualiza el Árbol Binario Genealógico y calcula la pureza racial.
     */
    public void visualizarArbolGenealogico(String idArete) {
        NodoGrafo nodo = grafo.buscarNodo(idArete);
        if (nodo == null) {
            System.out.println("❌ No se encontró el ejemplar con ID: " + idArete);
            return;
        }

        Bovino b = nodo.getBovino();
        ArbolAncestros arbol = new ArbolAncestros();
        arbol.construir(b, grafo, 3);
        arbol.visualizar();

        double pureza = arbol.calcularPurezaRacial();
        System.out.printf("  [GEN] Porcentaje Estimado de Pureza Racial (%s): %.2f%%\n", b.getRaza(), pureza);
        if (pureza >= 93.75) {
            System.out.println("  [OK] Clasificacion Zootecnica: Puro de Pedigri / F4 (Apto para registro oficial)");
        } else if (pureza >= 75.0) {
            System.out.println("  [OK] Clasificacion Zootecnica: Alto Mestizaje / F2-F3 (Excelente para produccion comercial)");
        } else if (pureza >= 50.0) {
            System.out.println("  [OK] Clasificacion Zootecnica: Media Sangre / F1 (Maximo vigor hibrido / heterosis)");
        } else {
            System.out.println("  [OK] Clasificacion Zootecnica: Cruce multirracial");
        }
        System.out.println("==========================================================================");
    }

    /**
     * Evalua la consanguinidad entre un toro y una vaca en consola.
     */
    public ResultadoConsanguinidad evaluarCruce(String idToro, String idVaca) {
        NodoGrafo nodoToro = grafo.buscarNodo(idToro);
        NodoGrafo nodoVaca = grafo.buscarNodo(idVaca);

        if (nodoToro == null) {
            System.out.println("[X] Error: No se encontro el Toro con ID: " + idToro);
            return null;
        }
        if (nodoVaca == null) {
            System.out.println("[X] Error: No se encontro la Vaca con ID: " + idVaca);
            return null;
        }

        if (!nodoToro.getBovino().esMacho()) {
            System.out.printf("[!] Advertencia: El ejemplar [%s] no es macho (%c).\n", idToro, nodoToro.getBovino().getSexo());
        }
        if (!nodoVaca.getBovino().esHembra()) {
            System.out.printf("[!] Advertencia: El ejemplar [%s] no es hembra (%c).\n", idVaca, nodoVaca.getBovino().getSexo());
        }

        ResultadoConsanguinidad resultado = grafo.evaluarConsanguinidad(idToro, idVaca);
        resultado.imprimirReporte();
        return resultado;
    }

    /**
     * Seleccion Genetica Inteligente:
     * Filtra los toros con 0% de consanguinidad con la vaca indicada,
     * los encola en la Cola de Prioridad (Max-Heap) por indice genetico,
     * y extrae el TOP 3 de mejores sementales recomendados.
     */
    public void recomendarTop3Cruces(String idVaca) {
        NodoGrafo nodoVaca = grafo.buscarNodo(idVaca);
        if (nodoVaca == null) {
            System.out.println("[X] Error: No se encontro la Vaca con ID: " + idVaca);
            return;
        }

        Bovino vaca = nodoVaca.getBovino();
        if (!vaca.esHembra()) {
            System.out.printf("[!] Advertencia: El ejemplar [%s] esta registrado como Macho.\n", idVaca);
        }

        System.out.println("==========================================================================");
        System.out.println("          SELECCION GENETICA INTELIGENTE: RECOMENDACION TOP 3             ");
        System.out.println("==========================================================================");
        System.out.printf("  Vaca Matriz Evaluada: [%s] %s | Raza: %s | I.G: %.1f | Peso: %.1f kg\n",
                vaca.getIdArete(), vaca.getNombre(), vaca.getRaza(), vaca.getIndiceGenetico(), vaca.getPesoKg());
        System.out.println("  Criterio de Seleccion: 0% Consanguinidad (Sin ancestros comunes en 3 generaciones)");
        System.out.println("                         Ordenamiento por Max-Heap segun Indice Genetico.");
        System.out.println("--------------------------------------------------------------------------");

        ColaPrioridadGenetica colaPrioridad = new ColaPrioridadGenetica();
        ListaBovinos torosDescartados = new ListaBovinos();

        NodoGrafo actual = grafo.getPrimerNodo();
        int totalTorosEvaluados = 0;

        while (actual != null) {
            Bovino candidato = actual.getBovino();
            if (candidato.esMacho()) {
                totalTorosEvaluados++;
                ResultadoConsanguinidad evaluacion = grafo.evaluarConsanguinidad(candidato.getIdArete(), vaca.getIdArete());

                if (evaluacion.getCoeficienteConsanguinidad() == 0.0 && !evaluacion.isAlertaConsanguinidad()) {
                    colaPrioridad.insertar(candidato);
                } else {
                    torosDescartados.insertar(candidato);
                }
            }
            actual = actual.getSiguienteEnGrafo();
        }

        System.out.printf("  Toros evaluados en el hato   : %d\n", totalTorosEvaluados);
        System.out.printf("  Toros 100%% Compatibles (F=0%%) : %d\n", colaPrioridad.getTamanio());
        System.out.printf("  Toros Descartados (F > 0%%)   : %d\n", torosDescartados.getTamanio());
        System.out.println("--------------------------------------------------------------------------");

        if (colaPrioridad.estaVacia()) {
            System.out.println("  [X] No se encontraron toros libres de consanguinidad en el hato para esta vaca.");
            System.out.println("      Se sugiere la incorporacion de sementales o pajillas externas.");
        } else {
            System.out.println("  [TOP 3] MEJORES CRUCES RECOMENDADOS (Extraidos del Max-Heap):");
            int puesto = 1;
            while (!colaPrioridad.estaVacia() && puesto <= 3) {
                Bovino toroTop = colaPrioridad.extraerMaximo();
                double promedioIG = (vaca.getIndiceGenetico() + toroTop.getIndiceGenetico()) / 2.0;

                System.out.printf("\n  (*) PUESTO #%d: [%s] %s\n", puesto, toroTop.getIdArete(), toroTop.getNombre());
                System.out.printf("     - Raza             : %s (Cruce: %s x %s)\n", toroTop.getRaza(), toroTop.getRaza(), vaca.getRaza());
                System.out.printf("     - Indice Genetico  : %.1f / 100.0 (Merito elite)\n", toroTop.getIndiceGenetico());
                System.out.printf("     - Peso Corporal    : %.1f kg\n", toroTop.getPesoKg());
                System.out.printf("     - I.G. Proyectado  : %.1f (Promedio parental esperado)\n", promedioIG);
                System.out.println("     - Estado Genetico  : [OK] 0.0% Consanguinidad | Vigor Hibrido Garantizado");
                puesto++;
            }
        }

        // Mostrar toros descartados si existen
        if (!torosDescartados.estaVacia()) {
            System.out.println("\n--------------------------------------------------------------------------");
            System.out.println("  [!] TOROS DESCARTADOS POR RIESGO DE CONSANGUINIDAD:");
            NodoBovino actualDesc = torosDescartados.getCabeza();
            while (actualDesc != null) {
                Bovino tDesc = actualDesc.getBovino();
                ResultadoConsanguinidad r = grafo.evaluarConsanguinidad(tDesc.getIdArete(), vaca.getIdArete());
                System.out.printf("   [X] [%s] %-12s | F = %5.2f%% | %s\n",
                        tDesc.getIdArete(), tDesc.getNombre(), r.getCoeficienteConsanguinidad(), r.getNivelRiesgo());
                actualDesc = actualDesc.getSiguiente();
            }
        }
        System.out.println("==========================================================================");
    }

    /**
     * Muestra estadísticas globales del hato sin usar librerías externas.
     */
    public void mostrarEstadisticasHato() {
        int total = 0;
        int machos = 0;
        int hembras = 0;
        double sumaPeso = 0;
        double sumaIG = 0;
        double maxIG = -1;
        double minIG = 101;
        String mejorBovino = "";

        NodoGrafo actual = grafo.getPrimerNodo();
        while (actual != null) {
            Bovino b = actual.getBovino();
            total++;
            if (b.esMacho()) machos++;
            if (b.esHembra()) hembras++;
            sumaPeso += b.getPesoKg();
            sumaIG += b.getIndiceGenetico();

            if (b.getIndiceGenetico() > maxIG) {
                maxIG = b.getIndiceGenetico();
                mejorBovino = "[" + b.getIdArete() + "] " + b.getNombre() + " (" + b.getRaza() + ")";
            }
            if (b.getIndiceGenetico() < minIG) {
                minIG = b.getIndiceGenetico();
            }

            actual = actual.getSiguienteEnGrafo();
        }

        System.out.println("==========================================================================");
        System.out.println("                     ESTADÍSTICAS GENERALES DEL HATO                      ");
        System.out.println("==========================================================================");
        System.out.printf("  Población Total del Hato     : %d ejemplares\n", total);
        System.out.printf("  Machos (Toros/Sementales)    : %d (%.1f%%)\n", machos, (total > 0 ? (machos * 100.0 / total) : 0));
        System.out.printf("  Hembras (Vacas/Novillas)     : %d (%.1f%%)\n", hembras, (total > 0 ? (hembras * 100.0 / total) : 0));
        System.out.printf("  Peso Promedio del Hato       : %.1f kg\n", (total > 0 ? (sumaPeso / total) : 0));
        System.out.printf("  Índice Genético Promedio     : %.2f / 100.0\n", (total > 0 ? (sumaIG / total) : 0));
        System.out.printf("  Rango de Índice Genético     : Min %.1f | Max %.1f\n", (total > 0 ? minIG : 0), (total > 0 ? maxIG : 0));
        System.out.printf("  Ejemplar de Mayor Mérito     : %s (%.1f/100)\n", mejorBovino, maxIG);
        System.out.println("==========================================================================");
    }
}
