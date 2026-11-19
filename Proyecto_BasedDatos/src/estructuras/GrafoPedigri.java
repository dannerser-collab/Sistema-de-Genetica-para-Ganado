package estructuras;

import modelo.Bovino;
import modelo.ResultadoConsanguinidad;

/**
 * Estructura de Datos: Grafo Dirigido Acíclico (DAG) de Pedigrí (100% propio).
 * Modela la red genealógica completa del hato ganadero.
 * 
 * Lógica:
 * - Vértices: Representados por la clase NodoGrafo (cada uno almacena un Bovino).
 * - Aristas ascendentes: Enlaces a progenitor paterno y materno.
 * - Aristas descendentes: Lista de adyacencia de hijos biológicos.
 * - Es acíclico por definición biológica (un descendiente no puede ser ancestro de sí mismo).
 * - Recorrido BFS (Breadth-First Search) implementado con nuestra propia ColaSimple
 *   para explorar exhaustivamente hasta 3 generaciones de ancestros (padres, abuelos, bisabuelos).
 * 
 * Complejidad:
 * - Inserción de vértice: O(1).
 * - Conexión de aristas (vinculación genealógica): O(V) por nodo, O(V^2) total si no hay índice hash,
 *   lo cual es óptimo para la escala del hato sin usar librerías externas.
 * - Búsqueda de nodo por ID: O(V).
 * - Recorrido BFS de 3 generaciones: O(b^d) donde b=2 (padre y madre) y d=3 (niveles).
 *   Por tanto, a lo sumo visita 2^1 + 2^2 + 2^3 = 14 nodos. Es O(1) tiempo acotado.
 * - Espacio: O(V + E) donde E <= 2*V (cada animal tiene a lo sumo 2 progenitores directos).
 */
public class GrafoPedigri {
    private NodoGrafo primerNodo;
    private int totalBovinos;

    public GrafoPedigri() {
        this.primerNodo = null;
        this.totalBovinos = 0;
    }

    /**
     * Agrega un nuevo bovino al grafo como vértice. O(1).
     */
    public void agregarBovino(Bovino bovino) {
        if (bovino == null) return;
        if (buscarNodo(bovino.getIdArete()) != null) {
            return; // Ya existe en el grafo
        }
        NodoGrafo nuevo = new NodoGrafo(bovino);
        nuevo.setSiguienteEnGrafo(primerNodo);
        primerNodo = nuevo;
        totalBovinos++;
    }

    /**
     * Busca un vértice en el grafo según el ID de arete. O(V).
     */
    public NodoGrafo buscarNodo(String idArete) {
        if (idArete == null) return null;
        String idBuscado = idArete.trim().toUpperCase();
        NodoGrafo actual = primerNodo;
        while (actual != null) {
            if (actual.getBovino().getIdArete().equalsIgnoreCase(idBuscado)) {
                return actual;
            }
            actual = actual.getSiguienteEnGrafo();
        }
        return null;
    }

    /**
     * Conecta las referencias de padres e hijos entre todos los vértices del grafo.
     * Complejidad: O(V * V) = O(V^2) sin colecciones externas.
     */
    public void conectarGenealogia() {
        NodoGrafo actual = primerNodo;
        while (actual != null) {
            Bovino b = actual.getBovino();
            if (b.getIdPadre() != null) {
                NodoGrafo padreNodo = buscarNodo(b.getIdPadre());
                if (padreNodo != null) {
                    actual.setPadre(padreNodo);
                    padreNodo.agregarDescendiente(actual, (b.esMacho() ? "HIJO" : "HIJA"));
                }
            }
            if (b.getIdMadre() != null) {
                NodoGrafo madreNodo = buscarNodo(b.getIdMadre());
                if (madreNodo != null) {
                    actual.setMadre(madreNodo);
                    madreNodo.agregarDescendiente(actual, (b.esMacho() ? "HIJO" : "HIJA"));
                }
            }
            actual = actual.getSiguienteEnGrafo();
        }
    }

