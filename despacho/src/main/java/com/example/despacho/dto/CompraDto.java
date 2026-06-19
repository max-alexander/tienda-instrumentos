package com.example.despacho.dto;

public class CompraDto {

    private int idCompra;
    private int idUsuario;
    private int idInstrumento;
    private String estadoCompra;

    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdInstrumento() { return idInstrumento; }
    public void setIdInstrumento(int idInstrumento) { this.idInstrumento = idInstrumento; }

    public String getEstadoCompra() { return estadoCompra; }
    public void setEstadoCompra(String estadoCompra) { this.estadoCompra = estadoCompra; }
}
