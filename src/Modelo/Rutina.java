package Modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Rutina {
    protected List<String> ejercicios;

    public Rutina() {
        this.ejercicios = new ArrayList<>();
    }

    public void agregarEjercicio(String ejercicio) {
        this.ejercicios.add(ejercicio);
    }

    public List<String> getEjercicios() {
        return ejercicios;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String ejercicio : ejercicios) {
            sb.append("- ").append(ejercicio).append("\n");
        }
        return sb.toString();
    }
}