package estructuras;

import modelo.Bovino;

/**
 * Estructura de Datos: Árbol Binario Genealógico (ArbolAncestros) implementado 100% desde cero.
 * 
 * Lógica:
 * Estructura de árbol binario estricta donde cada nodo representa un bovino,
 * su puntero izquierdo apunta a su progenitor paterno (nodoPadre), y su puntero derecho
 * apunta a su progenitor materno (nodoMadre).
 * Permite visualizar la genealogía ascendente en consola en formato jerárquico ASCII
 * y evaluar recursivamente la pureza racial transmitida por herencia mendeliana (50% padre, 50% madre).
 * 
 * Complejidad:
 * - Construcción del árbol (hasta profundidad h=3): O(2^h) nodos = O(1) tiempo acotado (máximo 15 nodos).
 * - Recorrido / Visualización: O(N) donde N es el número de nodos del árbol (N <= 15).
 * - Cálculo de Pureza Racial: O(N) recorrido en postorden / DFS recursivo.
 * - Espacio: O(h) memoria en pila de llamadas recursivas, donde h <= 3.
 */
public class ArbolAncestros {
    private NodoArbolGenealogico raiz;
    private int profundidadMaxima;

    public ArbolAncestros() {
        this.raiz = null;
        this.profundidadMaxima = 3;
    }

    /**
     * Construye el árbol binario genealógico de un bovino específico hasta una profundidad dada.
     */
    public void construir(Bovino bovino, GrafoPedigri grafo, int profundidadMaxima) {
        this.profundidadMaxima = profundidadMaxima;
        if (bovino == null || grafo == null) {
            this.raiz = null;
            return;
        }
        this.raiz = construirRecursivo(bovino.getIdArete(), grafo, 0, profundidadMaxima, "INDIVIDUO");
    }

    private NodoArbolGenealogico construirRecursivo(String idArete, GrafoPedigri grafo, int nivelActual,
                                                   int maxNivel, String rol) {
        if (idArete == null || nivelActual > maxNivel) {
            return null;
        }

        NodoGrafo vertice = grafo.buscarNodo(idArete);
        if (vertice == null) {
            return null;
        }

        Bovino bovino = vertice.getBovino();
        NodoArbolGenealogico nodo = new NodoArbolGenealogico(bovino, nivelActual, rol);

        if (nivelActual < maxNivel) {
            String rolPadre = (nivelActual == 0) ? "PADRE" : (rol.contains("PADRE") ? "ABUELO PAT." : "ABUELO MAT.");
            if (nivelActual == 2) rolPadre = "BISABUELO (" + rol + ")";

            String rolMadre = (nivelActual == 0) ? "MADRE" : (rol.contains("PADRE") ? "ABUELA PAT." : "ABUELA MAT.");
            if (nivelActual == 2) rolMadre = "BISABUELA (" + rol + ")";

            if (bovino.getIdPadre() != null) {
                nodo.setNodoPadre(construirRecursivo(bovino.getIdPadre(), grafo, nivelActual + 1, maxNivel, rolPadre));
            }
            if (bovino.getIdMadre() != null) {
                nodo.setNodoMadre(construirRecursivo(bovino.getIdMadre(), grafo, nivelActual + 1, maxNivel, rolMadre));
            }
        }

        return nodo;
    }

    /**
     * Visualiza en consola el árbol binario genealógico utilizando formato jerárquico estructurado.
     */
    public void visualizar() {
        if (raiz == null) {
            System.out.println("   [Árbol genealógico no disponible]");
            return;
        }
        System.out.println("==========================================================================");
        System.out.printf("   ÁRBOL BINARIO GENEALÓGICO (Hasta %d Generaciones)\n", profundidadMaxima);
        System.out.printf("   Ejemplar Raíz: [%s] %s (Raza: %s)\n",
                raiz.getBovino().getIdArete(), raiz.getBovino().getNombre(), raiz.getBovino().getRaza());
        System.out.println("==========================================================================");
        imprimirNodo(raiz, "", true, true);
        System.out.println("==========================================================================");
    }

