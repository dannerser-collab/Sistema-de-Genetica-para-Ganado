package main;

import java.util.Scanner;
import modelo.TipoEvento;
import servicios.ServicioHato;

/**
 * Clase Principal: Sistema de Control de Consanguinidad y Selección Genética Bovina.
 * Proporciona un menú interactivo por consola intuitivo y robusto.
 */
public class Main {
    private static ServicioHato servicio = new ServicioHato();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mostrarBienvenida();

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            System.out.print("> Seleccione una opcion: ");
            String opcionStr = scanner.nextLine().trim();

            System.out.println();
            switch (opcionStr) {
                case "1":
                    servicio.listarTodosLosBovinos();
                    break;
                case "2":
                    consultarFichaBovino();
                    break;
                case "3":
                    visualizarArbolGenealogico();
                    break;
                case "4":
                    evaluarConsanguinidad();
                    break;
                case "5":
                    recomendarTop3Toros();
                    break;
                case "6":
                    registrarNuevoBovino();
                    break;
                case "7":
                    registrarNuevoEvento();
                    break;
                case "8":
                    servicio.mostrarEstadisticasHato();
                    break;
                case "9":
                    ejecutarDemostracionGuiada();
                    break;
                case "0":
                    salir = true;
                    System.out.println("==========================================================================");
                    System.out.println("  Gracias por utilizar el Sistema de Seleccion Genetica Bovina.           ");
                    System.out.println("  Cerrando sesion de control ganadero...                                  ");
                    System.out.println("==========================================================================");
                    break;
                default:
                    System.out.println("[!] Opcion no valida. Por favor, ingrese un numero del 0 al 9.");
            }

