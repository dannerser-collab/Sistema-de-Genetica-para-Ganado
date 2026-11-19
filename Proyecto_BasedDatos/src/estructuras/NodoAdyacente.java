package estructuras;

/**
 * Nodo de arista para la Lista de Adyacencia del Grafo de Pedigrí.
 * 
 * Lógica:
 * Representa una arista dirigida hacia un NodoGrafo adyacente (por ejemplo, descendientes),
 * conteniendo el tipo de relación y la referencia al siguiente nodo de la lista de adyacencia.
 * 
 * Complejidad:
 * - Espacio: O(1) por arista.
 */
public class NodoAdyacente {
    private NodoGrafo destino;
    private String tipoRelacion; // Ej. "HIJO", "HIJA"
    private NodoAdyacente siguiente;

    public NodoAdyacente(NodoGrafo destino, String tipoRelacion) {
        this.destino = destino;
        this.tipoRelacion = tipoRelacion;
        this.siguiente = null;
    }

    public NodoGrafo getDestino() {
        return destino;
    }

    public String getTipoRelacion() {
        return tipoRelacion;
    }

    public NodoAdyacente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoAdyacente siguiente) {
        this.siguiente = siguiente;
    }
}
