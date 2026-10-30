package es.ercilla.modelos;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity 
@Table (name="EJEMPLAR", schema = "LIBRERIA")
public class Ejemplar {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    @Column(name ="CODIGO")
    private UUID codigo;

    @Column (nullable = false)
    private boolean prestado;

    @Column(name = "FECHA_FIN_PRESTAMO")
    private LocalDate fechaFinPrestamo;

    @ManyToOne (optional = false) //Clave FK obligatoria
    @JoinColumn (name = "LIBRO_ID", nullable = false) // atributo que se relaciona con la entity Libro
    private Libro libro;


    public Ejemplar() {

    }

    public Ejemplar(boolean prestado, LocalDate fechaFinPrestamo) {
        this.prestado = prestado;
        this.fechaFinPrestamo = fechaFinPrestamo;
    }

    public UUID getCodigo() {
        return codigo;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }

    public LocalDate getFechaFinPrestamo() {
        return fechaFinPrestamo;
    }

    public void setFechaFinPrestamo(LocalDate fechaFinPrestamo) {
        this.fechaFinPrestamo = fechaFinPrestamo;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "Ejemplar [codigo=" + codigo + ", prestado=" + prestado + ", fechaFinPrestamo=" + fechaFinPrestamo
                + ", libro=" + libro + "]";
    }
}