    /**
     * Algoritmo de recorrido en anchura (BFS) propio que explora hacia atrás
     * hasta 'maxGeneraciones' (por defecto 3) para recopilar todos los ancestros de un ejemplar.
     * 
     * Complejidad temporal: O(1) acotado (a lo sumo 14 visitas para 3 niveles).
     * Complejidad espacial: O(1) acotado en la cola enlazada.
     */
    public ListaAncestros recorrerAncestrosBFS(NodoGrafo inicio, int maxGeneraciones) {
        ListaAncestros listaAncestros = new ListaAncestros();
        if (inicio == null) return listaAncestros;

        ColaSimple<ElementoBFS> cola = new ColaSimple<>();

        // Encolar progenitores directos (Generación 1)
        if (inicio.getPadre() != null) {
            cola.encolar(new ElementoBFS(inicio.getPadre(), 1, "Padre"));
        }
        if (inicio.getMadre() != null) {
            cola.encolar(new ElementoBFS(inicio.getMadre(), 1, "Madre"));
        }

        while (!cola.estaVacia()) {
            ElementoBFS elemento = cola.desencolar();
            NodoGrafo nodoActual = elemento.getNodo();
            int genActual = elemento.getGeneracion();
            String parentesco = elemento.getRama();

            listaAncestros.insertar(new RegistroAncestro(nodoActual.getBovino(), genActual, parentesco));

            // Si aún no alcanzamos la cota máxima de generaciones, encolar sus progenitores
            if (genActual < maxGeneraciones) {
                if (nodoActual.getPadre() != null) {
                    String descPadre = (genActual == 1) ? ("Abuelo (" + parentesco + ")") : ("Bisabuelo (" + parentesco + ")");
                    cola.encolar(new ElementoBFS(nodoActual.getPadre(), genActual + 1, descPadre));
                }
                if (nodoActual.getMadre() != null) {
                    String descMadre = (genActual == 1) ? ("Abuela (" + parentesco + ")") : ("Bisabuela (" + parentesco + ")");
                    cola.encolar(new ElementoBFS(nodoActual.getMadre(), genActual + 1, descMadre));
                }
            }
        }

        return listaAncestros;
    }

