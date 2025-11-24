package Modelo;

public class Entrenador extends Persona {

    private String especialidad;
    private double salario;

    public Entrenador(String dni, String nombre, String apellido,
                      String especialidad, double salario) {
        super(dni, nombre, apellido);
        this.especialidad = especialidad;
        this.salario = salario;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

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