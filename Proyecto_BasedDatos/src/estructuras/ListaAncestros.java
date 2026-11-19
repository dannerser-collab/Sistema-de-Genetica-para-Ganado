package estructuras;

/**
 * Estructura de Datos: Lista Enlazada Simple de RegistroAncestro (100% personalizada).
 * Permite almacenar y consultar los ancestros recorridos en BFS/DFS.
 * 
 * Lógica:
 * Lista con puntero al inicio y final. Permite verificar pertenencia (contiene)
 * para detectar ancestros compartidos entre dos linajes en O(k), donde k es la cantidad
 * máxima de ancestros en 3 generaciones (a lo sumo 2 + 4 + 8 = 14 nodos).
 * 
 * Complejidad:
 * - Inserción: O(1)
 * - Búsqueda: O(k) donde k <= 14 (tiempo prácticamente constante en la práctica).
 * - Espacio: O(k).
 */
public class ListaAncestros {
    private NodoAncestro cabeza;
    private NodoAncestro cola;
    private int tamanio;

    public ListaAncestros() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    public void insertar(RegistroAncestro registro) {
        if (registro == null) return;
        NodoAncestro nuevo = new NodoAncestro(registro);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            cola = nuevo;
        }
        tamanio++;
    }

    public RegistroAncestro buscarPorId(String idArete) {
        if (idArete == null) return null;
        String idBuscado = idArete.trim().toUpperCase();
        NodoAncestro actual = cabeza;
        while (actual != null) {
            if (actual.getRegistro().getBovino().getIdArete().equalsIgnoreCase(idBuscado)) {
                return actual.getRegistro();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public boolean contiene(String idArete) {
        return buscarPorId(idArete) != null;
    }

    public NodoAncestro getCabeza() {
        return cabeza;
    }

    public int getTamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }
}
