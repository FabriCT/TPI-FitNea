package Modelo;

import java.time.LocalTime;
import java.util.Objects;

public class Entrenador extends Persona {

    private String especialidad;
    private double salario;
    private final LocalTime horaEntrada;
    private final LocalTime horaSalida;

    public Entrenador(String dni, String nombre, String apellido, String especialidad, double salario, LocalTime horaEntrada, LocalTime horaSalida) {
        super(dni, nombre, apellido);
        this.especialidad = especialidad;
        this.salario = salario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrenador entrenador = (Entrenador) o;
        return Double.compare(entrenador.salario, salario) == 0 &&
                Objects.equals(getDni(), entrenador.getDni()) &&
                Objects.equals(especialidad, entrenador.especialidad);
    }

    @Override
    public int hashCode() {
        // Incluimos los mismos campos que en el método equals
        return Objects.hash(getDni(), especialidad, salario);
    }
}