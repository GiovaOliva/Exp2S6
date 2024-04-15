/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp2_s6_giovanyoliva;

        
/**
 *
 * @author k-9_o
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Exp2_S6_GiovanyOliva {

    /**
     * @param args the command line arguments
     */
    
     

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner (System.in);
        int[] tribuna = new int[10]; // Array de Tribuna (Galería) con 10 asientos
        int[] palco = new int[6];   // Array de Palco con 6 asientos
        Map<String, List<Map<String, Object>>> purchaseDetails = new HashMap<>(); // Mapa para almacenar detalles de compras

        // Inicializar arrays de asientos
        iniciarAsientos(tribuna, palco);
        while (true) {
             
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir carácter de nueva línea

            switch (choice) {
                case 1:
                    comprarEntradas(tribuna, palco, purchaseDetails);
                    break;
                case 2:
                    imprimirBoleta(purchaseDetails);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        }
    
    }
    
    
    
    public static void displayMainMenu() {
        System.out.println("\nSistema de Venta de Entradas del Teatro Moro");
        System.out.println("-----------------------------------------");
        System.out.println("1. Comprar Entradas");
        System.out.println("2. Imprimir Boleta");
        System.out.println("0. Salir");
        System.out.print("Ingrese su opción: ");
    }
    
    private static void comprarEntradas(int[] tribuna, int[] palco, Map<String, List<Map<String, Object>>> purchaseDetails) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=================== Compra de Entradas =====================");

        // Mostrar opciones de tipo de entrada
        System.out.println("1. Tribuna");
        System.out.println("2. Palco");
        System.out.print("Seleccione el tipo de entrada: ");

        int tipoEntradaChoice = scanner.nextInt();

        // Validar la selección del tipo de entrada
        if (tipoEntradaChoice < 1 || tipoEntradaChoice > 2) {
            System.out.println("Opción inválida. Debe elegir entre 1 y 2.");
            return;
        }

        String tipoEntrada = tipoEntradaChoice == 1 ? "Tribuna" : "Palco";

        // Mostrar asientos disponibles del tipo seleccionado
        int[] selectedCategorySeats = tipoEntrada.equals("Tribuna") ? tribuna : palco;
        System.out.println("\nAsientos disponibles:");
        for (int seatNumber : selectedCategorySeats) {
            if (seatNumber != 0) {
                System.out.print(seatNumber + " ");
            }
        }

        System.out.print("\nSeleccione el número de asiento: ");
        int numeroAsientoElegido = scanner.nextInt();

        // Validar la selección del número de asiento
        boolean seatFound = false;
        for (int seatNumber : selectedCategorySeats) {
            if (seatNumber != 0 && seatNumber == numeroAsientoElegido) {
                seatFound = true;
                break;
            }
        }

        if (!seatFound) {
            System.out.println("Asiento inválido o no disponible. Seleccione un asiento de la lista.");
            return;
        }

        // Confirmar la compra
        System.out.print("¿Confirmar compra? (Si/No): ");
        String confirmation = scanner.next();

        if (!confirmation.equalsIgnoreCase("Si")) {
            System.out.println("Compra cancelada.");
            return;
        }

        // Marcar el asiento como no disponible (asignarle el valor 0)
        for (int i = 0; i < selectedCategorySeats.length; i++) {
            if (selectedCategorySeats[i] == numeroAsientoElegido) {
                selectedCategorySeats[i] = 0;
                break;
            }
        }

        // Agregar la información de la compra a la matriz de compras
        Map<String, Object> ticketInfo = new HashMap<>();
        ticketInfo.put("tipoEntrada", tipoEntrada);
        ticketInfo.put("numeroAsiento", numeroAsientoElegido);
        ticketInfo.put("precio", getTicketPrice(tipoEntrada)); // Obtener precio según el tipo de entrada

        List<Map<String, Object>> categoryTickets = purchaseDetails.getOrDefault(tipoEntrada, new ArrayList<>());
        categoryTickets.add(ticketInfo);
        purchaseDetails.put(tipoEntrada, categoryTickets);

        System.out.println("\nCompra realizada con éxito!");
    }
    private static void imprimirBoleta(Map<String, List<Map<String, Object>>> purchaseDetails) {
        // Declarar variables locales
        double totalTribunaPrecio = 0;
        double totalPalcoPrecio = 0;
        int totalTribunaAsientos = 0;
        int totalPalcoAsientos = 0;

        // Recorrer los detalles de compra
        for (Map.Entry<String, List<Map<String, Object>>> entry : purchaseDetails.entrySet()) {
            String tipoEntrada = entry.getKey();
            List<Map<String, Object>> entryDetails = entry.getValue();

            // Inicializar variables auxiliares para concatenar números de asiento
            String asientosTipoEntradaAux = "";
            String asientosTipoEntrada = "";

            // Procesar detalles de cada tipo de entrada
            for (Map<String, Object> detail : entryDetails) {
                int numeroAsiento = (int) detail.get("numeroAsiento"); // Obtener el número de asiento
                double precio = (double) detail.get("precio"); // Obtener el precio del asiento

                // Calcular y acumular totales por tipo de entrada
                if (tipoEntrada.equalsIgnoreCase("Tribuna")) {
                    totalTribunaAsientos++;
                    totalTribunaPrecio += precio;

                    if (asientosTipoEntradaAux.isEmpty()) {
                        asientosTipoEntradaAux += numeroAsiento;
                    } else {
                        asientosTipoEntradaAux += ", " + numeroAsiento;
                    }
                } else if (tipoEntrada.equalsIgnoreCase("Palco")) {
                    totalPalcoAsientos++;
                    totalPalcoPrecio += precio;

                    if (asientosTipoEntradaAux.isEmpty()) {
                        asientosTipoEntradaAux += numeroAsiento;
                    } else {
                        asientosTipoEntradaAux += ", " + numeroAsiento;
                    }
                }

                // Concatenar número de asiento a la variable principal
                asientosTipoEntrada += numeroAsiento + " ";
            }

            // Imprimir detalles de cada tipo de entrada
            if (!asientosTipoEntradaAux.isEmpty()) {
                System.out.println("\nTipo de entrada: " + tipoEntrada);
                System.out.println("Asientos: " + asientosTipoEntradaAux);

                if (tipoEntrada.equalsIgnoreCase("Tribuna")) {
                    System.out.printf("Total a pagar: $%.2f\n", totalTribunaPrecio);
                } else if (tipoEntrada.equalsIgnoreCase("Palco")) {
                    System.out.printf("Total a pagar: $%.2f\n", totalPalcoPrecio);
                }
            }
        }

        // Calcular y mostrar el total general
        double totalPrecio = totalTribunaPrecio + totalPalcoPrecio;
        System.out.println("\n==========================================");
        System.out.printf("TOTAL GENERAL A PAGAR: $%.2f\n", totalPrecio);
        System.out.println("==========================================");

        // Reiniciar el mapa de boletas para la siguiente compra
        purchaseDetails.clear(); // Eliminar todas las entradas del mapa
    }
    
    private static void iniciarAsientos(int[] tribuna, int[] palco) {
    // Inicializar asientos de Tribuna (Galería)
        for (int i = 0; i < tribuna.length; i++) {
            tribuna[i] = i + 1; // Asignar número de asiento (1, 2, 3...)
        }

        // Inicializar asientos de Palco
        for (int i = 0; i < palco.length; i++) {
            palco[i] = i + 11; // Asignar número de asiento (11, 12, 13...)
        }
    }
    
    private static double getTicketPrice(String tipoEntrada) {
        if (tipoEntrada.equals("Tribuna")) {
            return 5000.0; // Precio de Tribuna
        } else if (tipoEntrada.equals("Palco")) {
            return 10000.0; // Precio de Palco
        } else {
            System.out.println("Error: Tipo de entrada no válido.");
            return -1.0; // Valor de error
        }
    }
}
