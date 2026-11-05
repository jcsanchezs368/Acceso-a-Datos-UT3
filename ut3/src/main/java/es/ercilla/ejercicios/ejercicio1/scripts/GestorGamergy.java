package es.ercilla.ejercicios.ejercicio1.scripts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.UUID;

import es.ercilla.ejercicios.ejercicio1.dtos.ContactoJugadorDto;
import es.ercilla.ejercicios.ejercicio1.dtos.RecaudacionTorneoDto;
import es.ercilla.ejercicios.ejercicio1.dtos.ValoracionMediaGeneroDto;
import es.ercilla.ejercicios.ejercicio1.entidades.Jugador;
import es.ercilla.ejercicios.ejercicio1.entidades.Torneo;
import es.ercilla.ejercicios.ejercicio1.entidades.Videojuego;
import jakarta.persistence.EntityManagerFactory;

public class GestorGamergy {
    public static Videojuego insertarVideojuego(EntityManagerFactory emf, String titulo, LocalDate fechaLanzamiento, boolean multijugador, BigDecimal valoracionMedia, String genero) {
        //TODO
        return null;
    }

    public static Jugador insertarJugador(EntityManagerFactory emf, String alias, LocalDate fechaNacimiento, boolean aceptaComunicaciones, String telefono) {
        //TODO
        return null;
    }

    public static Torneo insertarTorneo(EntityManagerFactory emf, String nombre, LocalDate fechaCelebracion, int plazas, boolean inscripcionAbierta, BigDecimal importeInscripcion,int videojuegoId, UUID jugadorCodigo) {
        //TODO
        return null;
    }

    public static Jugador consultarJugadorPorCodigo(EntityManagerFactory emf, UUID codigo ) {
        //TODO
        return null;
    }

    public static void actualizarDatosTorneo(EntityManagerFactory emf, UUID id, String nombre, LocalDate fechaCelebracion, int plazas, boolean inscripcionAbierta, BigDecimal importeInscripcion) {
        //TODO
    }

    public static void borrarTorneoPorId(EntityManagerFactory emf, int id) {
        //TODO
    }

    public static List<Videojuego> consultarVideojuegosPorTitulo(EntityManagerFactory emf, String texto) {
        //TODO
        return null;
    } 
    
    public static List<Jugador> consultarJugadoresEntreFechas(EntityManagerFactory emf, LocalDate fechaDesde, LocalDate fechaHasta) {
        //TODO
        return null;
    }

    public static List<Videojuego> consultarVideojuegosSinValoracion(EntityManagerFactory emf) {
        //TODO
        return null;
    }

    public static List<Videojuego> consultarTop5VideojuegosMejorValorados(EntityManagerFactory emf) {
        //TODO
        return null;
    }

    public static List<ContactoJugadorDto> consultarAliasYTelefono(EntityManagerFactory emf) {
        //TODO
        return null;
    }

    public static List<ValoracionMediaGeneroDto> consultarValoracionMediaPorGenero(EntityManagerFactory emf) {
        //TODO
        return null;
    }

    public static List<RecaudacionTorneoDto> consultarRecaudacionTorneoPorAnyo(EntityManagerFactory emf, Year anio) {
        return null;
    }



   

}
