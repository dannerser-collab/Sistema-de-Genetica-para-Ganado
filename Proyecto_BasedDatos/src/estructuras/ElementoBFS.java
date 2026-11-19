package estructuras;

/**
 * Estructura auxiliar para el recorrido en anchura (BFS) del Grafo de Pedigrí.
 * 
 * Lógica:
 * Asocia un nodo del grafo con su distancia generacional y la descripción de la rama genealógica.
 * 
 * Complejidad:
 * - Espacio: O(1).
 */
public class ElementoBFS {
    private NodoGrafo nodo;
    private int generacion;
    private String rama;

    public ElementoBFS(NodoGrafo nodo, int generacion, String rama) {
        this.nodo = nodo;
        this.generacion = generacion;
        this.rama = rama;
    }

    public NodoGrafo getNodo() {
        return nodo;
    }

    public int getGeneracion() {
        return generacion;
    }

    public String getRama() {
        return rama;
    }
}
