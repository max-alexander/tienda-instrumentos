package com.example.pago.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pago")

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idPago;
    private int idCompra;
    private int montoPago;
    private String metodoPago;
    private String estadoPago;

    public Pago() {
    }

    public int getIdPago() {
        return idPago;
    }
    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }
    public int getIdCompra() {
        return idCompra;
    }
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }
    public int getMontoPago() {
        return montoPago;
    }
    public void setMontoPago(int montoPago) {
        this.montoPago = montoPago;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public String getEstadoPago() {
        return estadoPago;
    }
    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
}