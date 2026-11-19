package estructuras;

/**
 * Estructura de Datos: Cola Enlazada FIFO (Queue) implementada 100% desde cero.
 * Utilizada para los algoritmos de recorrido en anchura (BFS) del Grafo de Pedigrí.
 * 
 * Lógica:
 * Mantiene referencias al frente ('frente') para desencoalar y al final ('finalCola')
 * para encolar nuevos elementos. Cumple la disciplina estricta First-In, First-Out.
 * 
 * Complejidad:
 * - Encolar (enqueue): O(1) tiempo.
 * - Desencolar (dequeue): O(1) tiempo.
 * - Consultar frente (peek): O(1) tiempo.
 * - Espacio: O(k) donde k es la cantidad de elementos en la cola.
 */
public class ColaSimple<T> {
    private NodoCola<T> frente;
    private NodoCola<T> finalCola;
    private int tamanio;

    public ColaSimple() {
        this.frente = null;
        this.finalCola = null;
        this.tamanio = 0;
    }

    /**
     * Inserta un elemento al final de la cola en O(1).
     */
    public void encolar(T dato) {
        if (dato == null) return;
        NodoCola<T> nuevo = new NodoCola<>(dato);
        if (finalCola == null) {
            frente = nuevo;
            finalCola = nuevo;
        } else {
            finalCola.setSiguiente(nuevo);
            finalCola = nuevo;
        }
        tamanio++;
    }

    /**
     * Remueve y retorna el elemento al frente de la cola en O(1).
     */
    public T desencolar() {
        if (estaVacia()) return null;
        T dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            finalCola = null;
        }
        tamanio--;
        return dato;
    }

    /**
     * Consulta el elemento al frente sin removerlo en O(1).
     */
    public T verFrente() {
        if (estaVacia()) return null;
        return frente.getDato();
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}
