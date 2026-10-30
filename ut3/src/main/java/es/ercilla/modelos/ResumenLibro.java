package es.ercilla.modelos;

import java.math.BigDecimal;

// DTO - Data Transfer Object = Clase auxiliar para procesar datos
public class ResumenLibro {
    private String titulo;
    private BigDecimal calificacionMedia;

    public ResumenLibro(){
        super();
    }
    
    public ResumenLibro(String titulo, BigDecimal calificacionMedia) {
        this.titulo = titulo;
        this.calificacionMedia = calificacionMedia;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public BigDecimal getCalificacionMedia() {
        return calificacionMedia;
    }
    public void setCalificacionMedia(BigDecimal calificacionMedia) {
        this.calificacionMedia = calificacionMedia;
    }

    
}
