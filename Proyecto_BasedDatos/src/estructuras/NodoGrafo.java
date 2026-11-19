package estructuras;

import modelo.Bovino;

/**
 * Vértice del Grafo Dirigido Acíclico (DAG) de Pedigrí.
 * 
 * Lógica:
 * Representa a un individuo dentro de la red genealógica. Dispone de enlaces ascendentes directos
 * a sus progenitores ('padre' y 'madre') para navegación de ancestros en O(1), y una lista de
 * adyacencia de descendientes ('hijos') para navegación hacia abajo.
 * Además, cuenta con el puntero 'siguienteEnGrafo' para iterar sobre todos los vértices sin colecciones externas.
 * 
 * Complejidad:
 * - Acceso a progenitores directos: O(1).
 * - Espacio: O(1 + deg(v)) por vértice.
 */
public class NodoGrafo {
    private Bovino bovino;
    private NodoGrafo padre;
    private NodoGrafo madre;
    private ListaAdyacencia descendientes;
    private NodoGrafo siguienteEnGrafo;

    public NodoGrafo(Bovino bovino) {
        this.bovino = bovino;
        this.padre = null;
        this.madre = null;
        this.descendientes = new ListaAdyacencia();
        this.siguienteEnGrafo = null;
    }

    public Bovino getBovino() {
        return bovino;
    }

    public NodoGrafo getPadre() {
        return padre;
    }

    public void setPadre(NodoGrafo padre) {
        this.padre = padre;
    }

    public NodoGrafo getMadre() {
        return madre;
    }

    public void setMadre(NodoGrafo madre) {
        this.madre = madre;
    }

    public ListaAdyacencia getDescendientes() {
        return descendientes;
    }

    public void agregarDescendiente(NodoGrafo hijo, String tipoRelacion) {
        descendientes.agregar(hijo, tipoRelacion);
    }

    public NodoGrafo getSiguienteEnGrafo() {
        return siguienteEnGrafo;
    }

    public void setSiguienteEnGrafo(NodoGrafo siguienteEnGrafo) {
        this.siguienteEnGrafo = siguienteEnGrafo;
    }
}
