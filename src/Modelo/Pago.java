package Modelo;

import java.time.LocalDate;

public record Pago(int idPago, Socio socio, LocalDate fechaPago, double monto, String mesCorrespondiente) {
    @Override
    public String toString() {
        return "Pago{" +
                "id=" + idPago +
                ", socio=" + socio.getNombre() +
                ", fecha=" + fechaPago +
                ", mes='" + mesCorrespondiente + '\'' +
                ", monto=" + monto +
                '}';
    }
}