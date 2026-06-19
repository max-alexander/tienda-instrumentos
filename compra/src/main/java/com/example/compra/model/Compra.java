package com.example.compra.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compra")

public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idCompra;
    private int idInstrumento;
    private int cantidad;
    private int montoTotal;
    private String metodoPago;
    private String estadoCompra;

    public Compra() {
    }
    public int getIdCompra() {
        return idCompra;
    }
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdInstrumento() {
        return idInstrumento;
    }
    public void setIdInstrumento(int idInstrumento) {
        this.idInstrumento = idInstrumento;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getMontoTotal() {
        return montoTotal;
    }
    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public String getEstadoCompra() {
        return estadoCompra;
    }
    public void setEstadoCompra(String estadoCompra) {
        this.estadoCompra = estadoCompra;
    }
}