package com.example.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "instrumentos")
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idInstrumento;

    private String nombreInstrumento;
    private String marcaInstrumento;
    private String tipoInstrumento;
    private int precioInstrumento;
    private String descripcionInstrumento;

     public Instrumento() {
        this.idInstrumento = 0;
        this.marcaInstrumento= "";
        this.tipoInstrumento= "";
        this.precioInstrumento= 0;
        this.descripcionInstrumento= "";
        this.nombreInstrumento = "";
       
    }

     public int getIdInstrumento() {
         return idInstrumento;
    }

     public void setIdInstrumento(int idInstrumento) {
         this.idInstrumento = idInstrumento;
    }

     public String getNombreInstrumento() {
         return nombreInstrumento;
    }

     public void setNombreInstrumento(String nombreInstrumento) {
         this.nombreInstrumento = nombreInstrumento;
    }

     public String getMarcaInstrumento() {
         return marcaInstrumento;
    }

     public void setMarcaInstrumento(String marcaInstrumento) {
         this.marcaInstrumento = marcaInstrumento;
    }

     public String getTipoInstrumento() {
         return tipoInstrumento;
    }

     public void setTipoInstrumento(String tipoInstrumento) {
         this.tipoInstrumento = tipoInstrumento;
    }

     public int getPrecioInstrumento() {
         return precioInstrumento;
    }

     public void setPrecioInstrumento(int precioInstrumento) {
         this.precioInstrumento = precioInstrumento;
    }

     public String getDescripcionInstrumento() {
         return descripcionInstrumento;
    }

     public void setDescripcionInstrumento(String descripcionInstrumento) {
         this.descripcionInstrumento = descripcionInstrumento;
    }
     

}


