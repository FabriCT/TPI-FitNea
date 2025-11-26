package Servicio;

import Excepciones.ExcepcionSocioNoEncontrado;
import Modelo.Entrenador;
import Modelo.Pago;
import Modelo.Rutina;
import Modelo.Socio;

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
        for (Socio s : socios) {
            if (s.getIdSocio() == id) {
                return s;
            }
        }
        return null;
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

    public Entrenador agregarEntrenador(String dni, String nombre, String apellido, String especialidad, double salario) {
        // Los horarios se definen por defecto al crear un entrenador desde aquí.
        Entrenador entrenador = new Entrenador(dni, nombre, apellido, especialidad, salario, LocalTime.of(8, 0), LocalTime.of(18, 0));
        entrenadores.add(entrenador);
        return entrenador;
    }

    public List<Entrenador> listarEntrenadores() {
        return entrenadores;
    }

    public void eliminarEntrenador(String dni) {
        entrenadores.removeIf(e -> e.getDni().equalsIgnoreCase(dni));
    }

    public void registrarPago(int idSocio, double monto, String mesCorrespondiente) throws ExcepcionSocioNoEncontrado {
        Socio socio = buscarSocioPorId(idSocio);
        if (socio == null) {
            throw new ExcepcionSocioNoEncontrado("Socio con ID " + idSocio + " no encontrado.");
        }
        Pago nuevoPago = new Pago(siguienteIdPago.getAndIncrement(), socio, LocalDate.now(), monto, mesCorrespondiente);
        this.pagos.add(nuevoPago);
        socio.setActivo(true);
        socio.setFechaVencimiento(LocalDate.now().plusMonths(1));
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
