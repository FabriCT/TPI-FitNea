package Modelo;

public abstract class Persona {
    private final String dni;
    private final String nombre;
    private final String apellido;

    public Persona(String dni, String nombre, String apellido) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (DNI: " + dni + ")";
    }
}