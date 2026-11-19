package estructuras;

/**
 * Nodo para la Lista Enlazada Simple de RegistroAncestro.
 * 
 * Lógica:
 * Enlaza un RegistroAncestro con el siguiente en la secuencia.
 * 
 * Complejidad:
 * - Espacio: O(1) por nodo.
 */
public class NodoAncestro {
    private RegistroAncestro registro;
    private NodoAncestro siguiente;

    public NodoAncestro(RegistroAncestro registro) {
        this.registro = registro;
        this.siguiente = null;
    }

    public RegistroAncestro getRegistro() {
        return registro;
    }

    public void setRegistro(RegistroAncestro registro) {
        this.registro = registro;
    }

    public NodoAncestro getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoAncestro siguiente) {
        this.siguiente = siguiente;
    }
}
