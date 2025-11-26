package Servicio;

import Modelo.Entrenador;
import Modelo.Pago;
import Modelo.Socio;
import Modelo.Rutina;
import Excepciones.ExcepcionSocioNoEncontrado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ServicioGimnasio {

    private final AtomicInteger siguienteIdSocio = new AtomicInteger(1);
    private final List<Socio> socios = new ArrayList<>();

    private final List<Entrenador> entrenadores = new ArrayList<>();

    private final AtomicInteger siguienteIdPago = new AtomicInteger(1);
    private final List<Pago> pagos = new ArrayList<>();

    public Socio buscarSocioPorId(int id) {
        return socios.stream()
                .filter(s -> s.getIdSocio() == id)
                .findFirst()
                .orElse(null);
    }

    public Socio agregarSocio(String dni, String nombre, String apellido, String tipoMembresia) {
        Socio socio = new Socio(dni, nombre, apellido,
                siguienteIdSocio.getAndIncrement(), tipoMembresia, false);
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
    //------MEMBRESIAS------
    public List<Socio> buscarMembresiasPorVencer(int dias) {
        List<Socio> resultado = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(dias);

        for (Socio s : socios) {
            LocalDate venc = s.getFechaVencimiento();
            if (venc != null && !venc.isBefore(hoy) && !venc.isAfter(limite)) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    public List<Socio> buscarSociosConMora() {
        List<Socio> resultado = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        for (Socio s : socios) {
            LocalDate venc = s.getFechaVencimiento();
            if (venc != null && venc.isBefore(hoy)) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    public List<Socio> buscarSociosActivos() {
        return socios.stream()
                .filter(s -> s.isActivo() && s.tieneCuotaAlDia())
                .toList();
    }

    // --- MÉTODOS PARA ENTRENADORES ---

    public Entrenador agregarEntrenador(String dni, String nombre, String apellido,
                                        String especialidad, double salario, LocalTime horaEntrada, LocalTime horaSalida) {
        Entrenador entrenador = new Entrenador(dni, nombre, apellido, especialidad, salario, horaEntrada, horaSalida);
        entrenadores.add(entrenador);
        return entrenador;
    }

    public List<Entrenador> listarEntrenadores() {
        return new ArrayList<>(entrenadores);
    }

    public void eliminarEntrenador(String dni) {
        entrenadores.removeIf(e -> e.getDni().equalsIgnoreCase(dni));
    }

    public void registrarPago(int idSocio, double monto, String mesCorrespondiente) throws ExcepcionSocioNoEncontrado {
        Socio socio = buscarSocioPorId(idSocio);
        if (socio == null) {
            throw new ExcepcionSocioNoEncontrado("Socio con ID " + idSocio + " no encontrado.");
        }
        LocalDate fechaPago = LocalDate.now();
        Pago nuevoPago = new Pago(siguienteIdPago.getAndIncrement(), socio, fechaPago, monto, mesCorrespondiente);
        this.pagos.add(nuevoPago);
        socio.setActivo(true);
        socio.setFechaVencimiento(fechaPago.plusMonths(1));
    }

    public List<Pago> listarPagos() {
        return new ArrayList<>(pagos);
    }

    public void asignarRutinaSocio(String dniSocio, Rutina rutina) throws ExcepcionSocioNoEncontrado {
        Socio socio = socios.stream()
                .filter(s -> s.getDni().equals(dniSocio))
                .findFirst()
                .orElseThrow(() -> new ExcepcionSocioNoEncontrado("No se encontró un socio con el DNI proporcionado."));
        socio.setRutina(rutina);
    }
}