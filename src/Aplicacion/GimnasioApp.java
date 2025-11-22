package Aplicacion;

import Excepciones.ExcepcionSocioNoEncontrado;
import Modelo.Entrenador;
import Modelo.Socio;
import Servicio.ServicioGimnasio;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class GimnasioApp {

    private static final Scanner entrada = new Scanner(System.in);
    private static final ServicioGimnasio servicio = new ServicioGimnasio();

    public static void main(String[] args) {
        mostrarMenuPrincipal();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=====  GIMNASIO FITNEA  =====");
        System.out.println("1. Gestión de socios");
        System.out.println("2. Gestión de entrenadores");
        System.out.println("3. Gestión de clases grupales");
        System.out.println("4. Registrar asistencia");
        System.out.println("5. Registrar pago de cuota");
        System.out.println("6. Reportes");
        System.out.println("7. Ver grilla de horarios");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        int opcion = leerEnteroSeguro();

        switch (opcion) {
            case 1 -> menuSocios();
            case 2 -> menuEntrenadores();
            case 0 -> {
                System.out.println("Saliendo del sistema...");
                return;
            }
            default -> System.out.println("Opción inválida.");
        }

        mostrarMenuPrincipal();
    }
}