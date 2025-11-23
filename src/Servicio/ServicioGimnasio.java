package Servicio;

import Modelo.Entrenador;
import Modelo.Pago; // Asegúrate de que Pago.java exista en Modelo
import Modelo.Socio;
import Excepciones.ExcepcionSocioNoEncontrado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicioGimnasio {

    private List<Socio> socios = new ArrayList<>();
    private List<Entrenador> entrenadores = new ArrayList<>();
    private List<Pago> pagos = new ArrayList<>(); // Lista para guardar los pagos

    private int siguienteIdSocio = 1;
    private int siguienteIdEntrenador = 1;

    // --- MÉTODOS PARA SOCIOS ---

    public Socio agregarSocio(String dni, String nombre, String apellido,
                              String telefono, String correoElectronico, String tipoMembresia) {

        // Creamos el socio con ID automático
        Socio socio = new Socio(dni, nombre, apellido, telefono, correoElectronico,
                siguienteIdSocio++, tipoMembresia, LocalDate.now(), true);

        socios.add(socio);
        return socio;
    }

    public void eliminarSocio(int idSocio) throws ExcepcionSocioNoEncontrado {
        boolean eliminado = socios.removeIf(socio -> socio.getIdSocio() == idSocio);
        if (!eliminado) {
            throw new ExcepcionSocioNoEncontrado("No existe un socio con id " + idSocio);
        }
    }

    // Método auxiliar para eliminar por DNI (usado por el menú)
    public void eliminarSocioPorDni(String dni) throws ExcepcionSocioNoEncontrado {
        boolean eliminado = socios.removeIf(s -> s.getDni().equals(dni));
        if (!eliminado) {
            throw new ExcepcionSocioNoEncontrado("No existe un socio con DNI " + dni);
        }
    }

    public List<Socio> listarSocios() {
        // Ordenamos por nombre (Socio implementa Comparable)
        Collections.sort(socios);
        return socios;
    }

    // --- MÉTODOS PARA ENTRENADORES ---

    public Entrenador agregarEntrenador(String dni, String nombre, String apellido,
                                        String telefono, String correoElectronico,
                                        String especialidad, double salario) {
        // Asumiendo que Entrenador tiene un constructor similar
        Entrenador entrenador = new Entrenador(dni, nombre, apellido,
                telefono, correoElectronico, especialidad, salario);

        // Si Entrenador tiene ID, se lo asignaríamos aquí.
        // Por ahora lo agregamos a la lista.
        entrenadores.add(entrenador);
        return entrenador;
    }

    public List<Entrenador> listarEntrenadores() {
        return entrenadores;
    }

    public void eliminarEntrenador(int id) {
        // Esta lógica asume que Entrenador tiene un método getId() o getIdEntrenador()
        // Si no lo tiene, te dará error aquí. En ese caso, usa eliminar por DNI.

        // Opción A: Si Entrenador tiene ID
        // boolean eliminado = entrenadores.removeIf(e -> e.getIdEntrenador() == id);

        // Opción B: (Temporal) Eliminar por índice si el ID es el índice (menos seguro)
        if (id >= 0 && id < entrenadores.size()) {
            entrenadores.remove(id);
        } else {
            System.out.println("No se encontró entrenador con ese índice/ID.");
        }
    }

    // Método extra para eliminar entrenador por DNI (Más seguro)
    public void eliminarEntrenador(String dni) {
        entrenadores.removeIf(e -> e.getDni().equals(dni));
    }

    // --- MÉTODOS PARA PAGOS ---

    public void registrarPago(Pago pago) {
        this.pagos.add(pago);
    }
}