package estructuras;

import modelo.Evento;

/**
 * Estructura de Datos: Lista Enlazada Simple (Singly Linked List) implementada 100% desde cero.
 * 
 * Lógica:
 * Mantiene un puntero a la 'cabeza' (primer nodo) y un puntero a la 'cola' (último nodo),
 * además de un contador entero 'tamanio'. Al mantener la referencia a la cola,
 * las inserciones al final se realizan en tiempo constante sin necesidad de recorrer la lista.
 * 
 * Complejidad:
 * - Inserción al final: O(1) tiempo gracias al puntero 'cola'.
 * - Recorrido / Impresión: O(n) tiempo, donde n es la cantidad de eventos en la lista.
 * - Búsqueda / Conteo: O(n) tiempo en el peor caso.
 * - Espacio: O(n), donde n es el número de eventos almacenados dinámicamente.
 */
public class ListaEventos {
    private NodoEvento cabeza;
    private NodoEvento cola;
    private int tamanio;

    public ListaEventos() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    /**
     * Inserta un nuevo evento al final de la lista en O(1).
     */
    public void insertar(Evento evento) {
        if (evento == null) return;
        NodoEvento nuevo = new NodoEvento(evento);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            cola = nuevo;
        }
        tamanio++;
    }

    /**
     * Retorna verdadero si la lista carece de elementos. Complejidad: O(1).
     */
    public boolean estaVacia() {
        return cabeza == null;
    }

    /**
     * Retorna la cantidad de eventos registrados. Complejidad: O(1).
     */
    public int getTamanio() {
        return tamanio;
    }

    public NodoEvento getCabeza() {
        return cabeza;
    }

    /**
     * Imprime en consola todos los eventos cronológicos en O(n).
     */
    public void imprimirEventos() {
        if (estaVacia()) {
            System.out.println("   [Sin eventos registrados en su historial]");
            return;
        }
        NodoEvento actual = cabeza;
        int index = 1;
        while (actual != null) {
            System.out.println("   " + index + ". " + actual.getEvento().toString());
            actual = actual.getSiguiente();
            index++;
        }
    }
}
