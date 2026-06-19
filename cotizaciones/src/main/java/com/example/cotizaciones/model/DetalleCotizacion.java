package com.example.cotizaciones.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_cotizacion")
public class DetalleCotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetalle;

    private int idInstrumento;

    private int cantidad;

    private int precioUnitario;

    private int subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cotizacion_id")
    private Cotizacion cotizacion;

    public DetalleCotizacion() {}

    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public int getIdInstrumento() { return idInstrumento; }
    public void setIdInstrumento(int idInstrumento) { this.idInstrumento = idInstrumento; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(int precioUnitario) { this.precioUnitario = precioUnitario; }

    public int getSubtotal() { return subtotal; }
    public void setSubtotal(int subtotal) { this.subtotal = subtotal; }

    public Cotizacion getCotizacion() { return cotizacion; }
    public void setCotizacion(Cotizacion cotizacion) { this.cotizacion = cotizacion; }
}
