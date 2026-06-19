package com.example.cotizaciones.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int idCotizacion;

        private int idCliente;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

        private int montoTotal = 0;

    private String estado = "PENDIENTE";

    @JsonManagedReference
    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCotizacion> detalles = new ArrayList<>();

    public Cotizacion() {}

        public int getIdCotizacion() { return idCotizacion; }
        public void setIdCotizacion(int idCotizacion) { this.idCotizacion = idCotizacion; }

        public int getIdCliente() { return idCliente; }
        public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

        public int getMontoTotal() { return montoTotal; }
        public void setMontoTotal(int montoTotal) { this.montoTotal = montoTotal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetalleCotizacion> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleCotizacion> detalles) {
        this.detalles.clear();
        if (detalles != null) {
            detalles.forEach(this::addDetalle);
        }
    }

    public void addDetalle(DetalleCotizacion d) {
        d.setCotizacion(this);
        this.detalles.add(d);
    }

    public void removeDetalle(DetalleCotizacion d) {
        d.setCotizacion(null);
        this.detalles.remove(d);
    }
}
