package es.ercilla.ejercicios.ejercicio1.dtos;

import es.ercilla.ejercicios.ejercicio1.entidades.GeneroVideojuego;

public class ValoracionMediaGeneroDto {
    private GeneroVideojuego genero;
    private String valoracionMedia;

    
    public ValoracionMediaGeneroDto(GeneroVideojuego genero, Double valoracionMedia) {
        this.genero = genero;
        if(valoracionMedia == null  || valoracionMedia == 0){
            this.valoracionMedia = "Sin valoraciones";
        }else{
            this.valoracionMedia = String.valueOf(valoracionMedia);
        }
        
    }

    public GeneroVideojuego getGenero() {
        return genero;
    }
    public void setGenero(GeneroVideojuego genero) {
        this.genero = genero;
    }
    public String getValoracionMedia() {
        return valoracionMedia;
    }
    public void setValoracionMedia(String valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }

    @Override
    public String toString() {
        return "ValoracionMediaGeneroDto [genero=" + genero + ", valoracionMedia=" + valoracionMedia + "]";
    }

    
    
}
