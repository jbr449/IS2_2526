package es.unican.is2.practica6;

import java.util.List;
import java.util.Scanner;

/**
 * Interfaz de consola para evitar dependencias externas a fundamentos.
 */
public class GestionTransportesGUI {

    private static final int ANHADE_CONDUCTOR = 1;
    private static final int ANHADE_TRANSPORTE = 2;
    private static final int SUELDO_CONDUCTOR = 3;
    private static final int MEJOR_CONDUCTOR = 4;
    private static final int SALIR = 0;

    public static void main(String[] args) {
        GestionTransportes gt = new GestionTransportes();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            muestraMenu();
            if (!scanner.hasNextLine()) {
                return;
            }
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case ANHADE_CONDUCTOR -> anhadeConductor(gt, scanner);
                case ANHADE_TRANSPORTE -> anhadeTransporte(gt, scanner);
                case SUELDO_CONDUCTOR -> muestraSueldo(gt, scanner);
                case MEJOR_CONDUCTOR -> muestraMejoresConductores(gt);
                case SALIR -> System.out.println("Fin");
                default -> System.out.println("Opcion no valida");
            }
        } while (opcion != SALIR);
    }

    private static void muestraMenu() {
        System.out.println("Transportes");
        System.out.println("1. Anhade conductor");
        System.out.println("2. Anhade transporte");
        System.out.println("3. Sueldo conductor");
        System.out.println("4. Mejor conductor");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");
    }

    private static void anhadeConductor(GestionTransportes gt, Scanner scanner) {
        String dni = leeTexto(scanner, "DNI");
        String nombre = leeTexto(scanner, "Nombre");
        String apellido1 = leeTexto(scanner, "Apellido1");
        String apellido2 = leeTexto(scanner, "Apellido2");
        String direccion = leeTexto(scanner, "Direccion");

        if (!gt.anhadeConductor(dni, nombre, apellido1, apellido2, direccion)) {
            System.out.println("ERROR: Ya existe un conductor con DNI " + dni);
        }
    }

    private static void anhadeTransporte(GestionTransportes gt, Scanner scanner) {
        String dni = leeTexto(scanner, "DNI");
        Conductor c = gt.buscaConductor(dni);
        if (c == null) {
            System.out.println("ERROR: No existe un conductor con DNI " + dni);
            return;
        }

        String tipo = leeTexto(scanner, "Tipo Transporte: P | M | MP");
        double horas = leeDouble(scanner, "Horas");
        int valor = leeInt(scanner, tipo.equals("P") ? "Personas" : "Toneladas");
        CategoriaTransporte categoria = categoriaDesdeTexto(tipo);
        c.anhadeTransporte(new Transporte(horas, categoria, valor));
    }

    private static void muestraSueldo(GestionTransportes gt, Scanner scanner) {
        String dni = leeTexto(scanner, "DNI");
        Conductor c = gt.buscaConductor(dni);
        if (c != null) {
            System.out.println("El sueldo del conductor es: " + c.sueldo());
        } else {
            System.out.println("ERROR: No existe un conductor con DNI " + dni);
        }
    }

    private static void muestraMejoresConductores(GestionTransportes gt) {
        List<Conductor> resultado = gt.mejoresConductores();
        if (resultado.isEmpty()) {
            System.out.println("No hay conductores");
            return;
        }
        for (Conductor conductor : resultado) {
            System.out.println(conductor.getNombre() + " " + conductor.getApellido1());
        }
    }

    private static CategoriaTransporte categoriaDesdeTexto(String tipo) {
        return switch (tipo) {
            case "P" -> CategoriaTransporte.Personas;
            case "M" -> CategoriaTransporte.Mercancias;
            case "MP" -> CategoriaTransporte.MercanciasPeligrosas;
            default -> throw new IllegalArgumentException();
        };
    }

    private static String leeTexto(Scanner scanner, String etiqueta) {
        System.out.print(etiqueta + ": ");
        return scanner.nextLine();
    }

    private static int leeInt(Scanner scanner, String etiqueta) {
        return Integer.parseInt(leeTexto(scanner, etiqueta));
    }

    private static double leeDouble(Scanner scanner, String etiqueta) {
        return Double.parseDouble(leeTexto(scanner, etiqueta));
    }
}
