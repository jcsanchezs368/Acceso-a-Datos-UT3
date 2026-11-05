package es.ercilla.ejercicios.ejercicio1.entidades;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity
@Table(name = "VIDEOJUEGO", schema = "GAMERGY")
public class Videojuego {
    //TODO

    @Id
    @GeneratedValue(generator = "videojuego_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "videojuego_seq", allocationSize = 1, sequenceName = "VIDEOJUEGO_SEQ")
    @Column(name="ID", nullable = false)
    private int id;

    @Column(name = "TITULO", nullable = false, unique = true)
    private String titulo;

    @Column(name = "FECHA_LANZAMIENTO", nullable = false)
    private LocalDate fechaLanzamiento;

    @Column(name = "MULTIJUGADOR", nullable = false)
    private boolean multijugador;

    @Column(name = "VALORACION_MEDIA")
    private Float valoracionMedia;

    @Column(name="GENERO", nullable = false)
    private GeneroVideojuego genero;

    public Videojuego(){

    }

    

    public Videojuego(String titulo, LocalDate fechaLanzamiento, boolean multijugador, Float valoracionMedia,
            GeneroVideojuego genero) {
        this.titulo = titulo;
        this.fechaLanzamiento = fechaLanzamiento;
        this.multijugador = multijugador;
        this.valoracionMedia = valoracionMedia;
        this.genero = genero;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public boolean isMultijugador() {
        return multijugador;
    }

    public void setMultijugador(boolean multijugador) {
        this.multijugador = multijugador;
    }

    public Float getValoracionMedia() {
        return valoracionMedia;
    }

    public void setValoracionMedia(Float valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }

    public GeneroVideojuego getGenero() {
        return genero;
    }

    public void setGenero(GeneroVideojuego genero) {
        this.genero = genero;
    }

    


}
