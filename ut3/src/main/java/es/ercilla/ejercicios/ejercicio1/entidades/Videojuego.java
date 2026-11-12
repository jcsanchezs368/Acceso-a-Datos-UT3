package es.ercilla.ejercicios.ejercicio1.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity
@Table(name = "VIDEOJUEGO", schema = "GAMERGY")
public class Videojuego {
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
    private BigDecimal valoracionMedia;

    @Column(name="GENERO", nullable = false)
    private GeneroVideojuego genero;

    @OneToMany (mappedBy = "videojuego")
    private List<Torneo> torneos = new ArrayList<Torneo>();
    
    public Videojuego(){

    }

    public Videojuego(String titulo, LocalDate fechaLanzamiento, boolean multijugador, BigDecimal valoracionMedia,
            String genero) {
        this.titulo = titulo;
        this.fechaLanzamiento = fechaLanzamiento;
        this.multijugador = multijugador;
        this.valoracionMedia = valoracionMedia;
        try {
            this.genero = GeneroVideojuego.valueOf(genero.toUpperCase());    
        } catch (IllegalArgumentException e) {
            System.err.println("NO EXISTE EL GÉNERO INTRODUCIDO");
        }
        
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

    public BigDecimal getValoracionMedia() {
        return valoracionMedia;
    }

    public void setValoracionMedia(BigDecimal valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }

    public GeneroVideojuego getGenero() {
        return genero;
    }

    public void setGenero(GeneroVideojuego genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Videojuego [id=" + id + ", titulo=" + titulo + ", fechaLanzamiento=" + fechaLanzamiento
                + ", multijugador=" + multijugador + ", valoracionMedia=" + valoracionMedia + ", genero=" + genero
                + "]";
    }

    

}
