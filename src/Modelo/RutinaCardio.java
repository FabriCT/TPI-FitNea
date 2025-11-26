package Modelo;

public class RutinaCardio extends Rutina {
    private final int duracion; // en minutos
    private final int frecuenciaCardiaca; // en ppm

    public RutinaCardio(int duracion, int frecuenciaCardiaca) {
        super();
        this.duracion = duracion;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    @Override
    public String toString() {
        return "--- Rutina de Cardio ---\n" +
                "Duración: " + duracion + " minutos\n" +
                "Frecuencia Cardíaca: " + frecuenciaCardiaca + " ppm\n" +
                "Ejercicios:\n" + super.toString();
    }
}