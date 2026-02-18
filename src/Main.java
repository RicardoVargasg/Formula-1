import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String Fichero = "pilotos_f1.txt";
    private static List<Piloto> pilotos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        cargarPilotos();

        int opcion;
        do {
            System.out.println("Formula 1");
            System.out.println("1. Mostrar pilotos");
            System.out.println("2. Añadir nuevo piloto");
            System.out.println("3. Guardar datos");
            System.out.println("0. Salir");
            System.out.println("¿Que quieres hacer?: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    mostrarPilotos();
                    break;
                case 2:
                    añadirPiloto(scanner);
                    break;
                case 3:
                    guardarPilotos();
                    break;
                case 0:
                    System.out.println("Saliendo");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion != 0);

        scanner.close();
    }

    //Cargar Pilotos
    private static void cargarPilotos() {
        File file = new File(Fichero);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String nombre = datos[0];
                String escuderia = datos[1];
                int puntos = Integer.parseInt(datos[2]);

                pilotos.add(new Piloto(nombre, escuderia, puntos));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los pilotos");
        }
    }

    //Mostrar Pilotos
    private static void mostrarPilotos() {
        if (pilotos.isEmpty()) {
            System.out.println("No hay pilotos registrados.");
            return;
        }
        pilotos.forEach(System.out::println);
    }

    //Añadir nuevo piloto
    private static void añadirPiloto(Scanner scanner) {
        System.out.println("Nombre: ");
        String nombre = scanner.nextLine();

        boolean existe = pilotos.stream().anyMatch(p -> p.getNombre().equalsIgnoreCase(nombre));

        if (existe) {
            System.out.println("Ya hay un piloto con ese nombre");
            return;
        }

        System.out.println("Escuderia: ");
        String escuderia = scanner.nextLine();

        System.out.println("Puntos: ");
        int puntos = scanner.nextInt();
        scanner.nextLine();

        pilotos.add(new Piloto(nombre, escuderia, puntos));
        System.out.println("Piloto añadido");
    }

    //Guardar datos
    private static void guardarPilotos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Fichero))) {
            for (Piloto p : pilotos) {
                pw.println(p.toFileFormat());
            }
            System.out.println("Datos guardados.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos");
        }
    }
}