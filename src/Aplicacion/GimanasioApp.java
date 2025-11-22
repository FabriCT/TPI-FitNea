package Aplicacion;

public class GimanasioApp {

    public static void main(String[] args) {
        mostrarMenuPrincipal();
    }

    // Menú principal (usa recursividad simple)
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
    }
}
