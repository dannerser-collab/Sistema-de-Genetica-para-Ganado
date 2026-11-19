package estructuras;

import modelo.Bovino;

/**
 * Estructura de Datos: Lista Enlazada Simple de Bovinos (100% personalizada).
 * Permite manejar colecciones dinámicas de ejemplares sin utilizar java.util.
 * 
 * Lógica:
 * Punteros a 'cabeza' y 'cola' con contador de elementos. Las inserciones son O(1)
 * al final. Las búsquedas por ID o índice son lineales O(n).
 * 
 * Complejidad:
 * - Inserción al final: O(1)
 * - Búsqueda por ID / contiene: O(n)
 * - Obtención por índice: O(n)
 * - Espacio: O(n)
 */
public class ListaBovinos {
    private NodoBovino cabeza;
    private NodoBovino cola;
    private int tamanio;

    public ListaBovinos() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    public void insertar(Bovino bovino) {
        if (bovino == null) return;
        NodoBovino nuevo = new NodoBovino(bovino);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            cola = nuevo;
        }
        tamanio++;
    }

    public Bovino buscarPorId(String idArete) {
        if (idArete == null) return null;
        String idBuscado = idArete.trim().toUpperCase();
        NodoBovino actual = cabeza;
        while (actual != null) {
            if (actual.getBovino().getIdArete().equalsIgnoreCase(idBuscado)) {
                return actual.getBovino();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public boolean contiene(String idArete) {
        return buscarPorId(idArete) != null;
    }

    public Bovino obtener(int indice) {
        if (indice < 0 || indice >= tamanio) return null;
        NodoBovino actual = cabeza;
        int i = 0;
        while (actual != null) {
            if (i == indice) return actual.getBovino();
            actual = actual.getSiguiente();
            i++;
        }
        return null;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    public NodoBovino getCabeza() {
        return cabeza;
    }
}