    /**
     * Evalúa la consanguinidad entre un toro y una vaca mediante el cruce de sus recorridos BFS.
     * Calcula el coeficiente de consanguinidad de Wright:
     * F = sum( (1/2)^(n1 + n2 + 1) ) * 100%
     * donde n1 es la distancia generacional del toro al ancestro común, y n2 la de la vaca.
     * 
     * Complejidad: O(k1 * k2) donde k1, k2 <= 14 ancestros. Es O(1) constante en la práctica.
     */
    public ResultadoConsanguinidad evaluarConsanguinidad(String idToro, String idVaca) {
        NodoGrafo nodoToro = buscarNodo(idToro);
        NodoGrafo nodoVaca = buscarNodo(idVaca);

        if (nodoToro == null || nodoVaca == null) {
            return null;
        }

        Bovino toro = nodoToro.getBovino();
        Bovino vaca = nodoVaca.getBovino();

        ListaAncestros comunes = new ListaAncestros();
        double sumatoriaWright = 0.0;

        // Caso 1: Mismo animal (prohibido cruce con uno mismo)
        if (toro.getIdArete().equalsIgnoreCase(vaca.getIdArete())) {
            sumatoriaWright = 1.0;
            comunes.insertar(new RegistroAncestro(toro, 0, "Mismo individuo"));
            return new ResultadoConsanguinidad(toro, vaca, 100.0, comunes);
        }

        // Caso 2: El toro es el padre biológico directo de la vaca (n1=0, n2=1)
        if (vaca.getIdPadre() != null && vaca.getIdPadre().equalsIgnoreCase(toro.getIdArete())) {
            sumatoriaWright += Math.pow(0.5, 0 + 1 + 1); // 0.25 (25%)
            comunes.insertar(new RegistroAncestro(toro, 1, "El toro es el padre biológico de la vaca"));
        }

        // Caso 3: La vaca es la madre biológica directa del toro (n1=1, n2=0)
        if (toro.getIdMadre() != null && toro.getIdMadre().equalsIgnoreCase(vaca.getIdArete())) {
            sumatoriaWright += Math.pow(0.5, 1 + 0 + 1); // 0.25 (25%)
            comunes.insertar(new RegistroAncestro(vaca, 1, "La vaca es la madre biológica del toro"));
        }

        // Ejecutar recorridos BFS hasta 3 generaciones
        ListaAncestros ancestrosToro = recorrerAncestrosBFS(nodoToro, 3);
        ListaAncestros ancestrosVaca = recorrerAncestrosBFS(nodoVaca, 3);

        // Caso 4: El toro es ancestro de 2da o 3ra gen de la vaca (abuelo o bisabuelo)
        RegistroAncestro toroComoAncestro = ancestrosVaca.buscarPorId(toro.getIdArete());
        if (toroComoAncestro != null && !comunes.contiene(toro.getIdArete())) {
            int n2 = toroComoAncestro.getGeneracion();
            sumatoriaWright += Math.pow(0.5, 0 + n2 + 1);
            comunes.insertar(new RegistroAncestro(toro, n2, "El toro es " + toroComoAncestro.getNombreGeneracion() + " de la vaca"));
        }

        // Caso 5: La vaca es ancestra de 2da o 3ra gen del toro (abuela o bisabuela)
        RegistroAncestro vacaComoAncestro = ancestrosToro.buscarPorId(vaca.getIdArete());
        if (vacaComoAncestro != null && !comunes.contiene(vaca.getIdArete())) {
            int n1 = vacaComoAncestro.getGeneracion();
            sumatoriaWright += Math.pow(0.5, n1 + 0 + 1);
            comunes.insertar(new RegistroAncestro(vaca, n1, "La vaca es " + vacaComoAncestro.getNombreGeneracion() + " del toro"));
        }

        // Caso 6: Ancestros compartidos entre ambos linajes (padres, abuelos, bisabuelos)
        NodoAncestro actualT = ancestrosToro.getCabeza();
        while (actualT != null) {
            RegistroAncestro ancT = actualT.getRegistro();
            RegistroAncestro ancV = ancestrosVaca.buscarPorId(ancT.getBovino().getIdArete());

            if (ancV != null) {
                // Ancestro común detectado en ambos linajes
                int n1 = ancT.getGeneracion();
                int n2 = ancV.getGeneracion();
                sumatoriaWright += Math.pow(0.5, n1 + n2 + 1);

                if (!comunes.contiene(ancT.getBovino().getIdArete())) {
                    String detalle = String.format("Compartido (Línea Toro: %s / Línea Vaca: %s)",
                            ancT.getNombreGeneracion(), ancV.getNombreGeneracion());
                    comunes.insertar(new RegistroAncestro(ancT.getBovino(), Math.min(n1, n2), detalle));
                }
            }
            actualT = actualT.getSiguiente();
        }

        double coeficientePorcentaje = Math.min(100.0, sumatoriaWright * 100.0);
        return new ResultadoConsanguinidad(toro, vaca, coeficientePorcentaje, comunes);
    }

    /**
     * Retorna una lista con todos los bovinos registrados en el grafo. O(V).
     */
    public ListaBovinos obtenerTodos() {
        ListaBovinos lista = new ListaBovinos();
        NodoGrafo actual = primerNodo;
        while (actual != null) {
            lista.insertar(actual.getBovino());
            actual = actual.getSiguienteEnGrafo();
        }
        return lista;
    }

    public int getTotalBovinos() {
        return totalBovinos;
    }

    public NodoGrafo getPrimerNodo() {
        return primerNodo;
    }
}
