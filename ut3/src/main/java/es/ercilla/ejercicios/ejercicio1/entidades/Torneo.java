package es.ercilla.ejercicios.ejercicio1.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity 
@Table (name = "TORNEO", schema="GAMERGY")
public class Torneo {
    @Id

    @SequenceGenerator (name = "torneo_seq", sequenceName = "TORNEO_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "torneo_seq", strategy = GenerationType.SEQUENCE)
    @Column (name="ID", nullable = false)
    private int id;

    @Column (name="NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column (name = "FECHA_CELEBRACION", nullable = false)
    private LocalDate fechaCelebracion;

    @Column (name="NUM_PLAZAS", nullable = false)
    private int numPlazas;

    @Column (name = "INSCRIPCION_ABIERTA", nullable = false)
    private boolean inscripcionAbierta;

    @Column (name="IMPORTE_INSCRIPCION", precision = 4, scale = 2)
    private BigDecimal importeInscripcion;

    @ManyToOne
    @JoinColumn (name="videojuego_id")
    private Videojuego videojuego;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable (
        name="INSCRIPCION",
        joinColumns = @JoinColumn (name="torneo_id"),
        inverseJoinColumns = @JoinColumn (name="jugador_codigo")
    )
    private List<Jugador> jugadores = new ArrayList<Jugador>();

    public Torneo(){

    }

    public Torneo(String nombre, LocalDate fechaCelebracion, int numPlazas, boolean inscripcionAbierta,
            BigDecimal importeInscripcion) {
        this.nombre = nombre;
        this.fechaCelebracion = fechaCelebracion;
        this.numPlazas = numPlazas;
        this.inscripcionAbierta = inscripcionAbierta;
        this.importeInscripcion = importeInscripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCelebracion() {
        return fechaCelebracion;
    }

    public void setFechaCelebracion(LocalDate fechaCelebracion) {
        this.fechaCelebracion = fechaCelebracion;
    }

    public boolean isInscripcionAbierta() {
        return inscripcionAbierta;
    }

    public void setInscripcionAbierta(boolean inscripcionAbierta) {
        this.inscripcionAbierta = inscripcionAbierta;
    }

    public BigDecimal getImporteInscripcion() {
        return importeInscripcion;
    }

    public void setImporteInscripcion(BigDecimal importeInscripcion) {
        this.importeInscripcion = importeInscripcion;
    }

    public Videojuego getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
    }

    @Override
    public String toString() {
        return "Torneo [id=" + id + ", nombre=" + nombre + ", fechaCelebracion=" + fechaCelebracion
                + ", inscripcionAbierta=" + inscripcionAbierta + ", importeInscripcion=" + importeInscripcion + "]";
    }


    
    
}
