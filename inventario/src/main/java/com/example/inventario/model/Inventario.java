package com.example.inventario.model;


import jakarta.persistence.*;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idInventario;
    private int idInstrumento;
    private int stockInstrumento;
    private String sucursal;

    public Inventario() {
        this.idInventario = 0;
        this.idInstrumento = 0;
        this.stockInstrumento = 0;
        this.sucursal = "";
    }
    
    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdInstrumento() {
        return idInstrumento;
    }

    public void setIdInstrumento(int idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    public int getStockInstrumento() {
        return stockInstrumento;
    }

    public void setStockInstrumento(int stockInstrumento) {
        this.stockInstrumento = stockInstrumento;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
}
