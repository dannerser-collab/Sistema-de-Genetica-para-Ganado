package estructuras;

import modelo.Bovino;

/**
 * Representa el registro de un ancestro encontrado durante la exploración
 * genealógica, guardando su distancia generacional y parentesco.
 */
public class RegistroAncestro {
    private Bovino bovino;
    private int generacion; // 1 = Progenitor, 2 = Abuelo, 3 = Bisabuelo
    private String descripcionParentesco;

    public RegistroAncestro(Bovino bovino, int generacion, String descripcionParentesco) {
        this.bovino = bovino;
        this.generacion = generacion;
        this.descripcionParentesco = descripcionParentesco;
    }

    public Bovino getBovino() {
        return bovino;
    }

    public int getGeneracion() {
        return generacion;
    }

    public String getDescripcionParentesco() {
        return descripcionParentesco;
    }

    public String getNombreGeneracion() {
        switch (generacion) {
            case 1: return "1ª Gen (Padre/Madre)";
            case 2: return "2ª Gen (Abuelo/Abuela)";
            case 3: return "3ª Gen (Bisabuelo/Bisabuela)";
            default: return generacion + "ª Gen";
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %-14s | %-18s | %s",
                bovino.getIdArete(), bovino.getNombre(), getNombreGeneracion(), descripcionParentesco);
    }
}
