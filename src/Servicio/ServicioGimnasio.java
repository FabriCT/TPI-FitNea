package Servicio;

import Modelo.Entrenador;
import Modelo.Pago;
import Modelo.Socio;
import Modelo.Rutina;
import Excepciones.ExcepcionSocioNoEncontrado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicioGimnasio {

    private int siguienteIdSocio = 1;
    private List<Socio> socios = new ArrayList<>();

    private int siguienteIdEntrenador = 1;
    private List<Entrenador> entrenadores = new ArrayList<>();

    private int siguienteIdPago = 1;
    private List<Pago> pagos = new ArrayList<>();

    public Socio buscarSocioPorId(int id) {
        for (Socio s : socios) {
            if (s.getIdSocio() == id) {
                return s;
            }
        }
        return null;
    }

    public Socio agregarSocio(String dni, String nombre, String apellido,
                              String telefono, String correoElectronico, String tipoMembresia) {
        Socio socio = new Socio(dni, nombre, apellido, telefono, correoElectronico,
                siguienteIdSocio++, tipoMembresia, LocalDate.now(), false);
        socios.add(socio);
        return socio;
    }

    public void eliminarSocio(int idSocio) throws ExcepcionSocioNoEncontrado {
        boolean eliminado = socios.removeIf(socio -> socio.getIdSocio() == idSocio);
        if (!eliminado) {
            throw new ExcepcionSocioNoEncontrado("No existe un socio con id " + idSocio);
        }
    }

    public List<Socio> listarSocios() {
        Collections.sort(socios);
        return socios;
    }

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

    public void registrarPago(int idSocio, double monto, String mesCorrespondiente) throws ExcepcionSocioNoEncontrado {
        Socio socio = buscarSocioPorId(idSocio);
        if (socio == null) {
            throw new ExcepcionSocioNoEncontrado("Socio con ID " + idSocio + " no encontrado.");
        }
        Pago nuevoPago = new Pago(siguienteIdPago++, socio, LocalDate.now(), monto, mesCorrespondiente);
        this.pagos.add(nuevoPago);
        socio.setActivo(true);
    }

    public List<Pago> listarPagos() {
        return pagos;
    }

    public void asignarRutinaSocio(String dniSocio, Rutina rutina) throws ExcepcionSocioNoEncontrado {
        Socio socio = listarSocios().stream()
                .filter(s -> s.getDni().equals(dniSocio))
                .findFirst()
                .orElseThrow(() -> new ExcepcionSocioNoEncontrado("No se encontró un socio con el DNI proporcionado."));
        socio.setRutina(rutina);
    }
}