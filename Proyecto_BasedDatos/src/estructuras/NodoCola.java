package estructuras;

/**
 * Nodo genérico para la Cola FIFO (First-In, First-Out).
 * 
 * Lógica:
 * Enlaza un elemento genérico con el siguiente nodo en la fila.
 * 
 * Complejidad:
 * - Espacio: O(1) por nodo.
 */
public class NodoCola<T> {
    private T dato;
    private NodoCola<T> siguiente;

    public NodoCola(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoCola<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola<T> siguiente) {
        this.siguiente = siguiente;
    }
}