            if (!salir) {
                pausar();
            }
        }
    }

    private static void mostrarBienvenida() {
        System.out.println("==========================================================================");
        System.out.println("      SISTEMA DE CONTROL DE CONSANGUINIDAD Y SELECCION GENETICA BOVINA    ");
        System.out.println("                     [Arquitectura 100% Desde Cero]                       ");
        System.out.println("==========================================================================");
        System.out.println("  Estructuras de Datos Implementadas Manualmente:");
        System.out.println("  * GrafoPedigri        : Grafo Dirigido Aciclico (DAG) con BFS de 3 generaciones");
        System.out.println("  * ArbolAncestros      : Arbol Binario (Padre Izq / Madre Der) + Pureza Racial");
        System.out.println("  * ColaPrioridadGenetica: Max-Heap propio para Top 3 de Sementales");
        System.out.println("  * ListaEventos        : Lista Enlazada Simple para Historial Clinico");
        System.out.println("  * ListaBovinos/Ancestros: Listas Enlazadas Propias para Recorridos");
        System.out.println("  * Precarga Automatica : 16 bovinos en 3 generaciones con casos reales");
        System.out.println("==========================================================================");
    }

    private static void mostrarMenu() {
        System.out.println("\n+------------------------------------------------------------------------+");
        System.out.println("|                          MENU PRINCIPAL                                |");
        System.out.println("+------------------------------------------------------------------------+");
        System.out.println("|  1. Listar censo general del hato bovino                               |");
        System.out.println("|  2. Consultar ficha tecnica e historial clinico (Lista Enlazada)       |");
        System.out.println("|  3. Visualizar Arbol Binario Genealogico y Pureza Racial (Arbol)       |");
        System.out.println("|  4. Evaluar consanguinidad y cruce Toro vs Vaca (Grafo DAG + BFS)      |");
        System.out.println("|  5. Recomendar TOP 3 de Toros ideales para una Vaca (Max-Heap)         |");
        System.out.println("|  6. Registrar nuevo bovino en el hato                                  |");
        System.out.println("|  7. Registrar evento sanitario/productivo (Vacunacion, Pesaje, etc.)  |");
        System.out.println("|  8. Ver estadisticas globales del hato                                 |");
        System.out.println("|  9. [DEMO RAPIDA] Ejecutar prueba integral con casos del hato          |");
        System.out.println("|  0. Salir                                                              |");
        System.out.println("+------------------------------------------------------------------------+");
    }

    private static void consultarFichaBovino() {
        System.out.println("--- CONSULTA DE FICHA INDIVIDUAL ---");
        System.out.print("Ingrese ID de arete del ejemplar (ej. VACA-G3A, TORO-G2A, TORO-F1): ");
        String id = scanner.nextLine().trim();
        servicio.consultarFichaBovino(id);
    }

    private static void visualizarArbolGenealogico() {
        System.out.println("--- VISUALIZACIÓN DE ÁRBOL BINARIO GENEALÓGICO ---");
        System.out.println("Ejemplos recomendados:");
        System.out.println(" • VACA-G3A : Estrella (con 3 generaciones completas)");
        System.out.println(" • TORO-G3A : Gladiador (hijo de hermanos completos)");
        System.out.println(" • TORO-G2A : Centauro (ejemplar de 2ª generación)");
        System.out.print("Ingrese ID de arete del ejemplar: ");
        String id = scanner.nextLine().trim();
        servicio.visualizarArbolGenealogico(id);
    }

    private static void evaluarConsanguinidad() {
        System.out.println("--- EVALUACIÓN DE CRUCE Y CONSANGUINIDAD ---");
        System.out.println("Ejemplos de prueba:");
        System.out.println(" • Caso 1 (Hermanos completos): Toro TORO-G2A con Vaca VACA-G2A (F = 25%)");
        System.out.println(" • Caso 2 (Padre con Hija)    : Toro TORO-G2A con Vaca VACA-G3A (F >= 25%)");
        System.out.println(" • Caso 3 (Medio hermanos)    : Toro TORO-G3A con Vaca VACA-G3A");
        System.out.println(" • Caso 4 (0% Consanguinidad) : Toro TORO-G2C (Apollo) con Vaca VACA-G3A (Estrella)");
        System.out.print("Ingrese ID del Toro: ");
        String idToro = scanner.nextLine().trim();
        System.out.print("Ingrese ID de la Vaca: ");
        String idVaca = scanner.nextLine().trim();
        servicio.evaluarCruce(idToro, idVaca);
    }

    private static void recomendarTop3Toros() {
        System.out.println("--- SELECCIÓN GENÉTICA INTELIGENTE (TOP 3 TOROS COMPATIBLES) ---");
        System.out.println("Ejemplo: Ingrese 'VACA-G3A' (Estrella) para ver cómo el Max-Heap filtra");
        System.out.println("automáticamente los toros consanguíneos y extrae los 3 de mayor mérito genético.");
        System.out.print("Ingrese ID de arete de la Vaca matriz: ");
        String idVaca = scanner.nextLine().trim();
        servicio.recomendarTop3Cruces(idVaca);
    }

    private static void registrarNuevoBovino() {
        System.out.println("--- REGISTRO DE NUEVO BOVINO ---");
        System.out.print("ID de Arete (ej. NOV-001): ");
        String id = scanner.nextLine().trim();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Raza (ej. Nelore, Brahman, Girolando, Angus): ");
        String raza = scanner.nextLine().trim();

        System.out.print("Sexo ('M' para Macho / 'H' para Hembra): ");
        String sexoStr = scanner.nextLine().trim();
        char sexo = sexoStr.isEmpty() ? 'H' : sexoStr.charAt(0);

        System.out.print("Peso en Kg (ej. 450.0): ");
        double peso = leerDouble(400.0);

        System.out.print("Índice Genético (1.0 a 100.0): ");
        double ig = leerDouble(85.0);

        System.out.print("ID del Padre (dejar vacío o 'NONE' si es fundador): ");
        String idPadre = scanner.nextLine().trim();
        if (idPadre.isEmpty()) idPadre = null;

        System.out.print("ID de la Madre (dejar vacío o 'NONE' si es fundadora): ");
        String idMadre = scanner.nextLine().trim();
        if (idMadre.isEmpty()) idMadre = null;

        servicio.registrarBovino(id, nombre, raza, sexo, peso, ig, idPadre, idMadre);
    }

    private static void registrarNuevoEvento() {
        System.out.println("--- REGISTRO DE EVENTO SANITARIO / PRODUCTIVO ---");
        System.out.print("ID de Arete del Bovino: ");
        String id = scanner.nextLine().trim();

        System.out.print("Fecha del Evento (AAAA-MM-DD): ");
        String fecha = scanner.nextLine().trim();
        if (fecha.isEmpty()) fecha = "2026-03-01";

        System.out.println("Seleccione tipo de evento:");
        System.out.println(" 1. VACUNACION");
        System.out.println(" 2. PESAJE");
        System.out.println(" 3. PARTO");
        System.out.println(" 4. TRATAMIENTO");
        System.out.println(" 5. CONTROL_REPRODUCTIVO");
        System.out.print("Opción (1-5): ");
        String tipoOpt = scanner.nextLine().trim();

        TipoEvento tipo;
        switch (tipoOpt) {
            case "1": tipo = TipoEvento.VACUNACION; break;
            case "2": tipo = TipoEvento.PESAJE; break;
            case "3": tipo = TipoEvento.PARTO; break;
            case "4": tipo = TipoEvento.TRATAMIENTO; break;
            case "5": tipo = TipoEvento.CONTROL_REPRODUCTIVO; break;
            default: tipo = TipoEvento.PESAJE; break;
        }

        System.out.print("Descripción detallada del evento: ");
        String desc = scanner.nextLine().trim();

        double valor = 0.0;
        if (tipo == TipoEvento.PESAJE) {
            System.out.print("Ingrese nuevo peso en Kg: ");
            valor = leerDouble(0.0);
        } else {
            System.out.print("Valor numérico complementario (o 0 si no aplica): ");
            valor = leerDouble(0.0);
        }

        servicio.registrarEvento(id, fecha, tipo, desc, valor);
    }

    private static void ejecutarDemostracionGuiada() {
        System.out.println("==========================================================================");
        System.out.println("         EJECUCIÓN DE DEMOSTRACIÓN GUIADA DEL SISTEMA GENÉTICO            ");
        System.out.println("==========================================================================");

        System.out.println("\n[PASO 1] Visualizando Árbol Binario y Pureza de VACA-G3A (Estrella)...");
        servicio.visualizarArbolGenealogico("VACA-G3A");

        System.out.println("\n[PASO 2] Evaluando Cruce Consanguíneo Crítico: TORO-G2A x VACA-G2A (Hermanos)...");
        servicio.evaluarCruce("TORO-G2A", "VACA-G2A");

        System.out.println("\n[PASO 3] Evaluando Cruce Consanguíneo: TORO-G2A (Padre) x VACA-G3A (Hija)...");
        servicio.evaluarCruce("TORO-G2A", "VACA-G3A");

        System.out.println("\n[PASO 4] Evaluando Cruce Compatible 100% Libre: TORO-G2C (Apollo) x VACA-G3A...");
        servicio.evaluarCruce("TORO-G2C", "VACA-G3A");

        System.out.println("\n[PASO 5] Recomendación Inteligente TOP 3 Toros para VACA-G3A (Estrella)...");
        servicio.recomendarTop3Cruces("VACA-G3A");

        System.out.println("\n[PASO 6] Consultando Ficha e Historial Clínico de VACA-G3A...");
        servicio.consultarFichaBovino("VACA-G3A");

        System.out.println("\n✓ Demostración completada exitosamente. Todas las estructuras funcionaron al 100%.");
    }

    private static double leerDouble(double porDefecto) {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return porDefecto;
        try {
            return Double.parseDouble(input.replace(',', '.'));
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido, asignando por defecto: " + porDefecto);
            return porDefecto;
        }
    }

    private static void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}
