package modelo;

import estructuras.ListaAncestros;
import estructuras.NodoAncestro;

/**
 * Encapsula el veredicto del análisis genético y consanguinidad
 * entre dos ejemplares bovinos tras el recorrido del Grafo de Pedigrí.
 */
public class ResultadoConsanguinidad {
    private Bovino toro;
    private Bovino vaca;
    private double coeficienteConsanguinidad; // En porcentaje (0.0% a 100.0%)
    private boolean alertaConsanguinidad;
    private String nivelRiesgo;
    private ListaAncestros ancestrosComunes;

    public ResultadoConsanguinidad(Bovino toro, Bovino vaca, double coeficienteConsanguinidad,
                                   ListaAncestros ancestrosComunes) {
        this.toro = toro;
        this.vaca = vaca;
        this.coeficienteConsanguinidad = coeficienteConsanguinidad;
        this.ancestrosComunes = ancestrosComunes;
        this.alertaConsanguinidad = (coeficienteConsanguinidad > 0.0001 || !ancestrosComunes.estaVacia());
        determinarNivelRiesgo();
    }

    private void determinarNivelRiesgo() {
        if (!alertaConsanguinidad) {
            this.nivelRiesgo = "SEGURO (0.0% - Sin consanguinidad detectada en 3 generaciones)";
        } else if (coeficienteConsanguinidad >= 25.0) {
            this.nivelRiesgo = "CRÍTICO - Cruce Prohibido (Padre-Hija o Hermanos completos)";
        } else if (coeficienteConsanguinidad >= 12.5) {
            this.nivelRiesgo = "ALTO RIESGO - Cruce No Recomendado (Medio Hermanos o Abuelo-Nieta)";
        } else if (coeficienteConsanguinidad >= 6.25) {
            this.nivelRiesgo = "MODERADO - Precaución Genética (Tío-Sobrina o Bisabuelo-Bisnieta)";
        } else {
            this.nivelRiesgo = "BAJO - Parentesco lejano (Primos segundos o bisabuelos compartidos)";
        }
    }

    public Bovino getToro() {
        return toro;
    }

    public Bovino getVaca() {
        return vaca;
    }

    public double getCoeficienteConsanguinidad() {
        return coeficienteConsanguinidad;
    }

    public boolean isAlertaConsanguinidad() {
        return alertaConsanguinidad;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public ListaAncestros getAncestrosComunes() {
        return ancestrosComunes;
    }

    public void imprimirReporte() {
        System.out.println("==========================================================================");
        System.out.println("                 REPORTE DE COMPATIBILIDAD GENÉTICA                       ");
        System.out.println("==========================================================================");
        System.out.printf("  Toro Evaluado : [%s] %s (%s, I.G: %.1f)\n", toro.getIdArete(), toro.getNombre(), toro.getRaza(), toro.getIndiceGenetico());
        System.out.printf("  Vaca Evaluada : [%s] %s (%s, I.G: %.1f)\n", vaca.getIdArete(), vaca.getNombre(), vaca.getRaza(), vaca.getIndiceGenetico());
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("  Coeficiente de Consanguinidad (F) : %.2f%%\n", coeficienteConsanguinidad);
        System.out.printf("  Nivel de Riesgo                   : %s\n", nivelRiesgo);
        System.out.printf("  ¿Alerta de Consanguinidad Activa? : %s\n", alertaConsanguinidad ? "SÍ (PELIGRO DE DEPRESIÓN CONSANGUÍNEA)" : "NO (CRUCE RECOMENDADO)");
        System.out.println("--------------------------------------------------------------------------");

        if (alertaConsanguinidad && !ancestrosComunes.estaVacia()) {
            System.out.println("  Ancestros comunes compartidos detectados en las últimas 3 generaciones:");
            NodoAncestro actual = ancestrosComunes.getCabeza();
            int idx = 1;
            while (actual != null) {
                System.out.printf("   %d. %s\n", idx++, actual.getRegistro().toString());
                actual = actual.getSiguiente();
            }
        } else {
            System.out.println("  [OK] No se detectaron ancestros comunes entre ambos ejemplares hasta 3 generaciones.");
            System.out.println("  [OK] El cruce garantiza vigor hibrido y maximiza la ganancia genetica.");
        }
        System.out.println("==========================================================================");
    }
}
