package modelo;

import estructuras.ListaEventos;

/**
 * Modelo de Dominio: Bovino
 * Representa a un ejemplar bovino en el hato ganadero, conteniendo sus datos biométricos,
 * genealógicos y su historial clínico/productivo enlazado.
 */
public class Bovino {
    private String idArete;
    private String nombre;
    private String raza;
    private char sexo; // 'M' = Macho/Toro, 'H' = Hembra/Vaca
    private double pesoKg;
    private double indiceGenetico; // Escala de 1.0 a 100.0
    private String idPadre; // ID del padre (null o "" si es fundador)
    private String idMadre; // ID de la madre (null o "" si es fundadora)
    private ListaEventos historial; // Lista enlazada de eventos propios

    public Bovino(String idArete, String nombre, String raza, char sexo, double pesoKg,
                  double indiceGenetico, String idPadre, String idMadre) {
        this.idArete = (idArete != null) ? idArete.trim().toUpperCase() : "";
        this.nombre = (nombre != null) ? nombre.trim() : "";
        this.raza = (raza != null) ? raza.trim() : "";
        this.sexo = Character.toUpperCase(sexo);
        this.pesoKg = pesoKg;
        this.indiceGenetico = Math.max(1.0, Math.min(100.0, indiceGenetico));
        this.idPadre = (idPadre != null && !idPadre.trim().isEmpty() && !idPadre.trim().equalsIgnoreCase("NONE"))
                ? idPadre.trim().toUpperCase() : null;
        this.idMadre = (idMadre != null && !idMadre.trim().isEmpty() && !idMadre.trim().equalsIgnoreCase("NONE"))
                ? idMadre.trim().toUpperCase() : null;
        this.historial = new ListaEventos();
    }

    public void agregarEvento(Evento evento) {
        if (evento != null) {
            historial.insertar(evento);
        }
    }

    public String getIdArete() {
        return idArete;
    }

    public void setIdArete(String idArete) {
        this.idArete = idArete;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = Character.toUpperCase(sexo);
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public double getIndiceGenetico() {
        return indiceGenetico;
    }

    public void setIndiceGenetico(double indiceGenetico) {
        this.indiceGenetico = Math.max(1.0, Math.min(100.0, indiceGenetico));
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    public String getIdMadre() {
        return idMadre;
    }

    public void setIdMadre(String idMadre) {
        this.idMadre = idMadre;
    }

    public ListaEventos getHistorial() {
        return historial;
    }

    public boolean esMacho() {
        return sexo == 'M';
    }

    public boolean esHembra() {
        return sexo == 'H';
    }

    @Override
    public String toString() {
        String padreStr = (idPadre != null) ? idPadre : "N/D";
        String madreStr = (idMadre != null) ? idMadre : "N/D";
        String sexoStr = (sexo == 'M') ? "Toro (M)" : "Vaca (H)";
        return String.format("[%s] %-14s | %-10s | %-8s | %6.1f kg | I.G: %5.1f/100 | P: %-6s | M: %-6s",
                idArete, nombre, raza, sexoStr, pesoKg, indiceGenetico, padreStr, madreStr);
    }
}
