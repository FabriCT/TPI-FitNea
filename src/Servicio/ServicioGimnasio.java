package Servicio;

import Modelo.Entrenador;
import Modelo.Pago;
import Modelo.Socio;
import Excepciones.ExcepcionSocioNoEncontrado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicioGimnasio {

    // --- ATRIBUTOS (Declarados una sola vez) ---
    private int siguienteIdSocio = 1;
    private List<Socio> socios = new ArrayList<>();

    private int siguienteIdEntrenador = 1;
    private List<Entrenador> entrenadores = new ArrayList<>();

    private int siguienteIdPago = 1;
    private List<Pago> pagos = new ArrayList<>();


    // --- MÉTODOS AUXILIARES ---

    public Socio buscarSocioPorId(int id) {
        for (Socio s : socios) {
            if (s.getIdSocio() == id) {
                return s;
            }
        }
        return null;
    }

    // --- MÉTODOS PARA SOCIOS ---

    public Socio agregarSocio(String dni, String nombre, String apellido,
                              String telefono, String correoElectronico, String tipoMembresia) {
        Socio socio = new Socio(dni, nombre, apellido, telefono, correoElectronico,
                siguienteIdSocio++, tipoMembresia, LocalDate.now(), true);
        socios.add(socio);
        return socio;
    }

    // Sobrecarga para cuando se pasan peso y altura (si lo usas)
    public Socio agregarSocio(String dni, String nombre, String apellido,
                              String telefono, String correoElectronico, String tipoMembresia, double peso, double altura) {
        // Nota: Si tu constructor de Socio tiene peso y altura, úsalos aquí.
        // Si no, usa el constructor estándar.
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

    public void eliminarSocioPorDni(String dni) throws ExcepcionSocioNoEncontrado {
        boolean eliminado = socios.removeIf(s -> s.getDni().equals(dni));
        if (!eliminado) {
            throw new ExcepcionSocioNoEncontrado("No existe un socio con DNI " + dni);
        }
    }

    public List<Socio> listarSocios() {
        Collections.sort(socios);
        return socios;
    }

    // --- MÉTODOS PARA ENTRENADORES ---

    public Entrenador agregarEntrenador(String dni, String nombre, String apellido,
                                        String telefono, String correoElectronico,
                                        String especialidad, double salario) {
        Entrenador entrenador = new Entrenador(dni, nombre, apellido,
                telefono, correoElectronico, especialidad, salario);
        entrenadores.add(entrenador);
        return entrenador;
    }

    public List<Entrenador> listarEntrenadores() {
        return entrenadores;
    }

    public void eliminarEntrenador(int id) {
        if (id >= 0 && id < entrenadores.size()) {
            entrenadores.remove(id);
        }
    }

    public void eliminarEntrenador(String dni) {
        entrenadores.removeIf(e -> e.getDni().equals(dni));
    }

    // --- MÉTODOS PARA PAGOS ---


    public void registrarPago(int idSocio, double monto, String mesCorrespondiente) throws ExcepcionSocioNoEncontrado {

        Socio socio = buscarSocioPorId(idSocio);

        if (socio == null) {
            throw new ExcepcionSocioNoEncontrado("Socio con ID " + idSocio + " no encontrado.");
        }

        Pago nuevoPago = new Pago(
                siguienteIdPago++,
                socio,
                LocalDate.now(),
                monto,
                mesCorrespondiente
        );

        this.pagos.add(nuevoPago);

        socio.setActivo(true);
    }

    public List<Pago> listarPagos() {
        return pagos;
    }
}