    private void imprimirNodo(NodoArbolGenealogico nodo, String prefijo, boolean esUltimo, boolean esRaiz) {
        if (nodo == null) return;

        String conector = esRaiz ? "* " : (esUltimo ? "\\-- " : "|-- ");
        Bovino b = nodo.getBovino();
        String infoBovino = String.format("[%s] %s | %s | %s | I.G: %.1f",
                b.getIdArete(), b.getNombre(), b.getRaza(), (b.esMacho() ? "Macho" : "Hembra"), b.getIndiceGenetico());

        System.out.println(prefijo + conector + "<" + nodo.getRol() + "> " + infoBovino);

        String nuevoPrefijo = prefijo + (esRaiz ? "   " : (esUltimo ? "    " : "|   "));

        // En un árbol binario genealógico:
        // Izquierda = Padre, Derecha = Madre
        boolean tienePadre = (nodo.getNodoPadre() != null);
        boolean tieneMadre = (nodo.getNodoMadre() != null);

        if (tienePadre || tieneMadre) {
            if (tienePadre) {
                imprimirNodo(nodo.getNodoPadre(), nuevoPrefijo, !tieneMadre, false);
            } else {
                System.out.println(nuevoPrefijo + (tieneMadre ? "|-- " : "\\-- ") + "<PADRE> [No registrado / Fundador]");
            }

            if (tieneMadre) {
                imprimirNodo(nodo.getNodoMadre(), nuevoPrefijo, true, false);
            } else {
                System.out.println(nuevoPrefijo + "\\-- <MADRE> [No registrada / Fundadora]");
            }
        }
    }

    /**
     * Calcula el porcentaje estimado de pureza racial para la raza propia del bovino raíz.
     */
    public double calcularPurezaRacial() {
        if (raiz == null || raiz.getBovino() == null) return 0.0;
        return calcularPurezaRacial(raiz.getBovino().getRaza());
    }

    /**
     * Calcula el porcentaje estimado de pureza racial para una raza objetivo específica.
     * 
     * Lógica Genética:
     * - Cada individuo hereda el 50% de la carga genética de su padre y el 50% de su madre.
     * - Si un ancestro es hoja (fundador sin padres registrados en el pedigrí):
     *   * Si su raza coincide con la raza objetivo: aporta 100% de pureza en esa línea.
     *   * Si es de otra raza: aporta 0% a esa raza.
     * - Si tiene padres registrados: Pureza = 0.5 * pureza(Padre) + 0.5 * pureza(Madre).
     * 
     * Complejidad: O(N) donde N es el número de nodos del árbol (<= 15).
     */
    public double calcularPurezaRacial(String razaObjetivo) {
        if (raiz == null || razaObjetivo == null || razaObjetivo.trim().isEmpty()) {
            return 0.0;
        }
        return calcularPurezaNodo(raiz, razaObjetivo.trim().toUpperCase());
    }

    private double calcularPurezaNodo(NodoArbolGenealogico nodo, String razaObjetivo) {
        if (nodo == null || nodo.getBovino() == null) {
            return 0.0;
        }

        Bovino b = nodo.getBovino();
        boolean mismaRaza = b.getRaza().equalsIgnoreCase(razaObjetivo);

        // Caso base: Nodo sin progenitores en el árbol (Fundador / Hoja)
        if (nodo.getNodoPadre() == null && nodo.getNodoMadre() == null) {
            return mismaRaza ? 100.0 : 0.0;
        }

        // Si tiene progenitores registrados: herencia 50% paterna y 50% materna
        double aportePadre;
        if (nodo.getNodoPadre() != null) {
            aportePadre = calcularPurezaNodo(nodo.getNodoPadre(), razaObjetivo);
        } else {
            // Padre no registrado: se asume pureza según la raza declarada del ejemplar
            aportePadre = mismaRaza ? 100.0 : 0.0;
        }

        double aporteMadre;
        if (nodo.getNodoMadre() != null) {
            aporteMadre = calcularPurezaNodo(nodo.getNodoMadre(), razaObjetivo);
        } else {
            // Madre no registrada: se asume pureza según la raza declarada del ejemplar
            aporteMadre = mismaRaza ? 100.0 : 0.0;
        }

        return (0.5 * aportePadre) + (0.5 * aporteMadre);
    }

    public NodoArbolGenealogico getRaiz() {
        return raiz;
    }
}
