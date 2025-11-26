package Modelo;

import java.time.LocalTime;

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

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    @Override
    public String toString() {
        return "Entrenador{" + super.toString() +
                ", especialidad='" + especialidad + '\'' +
                ", salario=" + salario +
                '}';
    }
}