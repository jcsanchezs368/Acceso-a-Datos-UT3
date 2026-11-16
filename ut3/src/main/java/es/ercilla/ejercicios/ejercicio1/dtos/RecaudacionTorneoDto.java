package es.ercilla.ejercicios.ejercicio1.dtos;

import java.math.BigDecimal;

public class RecaudacionTorneoDto {
    private String nombre;
    private Long numInscripciones;
    private BigDecimal recaudacion;

    
    public RecaudacionTorneoDto(String nombre, Long numInscripciones, BigDecimal recaudacion) {
        this.nombre = nombre;
        this.numInscripciones = numInscripciones;
        this.recaudacion = recaudacion;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Long getNumInscripciones() {
        return numInscripciones;
    }
    public void setNumInscripciones(Long numInscripciones) {
        this.numInscripciones = numInscripciones;
    }
    public BigDecimal getRecaudacion() {
        return recaudacion;
    }
    public void setRecaudacion(BigDecimal recaudacion) {
        this.recaudacion = recaudacion;
    }

    @Override
    public String toString() {
        return "RecaudacionTorneoDto [nombre=" + nombre + ", numInscripciones=" + numInscripciones + ", recaudacion="
                + recaudacion + "]";
    }

    
    
}
