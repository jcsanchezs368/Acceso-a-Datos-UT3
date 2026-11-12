package es.ercilla.ejercicios.ejercicio1.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table (name = "JUGADOR", schema = "GAMERGY")
public class Jugador {

    @Id 
    @SequenceGenerator (name = "jugador_seq", sequenceName = "JUGADOR_SEQ", allocationSize = 1)
    @GeneratedValue (generator = "jugador_seq", strategy = GenerationType.UUID)
    @Column (name = "CODIGO", nullable = false)
    private UUID codigo;

    @Column (name = "ALIAS", nullable = false, length = 30)
    private String alias;

    @Column (name = "FECHA_NACIMIENTO", nullable = false)
    private LocalDate fechaNacimiento;

    @Column (name = "ACEPTA_COMUNICACIONES", nullable = false)
    private boolean aceptaComunicaciones;

    @Column (name = "TELEFONO")
    private String telefono;

    @ManyToMany (mappedBy = "jugadores", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Torneo> torneos = new ArrayList<Torneo>();

    public Jugador(){

    }

    public Jugador(String alias, LocalDate fechaNacimiento, boolean aceptaComunicaciones, String telefono) {
        this.alias = alias;
        this.fechaNacimiento = fechaNacimiento;
        this.aceptaComunicaciones = aceptaComunicaciones;
        this.telefono = telefono;
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(UUID codigo) {
        this.codigo = codigo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isAceptaComunicaciones() {
        return aceptaComunicaciones;
    }

    public void setAceptaComunicaciones(boolean aceptaComunicaciones) {
        this.aceptaComunicaciones = aceptaComunicaciones;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Torneo> getTorneos() {
        return torneos;
    }

    public void setTorneos(List<Torneo> torneos) {
        this.torneos = torneos;
    }

    @Override
    public String toString() {
        return "Jugador [codigo=" + codigo + ", alias=" + alias + ", fechaNacimiento=" + fechaNacimiento
                + ", aceptaComunicaciones=" + aceptaComunicaciones + ", telefono=" + telefono + "]";
    }

    





}
