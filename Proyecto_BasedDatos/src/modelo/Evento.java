package modelo;

/**
 * Representa un evento individual sanitario, productivo o reproductivo
 * dentro del historial clínico de un ejemplar bovino.
 */
public class Evento {
    private String fecha;
    private TipoEvento tipo;
    private String descripcion;
    private double valorNumerico; // Por ejemplo peso en kg, dosis en ml, o 0 si no aplica

    public Evento(String fecha, TipoEvento tipo, String descripcion, double valorNumerico) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.valorNumerico = valorNumerico;
    }

    public Evento(String fecha, TipoEvento tipo, String descripcion) {
        this(fecha, tipo, descripcion, 0.0);
    }

    public String getFecha() {
        return fecha;
    }

    public TipoEvento getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getValorNumerico() {
        return valorNumerico;
    }

    @Override
    public String toString() {
        if (valorNumerico > 0) {
            return String.format("[%s] %-18s | %s (Valor: %.2f)", fecha, tipo, descripcion, valorNumerico);
        }
        return String.format("[%s] %-18s | %s", fecha, tipo, descripcion);
    }
}
