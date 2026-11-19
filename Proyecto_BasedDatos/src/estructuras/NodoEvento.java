package estructuras;

import modelo.Evento;

/**
 * Nodo de la Lista Enlazada Simple para el registro de eventos individuales.
 * 
 * Lógica:
 * Cada nodo contiene una carga útil (instancia de Evento) y un puntero de referencia
 * 'siguiente' hacia el próximo nodo en la secuencia enlazada.
 * 
 * Complejidad:
 * - Espacial: O(1) por nodo.
 */
public class NodoEvento {
    private Evento evento;
    private NodoEvento siguiente;

    public NodoEvento(Evento evento) {
        this.evento = evento;
        this.siguiente = null;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public NodoEvento getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoEvento siguiente) {
        this.siguiente = siguiente;
    }
}
