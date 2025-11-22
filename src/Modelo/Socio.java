package Modelo;

import Utilidades.Pagable;

import java.time.LocalDate;
import java.util.Objects;

public class Socio extends Persona implements Pagable, Comparable<Socio> {

    private int idSocio;
    private String tipoMembresia; // REGULAR, PREMIUM
    private LocalDate fechaAlta;
    private boolean activo;
    private double peso;   // kg
    private double altura; // metros

    public Socio() {
    }

    public Socio(String dni, String nombre, String apellido, String telefono, String correoElectronico,
                 int idSocio, String tipoMembresia, LocalDate fechaAlta,
                 boolean activo, double peso, double altura) {
        super(dni, nombre, apellido, telefono, correoElectronico);
        this.idSocio = idSocio;
        this.tipoMembresia = tipoMembresia;
        this.fechaAlta = fechaAlta;
        this.activo = activo;
        this.peso = peso;
        this.altura = altura;
    }

    public int getIdSocio() { return idSocio; }
    public void setIdSocio(int idSocio) { this.idSocio = idSocio; }

    public String getTipoMembresia() { return tipoMembresia; }
    public void setTipoMembresia(String tipoMembresia) { this.tipoMembresia = tipoMembresia; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    @Override
    public double calcularCuota() {
        Double cuotaBase = 30000.0;
        if ("PREMIUM".equalsIgnoreCase(tipoMembresia)) {
            cuotaBase = cuotaBase * 1.5;
        }
        return cuotaBase;
    }

    @Override
    public int compareTo(Socio o) {
        return 0;
    }
}