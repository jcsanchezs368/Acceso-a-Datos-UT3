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
    @SequenceGenerator (name = "torneo_id_seq", allocationSize = 1, initialValue = 1, sequenceName = "TORNEO_ID_SEQ")
    @GeneratedValue(generator = "torneo_id_seq", strategy = GenerationType.SEQUENCE)
    @Column (name="ID", nullable = false)
    private int id;

    @Column (name="NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column (name = "FECHA_CELEBRACION", nullable = false)
    private LocalDate fechaCelebracion;

    @Column (name="PLAZAS", nullable = false)
    private int plazas;

    @Column (name = "INSCRIPCION_ABIERTA", nullable = false)
    private boolean inscripcionAbierta;

    @Column (name="IMPORTE_INSCRIPCION", precision = 4, scale = 2)
    private BigDecimal importeInscripcion;

    @ManyToOne
    @JoinColumn (name="videojuego_id")
    private Videojuego videojuego;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable (
        schema = "GAMERGY",
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
        this.plazas = numPlazas;
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

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int numPlazas) {
        this.plazas = numPlazas;
    }

    @Override
    public String toString() {
        return "Torneo [id=" + id + ", nombre=" + nombre + ", fechaCelebracion=" + fechaCelebracion
                + ", inscripcionAbierta=" + inscripcionAbierta + ", importeInscripcion=" + importeInscripcion + "]";
    }


    
    
}
