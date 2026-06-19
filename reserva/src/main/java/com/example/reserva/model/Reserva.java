package com.example.reserva.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReserva;
    private Integer idInstrumento;
    private Integer idUsuario;
    private Integer idSucursal;
    private LocalDate fechaVisita;
    private String estadoReserva;

    
    public Reserva() {
    }


    public Reserva(Integer idInstrumento, Integer idReserva, Integer idUsuario, Integer idSucursal,
            LocalDate fechaVisita, String estadoReserva) {
        this.idReserva = idReserva;
        this.idInstrumento = idInstrumento;
        this.idUsuario = idUsuario;
        this.idSucursal = idSucursal;
        this.fechaVisita = fechaVisita;
        this.estadoReserva = estadoReserva;
    }


    public Integer getIdInstrumento() {
        return idInstrumento;
    }


    public void setIdInstrumento(Integer idInstrumento) {
        this.idInstrumento = idInstrumento;
    }


    public Integer getIdReserva() {
        return idReserva;
    }


    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }


    public Integer getIdUsuario() {
        return idUsuario;
    }


    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }


    public Integer getIdSucursal() {
        return idSucursal;
    }


    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }


    public LocalDate getFechaVisita() {
        return fechaVisita;
    }


    public void setFechaVisita(LocalDate fechaVisita) {
        this.fechaVisita = fechaVisita;
    }


    public String getEstadoReserva() {
        return estadoReserva;
    }


    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
}

  