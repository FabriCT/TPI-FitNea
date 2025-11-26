package Modelo;

public class RutinaFuerza extends Rutina {
    private final int series;
    private final int repeticiones;
    private final double peso; // en kg

    public RutinaFuerza(int series, int repeticiones, double peso) {
        super();
        this.series = series;
        this.repeticiones = repeticiones;
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "--- Rutina de Fuerza ---\n" +
                "Series: " + series + "\n" +
                "Repeticiones: " + repeticiones + "\n" +
                "Peso: " + peso + " kg\n" +
                "Ejercicios:\n" + super.toString();
    }
}