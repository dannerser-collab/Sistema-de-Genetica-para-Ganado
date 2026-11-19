package estructuras;

import modelo.Bovino;

/**
 * Estructura de Datos: Cola de Prioridad basada en un Max-Heap (Montículo de Máximos) propio.
 * Implementada 100% desde cero sobre un arreglo primitivo redimensionable manualmente.
 * 
 * Lógica:
 * Cumple la propiedad de montículo de máximos donde cada nodo padre tiene un
 * 'indiceGenetico' mayor o igual al de sus hijos.
 * - Hijo izquierdo de nodo i: 2*i + 1
 * - Hijo derecho de nodo i: 2*i + 2
 * - Progenitor de nodo i: (i - 1) / 2
 * Las inserciones realizan flotación (siftUp) y las extracciones del máximo realizan
 * hundimiento (siftDown), garantizando que el bovino con mayor índice genético
 * siempre se encuentre en la raíz (posición 0).
 * 
 * Complejidad:
 * - Inserción (offer/enqueue): O(log n)
 * - Extracción del máximo (poll/dequeue): O(log n)
 * - Consulta del máximo (peek): O(1)
 * - Espacio: O(n) donde n es el número de toros candidatos.
 */
public class ColaPrioridadGenetica {
    private Bovino[] heap;
    private int capacidad;
    private int tamanio;

    public ColaPrioridadGenetica(int capacidadInicial) {
        this.capacidad = Math.max(10, capacidadInicial);
        this.heap = new Bovino[this.capacidad];
        this.tamanio = 0;
    }

    public ColaPrioridadGenetica() {
        this(16);
    }

    /**
     * Inserta un ejemplar en la cola de prioridad en O(log n).
     */
    public void insertar(Bovino bovino) {
        if (bovino == null) return;

        // Redimensionar arreglo manualmente si se llena
        if (tamanio >= capacidad) {
            redimensionar();
        }

        heap[tamanio] = bovino;
        flotar(tamanio);
        tamanio++;
    }

    /**
     * Extrae y devuelve el bovino con el índice genético más alto en O(log n).
     */
    public Bovino extraerMaximo() {
        if (estaVacia()) return null;

        Bovino maximo = heap[0];
        heap[0] = heap[tamanio - 1];
        heap[tamanio - 1] = null;
        tamanio--;

        if (tamanio > 0) {
            hundir(0);
        }

        return maximo;
    }

    /**
     * Consulta el ejemplar con mayor mérito genético sin extraerlo en O(1).
     */
    public Bovino obtenerMaximo() {
        if (estaVacia()) return null;
        return heap[0];
    }

    private void flotar(int indice) {
        int actual = indice;
        while (actual > 0) {
            int padre = (actual - 1) / 2;
            if (heap[actual].getIndiceGenetico() > heap[padre].getIndiceGenetico()) {
                intercambiar(actual, padre);
                actual = padre;
            } else {
                break;
            }
        }
    }

    private void hundir(int indice) {
        int actual = indice;
        while (actual < tamanio) {
            int mayor = actual;
            int hijoIzq = (2 * actual) + 1;
            int hijoDer = (2 * actual) + 2;

            if (hijoIzq < tamanio && heap[hijoIzq].getIndiceGenetico() > heap[mayor].getIndiceGenetico()) {
                mayor = hijoIzq;
            }

            if (hijoDer < tamanio && heap[hijoDer].getIndiceGenetico() > heap[mayor].getIndiceGenetico()) {
                mayor = hijoDer;
            }

            if (mayor != actual) {
                intercambiar(actual, mayor);
                actual = mayor;
            } else {
                break;
            }
        }
    }

    private void intercambiar(int i, int j) {
        Bovino temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void redimensionar() {
        int nuevaCapacidad = capacidad * 2;
        Bovino[] nuevoHeap = new Bovino[nuevaCapacidad];
        for (int i = 0; i < tamanio; i++) {
            nuevoHeap[i] = heap[i];
        }
        heap = nuevoHeap;
        capacidad = nuevaCapacidad;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    public int getTamanio() {
        return tamanio;
    }
}
