package es.ercilla.ejercicios.ejercicio1.scripts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import es.ercilla.ejercicios.ejercicio1.dtos.ContactoJugadorDto;
import es.ercilla.ejercicios.ejercicio1.dtos.RecaudacionTorneoDto;
import es.ercilla.ejercicios.ejercicio1.dtos.ValoracionMediaGeneroDto;
import es.ercilla.ejercicios.ejercicio1.entidades.Jugador;
import es.ercilla.ejercicios.ejercicio1.entidades.Torneo;
import es.ercilla.ejercicios.ejercicio1.entidades.Videojuego;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class GestorGamergy {

    public static void main(String[] args) {

        Scanner scanner = null;

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("ad-clase");) {
            // a. Insertar un videojuego
            System.out.println(insertarVideojuego(emf, "Minecraft", LocalDate.of(2015, 1, 1), true, new BigDecimal("8.75"), "aventura"));
            System.out.println(insertarVideojuego(emf, "Outlast", LocalDate.of(2016, 2, 13), false, new BigDecimal("7.75"), "terror"));
            // b. Insertar un jugador

            // c. Crear un torneo e inscribir a un jugador

            // d. Consultar un jugador por su código

            // e. Actualizar los datos de un torneo

            // f. Borrar un torneo

            // g. Buscar videojuegos por título

            // h. Buscar jugadores entre dos fechas

            // i. Consultar videojuegos sin valoración

            // j. Consultar los cinco videojuegos mejor valorados

            // k. Consultar los datos de contacto

            // l. Consultar la valoración media por género

            // m. Consultar la recaudación de todos los torneos de un año concreto
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(scanner != null){
                scanner.close();
            }
        }
    }
    

    public static Videojuego insertarVideojuego(EntityManagerFactory emf, String titulo, LocalDate fechaLanzamiento, boolean multijugador, BigDecimal valoracionMedia, String genero) {
        EntityManager em = null;
        EntityTransaction tx = null;
        Videojuego videojuego = null;
        try{
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            videojuego = new Videojuego(titulo, fechaLanzamiento, multijugador, valoracionMedia, genero);
            em.persist(videojuego);
            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()){
                tx.rollback();
            }
        } finally{
            if(em != null){
                em.close();
            }
        }
        return videojuego;
    }

    public static Jugador insertarJugador(EntityManagerFactory emf, String alias, LocalDate fechaNacimiento, boolean aceptaComunicaciones, String telefono) {
        Jugador jugador = null;
        EntityManager em = null;
        EntityTransaction tx = null;
        try{
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            jugador = new Jugador(alias,fechaNacimiento,aceptaComunicaciones,telefono);
            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()){
                tx.rollback();
            }
        } finally{
            if(em != null){
                em.close();
            }
        }
        return jugador;
    }

    public static Torneo insertarTorneo(EntityManagerFactory emf, String nombre, LocalDate fechaCelebracion, int plazas, boolean inscripcionAbierta, BigDecimal importeInscripcion,int videojuegoId, UUID jugadorCodigo) {
        Torneo torneo = null;
        EntityManager em = null;
        EntityTransaction tx = null;
        try{
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            torneo = new Torneo(nombre, fechaCelebracion, plazas, inscripcionAbierta, importeInscripcion);

            Jugador jugador = em.find(Jugador.class, jugadorCodigo);
            Videojuego videojuego = em.find(Videojuego.class, videojuegoId);

            if(jugador == null || videojuego == null){
                return null;
            }

            torneo.getJugadores().add(jugador);
            jugador.getTorneos().add(torneo);

            em.persist(torneo);

            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()){
                tx.rollback();
            }
        } finally{
            if(em != null){
                em.close();
            }
        }
        return null;
    }

    public static Jugador consultarJugadorPorCodigo(EntityManagerFactory emf, UUID codigo ) {
        EntityManager em = emf.createEntityManager();
        return em.find(Jugador.class, codigo);
    }

    public static void actualizarDatosTorneo(EntityManagerFactory emf, int id, String nombre, LocalDate fechaCelebracion, int plazas, boolean inscripcionAbierta, BigDecimal importeInscripcion) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try{
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Torneo torneo = em.find(Torneo.class, id);
            
            if(torneo == null){
                System.err.println("El torneo no existe");
                return;
            }
            torneo.setNombre(nombre);
            torneo.setFechaCelebracion(fechaCelebracion);
            torneo.setNumPlazas(plazas);
            torneo.setInscripcionAbierta(inscripcionAbierta);
            torneo.setImporteInscripcion(importeInscripcion);

            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()){
                tx.rollback();
            }
        } finally{
            if(em != null){
                em.close();
            }
        }
    }

    public static void borrarTorneoPorId(EntityManagerFactory emf, int id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try{
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Torneo torneo = em.find(Torneo.class, id);
            
            if(torneo == null){
                System.err.println("El torneo no existe");
                return;
            }

            em.remove(torneo);

            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()){
                tx.rollback();
            }
        } finally{
            if(em != null){
                em.close();
            }
        }
    }

    public static List<Videojuego> consultarVideojuegosPorTitulo(EntityManagerFactory emf, String texto) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Videojuego> consulta = em.createQuery("SELECT v FROM Videojuego v WHERE LOWER(v.titulo) LIKE LOWER(:texto)", Videojuego.class).setParameter("texto", "%" + texto + "%");
        List<Videojuego> videojuegos = consulta.getResultList();

        return videojuegos;
    } 
    
    public static List<Jugador> consultarJugadoresEntreFechas(EntityManagerFactory emf, LocalDate fechaDesde, LocalDate fechaHasta) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT j FROM Jugador j WHERE j.fechaNacimiento BETWEEN :fechaDesde AND :fechaHasta", Jugador.class).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).getResultList();
    }

    public static List<Videojuego> consultarVideojuegosSinValoracion(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT v FROM Videojuego v WHERE v.valoracionMedia IS NULL", Videojuego.class).getResultList();
    }

    public static List<Videojuego> consultarTop5VideojuegosMejorValorados(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT v FROM Videojuego v WHERE v.valoracionMedia IS NOT NULL ORDER BY v.valoracionMedia DESC, v.id ASC", Videojuego.class).setMaxResults(5).getResultList();
    }

    public static List<ContactoJugadorDto> consultarAliasYTelefono(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT NEW es.ercilla.ejercicios.ejercicio1.dtos.ContactoJugadorDto(j.alias, j.telefono) FROM Jugador j", ContactoJugadorDto.class).getResultList();
    }

    public static List<ValoracionMediaGeneroDto> consultarValoracionMediaPorGenero(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT NEW es.ercilla.ejercicios.ejercicio1.dtos.RecaudacionTorneoDto(v.genero, AVG(v.valoracionMedia)) FROM Videojuego v GROUP BY v.genero", ValoracionMediaGeneroDto.class).getResultList();
    }

    public static List<RecaudacionTorneoDto> consultarRecaudacionTorneoPorAnyo(EntityManagerFactory emf, Year anio) {
        
        return null;
    }



   

}
