package Aplicacion;

import Excepciones.ExcepcionSocioNoEncontrado;
import Modelo.Entrenador;
import Modelo.Socio;
import Modelo.Rutina;
import Modelo.RutinaCardio;
import Modelo.RutinaFuerza;
import Servicio.ServicioGimnasio;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class GimnasioApp {

    private static final Scanner entrada = new Scanner(System.in);
    private static final ServicioGimnasio servicio = new ServicioGimnasio();

    private record InputResult(String value, boolean back) {
    }

    static void main() {
        mostrarMenuPrincipal();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=====  GIMNASIO FITNEA  =====");
        System.out.println("1. Gestión de socios");
        System.out.println("2. Gestión de entrenadores");
        System.out.println("3. Registrar pago de cuota");
        System.out.println("4. Reportes");
        System.out.println("5. Ver grilla de horarios");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        int opcion = leerEnteroSeguro();

        switch (opcion) {
            case 1 -> menuSocios();
            case 2 -> menuEntrenadores();
            case 3 -> registrarPago();
            case 0 -> {
                System.out.println("Saliendo del sistema...");
                return;
            }
            default -> System.out.println("Opción inválida.");
        }

        mostrarMenuPrincipal();
    }

    private static int leerEnteroSeguro() {
        try {
            return Integer.parseInt(entrada.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número entero.");
            return -1;
        }
    }

    private static double leerDoubleSeguro() {
        try {
            return Double.parseDouble(entrada.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un número válido.");
            return 0;
        }
    }

    private static double leerDoublePositivo(String prompt) {
        double val;
        do {
            if (!prompt.isEmpty()) System.out.print(prompt);
            val = leerDoubleSeguro();
            if (val <= 0) {
                System.out.println("Debe ingresar un número mayor que 0.");
            }
        } while (val <= 0);
        return val;
    }

    private static void mostrarIndicacionVolver() {
        System.out.println("Presiona V para volver");
    }

    private static InputResult leerLineaSinHint(String prompt) {
        System.out.print(prompt + " ");
        String line = entrada.nextLine();
        if (line.equalsIgnoreCase("V")) return new InputResult("", true);
        return new InputResult(line, false);
    }

    private static InputResult leerSalarioConVolver() {
        while (true) {
            InputResult r = leerLineaSinHint("Salario:");
            if (r.back) return r;
            try {
                Double.parseDouble(r.value);
                return r;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido o presione V para volver.");
            }
        }
    }

    private static InputResult leerTipoMembresiaConVolver() {
        while (true) {
            System.out.print("Tipo de membresía (1. REGULAR | 2. COMPLETA): ");
            String line = entrada.nextLine();
            if (line.equalsIgnoreCase("V")) return new InputResult("", true);
            int opcion;
            try {
                opcion = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar 1 o 2, o V para volver.");
                continue;
            }
            if (opcion == 1) return new InputResult("REGULAR", false);
            if (opcion == 2) return new InputResult("COMPLETA", false);
            System.out.println("Opción inválida. Debe seleccionar 1 o 2.");
        }
    }

    private static InputResult leerTipoRutinaConVolver() {
        while (true) {
            System.out.print("Tipo de rutina (1. CARDIO | 2. FUERZA): ");
            String line = entrada.nextLine();
            if (line.equalsIgnoreCase("V")) return new InputResult("", true);
            int opcion;
            try {
                opcion = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar 1 o 2, o V para volver.");
                continue;
            }
            if (opcion == 1) return new InputResult("CARDIO", false);
            if (opcion == 2) return new InputResult("FUERZA", false);
            System.out.println("Opción inválida. Debe seleccionar 1 o 2.");
        }
    }

    private static Socio buscarSocioPorDni(String dni) {
        if (dni == null || dni.isEmpty()) return null;
        return servicio.listarSocios().stream()
                .filter(s -> s.getDni().equalsIgnoreCase(dni))
                .findFirst()
                .orElse(null);
    }

    private static Entrenador buscarEntrenadorPorDni(String dni) {
        if (dni == null || dni.isEmpty()) return null;
        return servicio.listarEntrenadores().stream()
                .filter(e -> e.getDni().equalsIgnoreCase(dni))
                .findFirst()
                .orElse(null);
    }

    private static void menuSocios() {
        System.out.println("\n--- Gestión de socios ---");
        System.out.println("1. Agregar socio");
        System.out.println("2. Listar socios");
        System.out.println("3. Eliminar socio");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        int opcion = leerEnteroSeguro();

        switch (opcion) {
            case 1 -> agregarSocio();
            case 2 -> listarSocios();
            case 3 -> eliminarSocio();
            case 0 -> {
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    private static void agregarSocio() {
        String dni = "", nombre = "", apellido = "", telefono = "", email = "", tipoMembresia = "";
        mostrarIndicacionVolver();
        int paso = 0;
        while (paso < 6) {
            switch (paso) {
                case 0 -> {
                    InputResult r = leerLineaSinHint("DNI:");
                    if (r.back()) {
                        System.out.println("Volviendo al menú de socios.");
                        return;
                    }
                    dni = r.value();
                    paso++;
                }
                case 1 -> {
                    InputResult r = leerLineaSinHint("Nombre:");
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    nombre = r.value();
                    paso++;
                }
                case 2 -> {
                    InputResult r = leerLineaSinHint("Apellido:");
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    apellido = r.value();
                    paso++;
                }
                case 3 -> {
                    InputResult r = leerLineaSinHint("Numero Telefonico:");
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    telefono = r.value();
                    paso++;
                }
                case 4 -> {
                    InputResult r = leerLineaSinHint("Email:");
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    email = r.value();
                    paso++;
                }
                case 5 -> {
                    InputResult r = leerTipoMembresiaConVolver();
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    tipoMembresia = r.value();
                    paso++;
                }
            }
        }
        try {
            Socio socio = servicio.agregarSocio(dni, nombre, apellido, telefono, email, tipoMembresia);
            System.out.println("Socio: DNI: " + socio.getDni()
                    + ", Nombre Completo: " + socio.getNombre() + " " + socio.getApellido()
                    + ", Membresia: " + socio.getTipoMembresia()
                    + ", Estado: " + (socio.isActivo() ? "activo" : "no activo"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarSocios() {
        List<Socio> socios = servicio.listarSocios();
        if (socios.isEmpty()) {
            System.out.println("No hay socios registrados por el momento.");
            return;
        }
        for (Socio s : socios) {
            System.out.println("Socio: DNI: " + s.getDni()
                    + ", Nombre Completo: " + s.getNombre() + " " + s.getApellido()
                    + ", Membresia: " + s.getTipoMembresia()
                    + ", Estado: " + (s.isActivo() ? "activo" : "no activo"));
        }
    }

    private static void eliminarSocio() {
        List<Socio> socios = servicio.listarSocios();
        if (socios.isEmpty()) {
            System.out.println("No es posible eliminar, ya que no hay socios registrados.");
            return;
        }
        System.out.print("Ingrese DNI del socio a eliminar: ");
        String dni = entrada.nextLine();
        Socio s = buscarSocioPorDni(dni);
        if (s == null) {
            System.out.println("No se encontró el socio. Volviendo a gestión de socios.");
            return;
        }
        try {
            servicio.eliminarSocio(s.getIdSocio());
            System.out.println("Socio eliminado correctamente.");
        } catch (ExcepcionSocioNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }

    private static void menuEntrenadores() {
        System.out.println("\n--- Gestión de entrenadores ---");
        System.out.println("1. Agregar entrenador");
        System.out.println("2. Listar entrenadores");
        System.out.println("3. Modificar especialidad");
        System.out.println("4. Modificar salario");
        System.out.println("5. Eliminar entrenador");
        System.out.println("6. Crear y asignar rutina a un socio");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        int opcion = leerEnteroSeguro();
        switch (opcion) {
            case 1 -> agregarEntrenador();
            case 2 -> listarEntrenadores();
            case 3 -> modificarEspecialidadEntrenador();
            case 4 -> modificarSalarioEntrenador();
            case 5 -> eliminarEntrenador();
            case 6 -> crearYAsignarRutinaEntrenador();
            case 0 -> {
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    private static void agregarEntrenador() {
        String dni = "", nombre = "", apellido = "", telefono = "", email = "", especialidad = "";
        double salario = 0.0;
        mostrarIndicacionVolver();
        int paso = 0;
        while (paso < 7) {
            switch (paso) {
                case 0 -> {
                    InputResult r = leerLineaSinHint("DNI:");
                    if (r.back()) {
                        System.out.println("Volviendo al menú de entrenadores.");
                        return;
                    }
                    dni = r.value();
                    paso++;
                }
                case 1 -> {
                    InputResult r = leerLineaSinHint("Nombre:");
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    nombre = r.value();
                    paso++;
                }
                case 2 -> {
                    InputResult r = leerLineaSinHint("Apellido:");
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    apellido = r.value();
                    paso++;
                }
                case 3 -> {
                    InputResult r = leerLineaSinHint("Teléfono:");
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    telefono = r.value();
                    paso++;
                }
                case 4 -> {
                    InputResult r = leerLineaSinHint("Email:");
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    email = r.value();
                    paso++;
                }
                case 5 -> {
                    InputResult r = leerLineaSinHint("Especialidad:");
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    especialidad = r.value();
                    paso++;
                }
                case 6 -> {
                    InputResult r = leerSalarioConVolver();
                    if (r.back()) {
                        paso--;
                        continue;
                    }
                    salario = Double.parseDouble(r.value());
                    paso++;
                }
            }
        }
        Entrenador entrenador = servicio.agregarEntrenador(dni, nombre, apellido, telefono, email, especialidad, salario);
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.println("Entrenador: DNI: " + entrenador.getDni()
                + ", Nombre Completo: " + entrenador.getNombre() + " " + entrenador.getApellido()
                + ", Especialidad: " + entrenador.getEspecialidad()
                + ", Salario: " + df.format(entrenador.getSalario()) + " $");
    }

    private static void listarEntrenadores() {
        List<Entrenador> entrenadores = servicio.listarEntrenadores();
        if (entrenadores.isEmpty()) {
            System.out.println("No se puede listar, ya que no hay entrenadores registrados.");
            return;
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        for (Entrenador e : entrenadores) {
            System.out.println("Entrenador: DNI: " + e.getDni()
                    + ", Nombre Completo: " + e.getNombre() + " " + e.getApellido()
                    + ", Especialidad: " + e.getEspecialidad()
                    + ", Salario: " + df.format(e.getSalario()) + " $");
        }
    }

    private static void modificarEspecialidadEntrenador() {
        System.out.print("Ingrese DNI del entrenador a modificar: ");
        String dni = entrada.nextLine();
        Entrenador e = buscarEntrenadorPorDni(dni);
        if (e == null) {
            System.out.println("No se encontró el entrenador. Volviendo a gestión de entrenadores.");
            return;
        }
        System.out.println("Entrenador encontrado: DNI: " + e.getDni()
                + ", Nombre Completo: " + e.getNombre() + " " + e.getApellido());
        System.out.println("Especialidad actual: " + e.getEspecialidad());
        System.out.print("Nueva especialidad: ");
        String nuevaEsp = entrada.nextLine();
        if (nuevaEsp.isEmpty()) {
            System.out.println("Especialidad vacía. Modificación cancelada.");
            return;
        }
        e.setEspecialidad(nuevaEsp);
        System.out.println("Especialidad actualizada correctamente.");
    }

    private static void modificarSalarioEntrenador() {
        System.out.print("Ingrese DNI del entrenador a modificar: ");
        String dni = entrada.nextLine();
        Entrenador e = buscarEntrenadorPorDni(dni);
        if (e == null) {
            System.out.println("No se encontró el entrenador. Volviendo a gestión de entrenadores.");
            return;
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.println("Entrenador encontrado: " + e.getNombre() + " " + e.getApellido());
        System.out.println("Salario actual: " + df.format(e.getSalario()) + " $");
        double nuevoSalario = leerDoublePositivo("Nuevo salario: ");
        e.setSalario(nuevoSalario);
        System.out.println("Salario actualizado correctamente.");
    }

    private static void eliminarEntrenador() {
        List<Entrenador> entrenadores = servicio.listarEntrenadores();
        if (entrenadores.isEmpty()) {
            System.out.println("No es posible eliminar, ya que no hay entrenadores registrados.");
            return;
        }
        System.out.print("Ingrese DNI del entrenador a eliminar: ");
        String dni = entrada.nextLine();
        Entrenador e = buscarEntrenadorPorDni(dni);
        if (e == null) {
            System.out.println("No se encontró el entrenador. Volviendo a gestión de entrenadores.");
            return;
        }
        // Asumiendo que el ID del entrenador se puede obtener de alguna manera
        // servicio.eliminarEntrenador(e.getId());
        System.out.println("Funcionalidad de eliminar entrenador no implementada completamente.");
    }

    private static void crearYAsignarRutinaEntrenador() {
        System.out.println("\n--- Crear y asignar rutina a un socio ---");
        mostrarIndicacionVolver();
        InputResult rDni = leerLineaSinHint("Ingrese DNI del socio:");
        if (rDni.back()) {
            System.out.println("Volviendo a gestión de entrenadores.");
            return;
        }
        String dni = rDni.value();
        Socio socio = buscarSocioPorDni(dni);
        if (socio == null) {
            System.out.println("No se encontró el socio con DNI " + dni + ". Operación cancelada.");
            return;
        }
        InputResult tipoR = leerTipoRutinaConVolver();
        if (tipoR.back()) {
            System.out.println("Operación cancelada.");
            return;
        }
        boolean esCardio = "CARDIO".equalsIgnoreCase(tipoR.value());
        Rutina rutina;
        if (esCardio) {
            int duracion = (int) leerDoublePositivo("Duración (minutos): ");
            int frecCardiaca = (int) leerDoublePositivo("Frecuencia cardíaca (ppm): ");
            rutina = new RutinaCardio(duracion, frecCardiaca);
        } else {
            int series = (int) leerDoublePositivo("Series: ");
            int repeticiones = (int) leerDoublePositivo("Repeticiones: ");
            double peso = leerDoublePositivo("Peso (kg): ");
            rutina = new RutinaFuerza(series, repeticiones, peso);
        }
        System.out.println("Ingrese ejercicios uno por línea. Dejar vacío para terminar.");
        int idx = 1;
        while (true) {
            InputResult rEj = leerLineaSinHint("Ejercicio " + idx + ":");
            if (rEj.back() || rEj.value().trim().isEmpty()) {
                break;
            }
            rutina.agregarEjercicio(rEj.value().trim());
            idx++;
        }
        try {
            servicio.asignarRutinaSocio(dni, rutina);
            System.out.println("Rutina asignada correctamente al socio " + dni + ".");
            System.out.println(rutina);
        } catch (ExcepcionSocioNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }

    private static void registrarPago() {
        System.out.println("\n--- Registrar Pago de Cuota ---");
        mostrarIndicacionVolver();
        InputResult rDni = leerLineaSinHint("Ingrese DNI del socio:");
        if (rDni.back()) {
            System.out.println("Volviendo al menú principal.");
            return;
        }
        String dni = rDni.value();
        Socio socio = buscarSocioPorDni(dni);
        if (socio == null) {
            System.out.println("Error: No se encontró el socio con DNI " + dni + ".");
            return;
        }
        double montoCuota = socio.calcularCuota();
        InputResult rMes = leerLineaSinHint("Ingrese el mes a pagar (formato MM-AAAA):");
        if (rMes.back()) {
            registrarPago();
            return;
        }
        String mesIngresado = rMes.value();
        System.out.println("Cuota a pagar: " + new DecimalFormat("#0.00").format(montoCuota) + " $");
        try {
            servicio.registrarPago(socio.getIdSocio(), montoCuota, mesIngresado);
            System.out.println("Pago del mes " + mesIngresado + " registrado exitosamente.");
            System.out.println("El socio " + socio.getNombre() + " está activo.");
        } catch (Exception e) {
            System.out.println("Error al procesar el pago: " + e.getMessage());
        }
    }
}