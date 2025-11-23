package Aplicacion;

import Excepciones.ExcepcionSocioNoEncontrado;
import Modelo.Entrenador;
import Modelo.Socio;
import Modelo.Pago;
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

    private static String leerTipoMembresia() {
        while (true) {
            System.out.print("Tipo de membresía (1. REGULAR | 2. COMPLETA): ");
            String line = entrada.nextLine();
            int opcion;
            try {
                opcion = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar 1 o 2.");
                continue;
            }
            if (opcion == 1) return "REGULAR";
            if (opcion == 2) return "COMPLETA";
            System.out.println("Opción inválida. Debe seleccionar 1 o 2.");
        }
    }

    private static String obtenerStringPorMetodos(Object obj, String... nombresMetodos) {
        if (obj == null) return "";
        for (String nombre : nombresMetodos) {
            try {
                Method m = obj.getClass().getMethod(nombre);
                Object res = m.invoke(obj);
                if (res != null) return res.toString();
            } catch (Exception ignored) {}
        }
        return "";
    }

    private static double obtenerDoublePorMetodos(Object obj, String... nombresMetodos) {
        if (obj == null) return 0.0;
        for (String nombre : nombresMetodos) {
            try {
                Method m = obj.getClass().getMethod(nombre);
                Object res = m.invoke(obj);
                if (res instanceof Number) return ((Number) res).doubleValue();
            } catch (Exception ignored) {}
        }
        return 0.0;
    }

    private static int obtenerIdPorMetodos(Object obj, String... nombresMetodos) {
        if (obj == null) return -1;
        for (String nombre : nombresMetodos) {
            try {
                Method m = obj.getClass().getMethod(nombre);
                Object res = m.invoke(obj);
                if (res instanceof Number) return ((Number) res).intValue();
                if (res instanceof String) {
                    try { return Integer.parseInt((String) res); } catch (Exception ignored) {}
                }
            } catch (Exception ignored) {}
        }
        return -1;
    }

    private static String obtenerEstado(Socio s) {
        if (s == null) return "no activo";
        try {
            try {
                Method m = s.getClass().getMethod("estaActivo");
                Object res = m.invoke(s);
                if (res instanceof Boolean) return ((Boolean) res) ? "activo" : "no activo";
            } catch (NoSuchMethodException ignored) {}

            try {
                Method m = s.getClass().getMethod("tieneCuotaAlDia");
                Object res = m.invoke(s);
                if (res instanceof Boolean) return ((Boolean) res) ? "activo" : "no activo";
            } catch (NoSuchMethodException ignored) {}

            try {
                Method m = s.getClass().getMethod("isActivo");
                Object res = m.invoke(s);
                if (res instanceof Boolean) return ((Boolean) res) ? "activo" : "no activo";
            } catch (NoSuchMethodException ignored) {}
        } catch (Exception ignored) {}

        return "no activo";
    }

    private static Socio buscarSocioPorDni(String dni) {
        if (dni == null || dni.isEmpty()) return null;
        for (Socio s : servicio.listarSocios()) {
            String sdni = obtenerStringPorMetodos(s, "getDni", "dni", "getDocumento");
            if (sdni != null && sdni.equalsIgnoreCase(dni)) {
                return s;
            }
        }
        return null;
    }

    private static Entrenador buscarEntrenadorPorDni(String dni) {
        if (dni == null || dni.isEmpty()) return null;
        for (Entrenador e : servicio.listarEntrenadores()) {
            String edni = obtenerStringPorMetodos(e, "getDni", "dni", "getDocumento");
            if (edni != null && edni.equalsIgnoreCase(dni)) {
                return e;
            }
        }
        return null;
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
            case 0 -> { }
            default -> System.out.println("Opción inválida.");
        }
    }

    // ======== SOCIOS ========

    private static void agregarSocio() {
        System.out.print("DNI: ");
        String dni = entrada.nextLine();
        System.out.print("Nombre: ");
        String nombre = entrada.nextLine();
        System.out.print("Apellido: ");
        String apellido = entrada.nextLine();
        System.out.print("Numero Telefonico: ");
        String telefono = entrada.nextLine();
        System.out.print("Email: ");
        String email = entrada.nextLine();
        String tipoMembresia = leerTipoMembresia();

        try {
            Socio socio = servicio.agregarSocio(dni, nombre, apellido, telefono, email, tipoMembresia);

            String estado = obtenerEstado(socio);

            System.out.println("Socio: DNI: " + (dni.isEmpty() ? "-" : dni)
                    + ", Nombre Completo: " + (nombre.isEmpty() ? "-" : nombre)
                    + (apellido.isEmpty() ? "" : " " + apellido)
                    + ", Membresia: " + (tipoMembresia.isEmpty() ? "-" : tipoMembresia)
                    + ", Estado: " + estado);

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
        DecimalFormat df = new DecimalFormat("#0.00");
        for (Socio s : socios) {
            String dni = obtenerStringPorMetodos(s, "getDni", "dni", "getDocumento");
            String nombre = obtenerStringPorMetodos(s, "getNombre", "nombre");
            String apellido = obtenerStringPorMetodos(s, "getApellido", "apellido");
            String membresia = obtenerStringPorMetodos(s, "getTipoMembresia", "getMembresia", "tipoMembresia", "membresia");

            String estado = obtenerEstado(s);

            System.out.println("Socio: DNI: " + (dni.isEmpty() ? "-" : dni)
                    + ", Nombre Completo: " + (nombre.isEmpty() ? "-" : nombre)
                    + (apellido.isEmpty() ? "" : " " + apellido)
                    + ", Membresia: " + (membresia.isEmpty() ? "-" : membresia)
                    + ", Estado: " + estado);
        }
    }

    private static void modificarSocio() {
        List<Socio> socios = servicio.listarSocios();
        if (socios.isEmpty()) {
            System.out.println("No es posible modificar, ya que no hay socios registrados.");
            return;
        }

        System.out.print("Ingrese DNI del socio a modificar: ");
        String dni = entrada.nextLine();
        Socio s = buscarSocioPorDni(dni);
        if (s == null) {
            System.out.println("No se encontró el socio. Volviendo a gestión de socios.");
            return;
        }

        DecimalFormat df = new DecimalFormat("#0.00");
        String nombre = obtenerStringPorMetodos(s, "getNombre", "nombre");
        String apellido = obtenerStringPorMetodos(s, "getApellido", "apellido");
        System.out.println("Socio encontrado: DNI: " + (dni.isEmpty() ? "-" : dni)
                + ", Nombre Completo: " + (nombre.isEmpty() ? "-" : nombre) + (apellido.isEmpty() ? "" : " " + apellido));


        int id = obtenerIdPorMetodos(s, "getId", "getIdSocio", "id", "getId_usuario");
        if (id == -1) {
            System.out.println("No se pudo obtener el ID interno del socio. Modificación cancelada.");
            return;
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

        int id = obtenerIdPorMetodos(s, "getId", "getIdSocio", "id", "getId_usuario");
        if (id != -1) {
            try {
                servicio.eliminarSocio(id);
                System.out.println("Socio eliminado correctamente.");
            } catch (ExcepcionSocioNoEncontrado e) {
                System.out.println(e.getMessage());
            }
            return;
        }


        String[] candidatos = {"eliminarSocioPorDni", "eliminarPorDni", "eliminarSocioByDni", "eliminarSocio"};
        for (String nombre : candidatos) {
            try {
                Method m = servicio.getClass().getMethod(nombre, String.class);
                m.invoke(servicio, dni);
                System.out.println("Socio eliminado correctamente.");
                return;
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                System.out.println("Error al eliminar el socio: " + e.getMessage());
                return;
            }
        }

        System.out.println("No se pudo eliminar el socio: no se encontró id interno ni método de eliminación por DNI en ServicioGimnasio.");
    }

    // ======== ENTRENADORES ========

    private static void menuEntrenadores() {
        System.out.println("\n--- Gestión de entrenadores ---");
        System.out.println("1. Agregar entrenador");
        System.out.println("2. Listar entrenadores");
        System.out.println("3. Modificar especialidad");
        System.out.println("4. Modificar salario");
        System.out.println("5. Eliminar entrenador");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        int opcion = leerEnteroSeguro();

        switch (opcion) {
            case 1 -> agregarEntrenador();
            case 2 -> listarEntrenadores();
            case 3 -> modificarEspecialidadEntrenador();
            case 4 -> modificarSalarioEntrenador();
            case 5 -> eliminarEntrenador();
            case 0 -> { }
            default -> System.out.println("Opción inválida.");
        }
    }

    private static void agregarEntrenador() {
        System.out.print("DNI: ");
        String dni = entrada.nextLine();
        System.out.print("Nombre: ");
        String nombre = entrada.nextLine();
        System.out.print("Apellido: ");
        String apellido = entrada.nextLine();
        System.out.print("Teléfono: ");
        String telefono = entrada.nextLine();
        System.out.print("Email: ");
        String email = entrada.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = entrada.nextLine();
        System.out.print("Salario: ");
        double salario = leerDoublePositivo("");

        servicio.agregarEntrenador(dni, nombre, apellido, telefono, email, especialidad, salario);

        DecimalFormat df = new DecimalFormat("#0.00");
        String salarioStr = df.format(salario);
        System.out.println("Entrenador: DNI: " + (dni.isEmpty() ? "-" : dni)
                + ", Nombre Completo: " + (nombre.isEmpty() ? "-" : nombre)
                + (apellido.isEmpty() ? "" : " " + apellido)
                + ", Especialidad: " + (especialidad.isEmpty() ? "-" : especialidad)
                + ", Salario: " + salarioStr + " $");
    }

    private static void listarEntrenadores() {
        List<Entrenador> entrenadores = servicio.listarEntrenadores();
        if (entrenadores.isEmpty()) {
            System.out.println("No se puede listar, ya que no hay entrenadores registrados.");
            return;
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        for (Entrenador e : entrenadores) {
            String dni = obtenerStringPorMetodos(e, "getDni", "dni", "getDocumento");
            String nombre = obtenerStringPorMetodos(e, "getNombre", "nombre");
            String apellido = obtenerStringPorMetodos(e, "getApellido", "apellido");
            String especialidad = obtenerStringPorMetodos(e, "getEspecialidad", "especialidad");
            double salario = obtenerDoublePorMetodos(e, "getSalario", "salario");

            String salarioStr = df.format(salario);

            System.out.println("Entrenador: DNI: " + (dni.isEmpty() ? "-" : dni)
                    + ", Nombre Completo: " + (nombre.isEmpty() ? "-" : nombre)
                    + (apellido.isEmpty() ? "" : " " + apellido)
                    + ", Especialidad: " + (especialidad.isEmpty() ? "-" : especialidad)
                    + ", Salario: " + salarioStr + " $");
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

        String nombre = obtenerStringPorMetodos(e, "getNombre", "nombre");
        String apellido = obtenerStringPorMetodos(e, "getApellido", "apellido");
        String especialidadActual = obtenerStringPorMetodos(e, "getEspecialidad", "especialidad");
        System.out.println("Entrenador encontrado: DNI: " + (dni.isEmpty() ? "-" : dni)
                + ", Nombre Completo: " + (nombre.isEmpty() ? "-" : nombre) + (apellido.isEmpty() ? "" : " " + apellido));
        System.out.println("Especialidad actual: " + (especialidadActual.isEmpty() ? "-" : especialidadActual));
        System.out.print("Nueva especialidad: ");
        String nuevaEsp = entrada.nextLine();
        if (nuevaEsp.isEmpty()) {
            System.out.println("Especialidad vacía. Modificación cancelada.");
            return;
        }

        try {
            Method setter = e.getClass().getMethod("setEspecialidad", String.class);
            setter.invoke(e, nuevaEsp);
            System.out.println("Especialidad actualizada correctamente.");
            return;
        } catch (Exception ignored) {}

        System.out.println("No se pudo modificar la especialidad (falta método setEspecialidad en Entrenador).");
    }

    private static void modificarSalarioEntrenador() {
        System.out.print("Ingrese DNI del entrenador a modificar: ");
        String dni = entrada.nextLine();
        Entrenador e = buscarEntrenadorPorDni(dni);

        if (e == null) {
            System.out.println("No se encontró el entrenador. Volviendo a gestión de entrenadores.");
            return;
        }

        // Mostrar datos actuales
        String nombre = obtenerStringPorMetodos(e, "getNombre", "nombre");
        String apellido = obtenerStringPorMetodos(e, "getApellido", "apellido");
        double salarioActual = obtenerDoublePorMetodos(e, "getSalario", "salario");
        DecimalFormat df = new DecimalFormat("#0.00");

        System.out.println("Entrenador encontrado: " + nombre + " " + apellido);
        System.out.println("Salario actual: " + df.format(salarioActual) + " $");

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


        int id = obtenerIdPorMetodos(e, "getId", "getIdEntrenador", "id", "getId_usuario");

        if (id != -1) {
            try {
                servicio.eliminarEntrenador(id);
                System.out.println("Entrenador eliminado correctamente.");
            } catch (Exception ex) {
                System.out.println("Error al eliminar el entrenador: " + ex.getMessage());
            }
            return;
        }

        String[] candidatos = {"eliminarEntrenadorPorDni", "eliminarPorDniEntrenador", "eliminarEntrenador", "eliminarPorDni"};
        for (String nombreMetodo : candidatos) {
            try {
                Method m = servicio.getClass().getMethod(nombreMetodo, String.class);
                m.invoke(servicio, dni);
                System.out.println("Entrenador eliminado correctamente.");
                return;
            } catch (NoSuchMethodException ignored) {
            } catch (Exception ex) {
                System.out.println("Error al eliminar el entrenador: " + ex.getMessage());
                return;
            }
        }

        System.out.println("No se pudo eliminar el entrenador: no se encontró id interno ni método de eliminación por DNI en ServicioGimnasio.");
    }

    // ======== Registrar pago de cuota ========

    private static void registrarPago() {
        System.out.println("\n--- Registrar Pago de Cuota ---");

        System.out.print("Ingrese DNI del socio: ");
        String dni = entrada.nextLine();

        Socio socio = buscarSocioPorDni(dni);

        if (socio == null) {
            System.out.println("Error: No se encontró el socio con DNI " + dni + ".");
            return;
        }

        System.out.print("Ingrese el valor base de la cuota: ");
        double valorBase = 0;

        try {
            valorBase = Double.parseDouble(entrada.nextLine());
        } catch (Exception e) {
            System.out.println("Error: debe ingresar un número válido.");
            return;
        }

        double montoCuota;

        String tipo = socio.getTipoMembresia();

        if (tipo.equalsIgnoreCase("REGULAR")) {
            montoCuota = valorBase;
        } else {
            montoCuota = valorBase * 1.5;
        }

        System.out.println("Monto final de la cuota: " + new DecimalFormat("#0.00").format(montoCuota) + " $");

        System.out.print("Ingrese el mes a pagar (formato MM-AAAA): ");
        String mesIngresado = entrada.nextLine();

        int id = obtenerIdPorMetodos(socio, "getId", "getIdSocio");

        try {
            servicio.registrarPago(id, montoCuota, mesIngresado);
            System.out.println("Pago registrado correctamente.");
            System.out.println("El socio " + socio.getNombre() + " pago su cuota.");
        } catch (Exception e) {
            System.out.println("Error al procesar el pago: " + e.getMessage());
        }
    }
}