package Modelo;

import java.time.LocalDate;


public class Pago {

    private int idPago;
    private Socio socio;
    private LocalDate fechaPago;
    private double monto;
    private String mesCorrespondiente;

    public Pago(int idPago, Socio socio, LocalDate fechaPago,
                double monto, String mesCorrespondiente) {
        this.idPago = idPago;
        this.socio = socio;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.mesCorrespondiente = mesCorrespondiente;
    }

    public int getIdPago() { return idPago; }
    public Socio getSocio() { return socio; }
    public LocalDate getFechaPago() { return fechaPago; }
    public double getMonto() { return monto; }
    public String getMesCorrespondiente() { return mesCorrespondiente; }

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