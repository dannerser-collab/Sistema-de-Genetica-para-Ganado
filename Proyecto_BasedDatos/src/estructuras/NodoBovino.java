package estructuras;

import modelo.Bovino;

/**
 * Nodo de la Lista Enlazada de Bovinos.
 * 
 * Lógica:
 * Almacena la referencia a una entidad Bovino y el puntero al siguiente nodo.
 * 
 * Complejidad:
 * - Espacio: O(1) por nodo.
 */
public class NodoBovino {
    private Bovino bovino;
    private NodoBovino siguiente;

    public NodoBovino(Bovino bovino) {
        this.bovino = bovino;
        this.siguiente = null;
    }

    public Bovino getBovino() {
        return bovino;
    }

    public void setBovino(Bovino bovino) {
        this.bovino = bovino;
    }

    public NodoBovino getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoBovino siguiente) {
        this.siguiente = siguiente;
    }
}
