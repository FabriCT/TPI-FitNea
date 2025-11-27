package Utilidades;

public class ListaEnlazadaLog<T> {
    private Nodo<T> cabeza;
    private int tamano;

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamano++;
    }

    public void mostrarLog() {
        if (cabeza == null) {
            System.out.println("Historial vacío.");
            return;
        }
        System.out.println("\n--- HISTORIAL DE ACCIONES (Estructura Propia) ---");
        Nodo<T> actual = cabeza;
        while (actual != null) {
            System.out.println(">> " + actual.dato);
            actual = actual.siguiente;
        }
        System.out.println("Total acciones registradas: " + tamano);
    }
}