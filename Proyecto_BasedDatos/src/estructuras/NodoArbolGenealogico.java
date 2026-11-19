package estructuras;

import modelo.Bovino;

/**
 * Nodo del Árbol Binario Genealógico (ArbolAncestros).
 * 
 * Lógica:
 * Implementa una estructura en árbol binario donde:
 * - Subárbol Izquierdo (nodoPadre): Linaje paterno del bovino.
 * - Subárbol Derecho (nodoMadre): Linaje materno del bovino.
 * 
 * Complejidad:
 * - Espacio: O(1) por nodo.
 */
public class NodoArbolGenealogico {
    private Bovino bovino;
    private int nivel; // 0 = Raíz (individuo), 1 = Padres, 2 = Abuelos, 3 = Bisabuelos
    private String rol; // Ej. "INDIVIDUO", "PADRE", "MADRE", "ABUELO PATERNO", etc.
    private NodoArbolGenealogico nodoPadre; // Hijo izquierdo en el árbol binario
    private NodoArbolGenealogico nodoMadre; // Hijo derecho en el árbol binario

    public NodoArbolGenealogico(Bovino bovino, int nivel, String rol) {
        this.bovino = bovino;
        this.nivel = nivel;
        this.rol = rol;
        this.nodoPadre = null;
        this.nodoMadre = null;
    }

    public Bovino getBovino() {
        return bovino;
    }

    public int getNivel() {
        return nivel;
    }

    public String getRol() {
        return rol;
    }

    public NodoArbolGenealogico getNodoPadre() {
        return nodoPadre;
    }

    public void setNodoPadre(NodoArbolGenealogico nodoPadre) {
        this.nodoPadre = nodoPadre;
    }

    public NodoArbolGenealogico getNodoMadre() {
        return nodoMadre;
    }

    public void setNodoMadre(NodoArbolGenealogico nodoMadre) {
        this.nodoMadre = nodoMadre;
    }

    public boolean esHoja() {
        return nodoPadre == null && nodoMadre == null;
    }
}
