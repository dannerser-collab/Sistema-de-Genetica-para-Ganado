package estructuras;

/**
 * Estructura de Datos: Lista de Adyacencia implementada 100% desde cero.
 * Mantiene las conexiones salientes (hacia descendientes) de un vértice en el grafo.
 * 
 * Lógica:
 * Lista enlazada simple de aristas que conectan un nodo con sus hijos directos.
 * 
 * Complejidad:
 * - Inserción de arista: O(1) al frente de la lista.
 * - Recorrido de aristas: O(grado de salida) = O(deg+(u)).
 * - Espacio: O(E) total en el grafo.
 */
public class ListaAdyacencia {
    private NodoAdyacente cabeza;
    private int cantidad;

    public ListaAdyacencia() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    public void agregar(NodoGrafo destino, String tipoRelacion) {
        if (destino == null) return;
        NodoAdyacente nuevo = new NodoAdyacente(destino, tipoRelacion);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
        cantidad++;
    }

    public NodoAdyacente getCabeza() {
        return cabeza;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }
}
