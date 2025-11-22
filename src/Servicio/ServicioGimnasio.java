package Servicio;

import Modelo.Entrenador;
import Modelo.Socio;
import Excepciones.ExcepcionSocioNoEncontrado;
import Modelo.*;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicioGimnasio {

    private List<Socio> socios = new ArrayList<>();
    private List<Entrenador> entrenadores = new ArrayList<>();
    private List<ClaseGrupal> clasesGrupales = new ArrayList<>();
    private List<Pago> pagos = new ArrayList<>();

    private String[][] grillaHorarios = new String[6][4];

    private int siguienteIdSocio = 1;
    private int siguienteIdClase = 1;
    private int siguienteIdPago = 1;


    public Socio agregarSocio(String dni, String nombre, String apellido,
                              String telefono, String correoElectronico, {

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
        boolean eliminado = socios.removeIf(socio -> {
            String sdni = null;
            try { sdni = socio.getDni(); } catch (Exception ignored) {}
            return sdni != null && sdni.equalsIgnoreCase(dni);
        });
        if (!eliminado) {
            throw new ExcepcionSocioNoEncontrado("No existe un socio con DNI " + dni);
        }
    }

    public void eliminarSocio(String dni) throws ExcepcionSocioNoEncontrado {
        eliminarSocioPorDni(dni);
    }

    public Socio buscarSocioPorId(int idSocio) throws ExcepcionSocioNoEncontrado {
        return socios.stream()
                .filter(socio -> socio.getIdSocio() == idSocio)
                .findFirst()
                .orElseThrow(() -> new ExcepcionSocioNoEncontrado("Socio no encontrado."));
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

    public void eliminarEntrenadorPorDni(String dni) {
        boolean eliminado = entrenadores.removeIf(entrenador -> {
            String edni = null;
            try { edni = entrenador.getDni(); } catch (Exception ignored) {}
            return edni != null && edni.equalsIgnoreCase(dni);
        });
        if (!eliminado) {
            throw new RuntimeException("No existe un entrenador con DNI " + dni);
        }
    }

    public void eliminarEntrenador(String dni) {
        eliminarEntrenadorPorDni(dni);
    }

    public void eliminarEntrenador(int id) {
        boolean eliminado = false;
        for (int i = 0; i < entrenadores.size(); i++) {
            Entrenador e = entrenadores.get(i);
            Integer eid = obtenerIdPorMetodos(e, "getId", "getIdEntrenador", "getId_usuario", "id");
            if (eid != null && eid == id) {
                entrenadores.remove(i);
                eliminado = true;
                break;
            }
        }
        if (!eliminado) {
            throw new RuntimeException("No existe un entrenador con id " + id);
        }
    }

    private Integer obtenerIdPorMetodos(Object obj, String... nombresMetodos) {
        if (obj == null) return null;
        for (String nombre : nombresMetodos) {
            try {
                Method m = obj.getClass().getMethod(nombre);
                Object res = m.invoke(obj);
                if (res instanceof Number) return ((Number) res).intValue();
                if (res instanceof String) {
                    try { return Integer.parseInt((String) res); } catch (Exception ignored) {}
                }
            } catch (NoSuchMethodException ignored) {
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
