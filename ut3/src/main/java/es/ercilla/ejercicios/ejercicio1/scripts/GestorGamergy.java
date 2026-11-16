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
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("ad-clase")) {
            // a. Insertar un videojuego
            Videojuego videojuego = insertarVideojuego(emf, "Minecraft", LocalDate.of(2015, 1, 1), true, Float.valueOf("8.75"), "aventura");
            System.out.println(videojuego);
            System.out.println(insertarVideojuego(emf, "Outlast", LocalDate.of(2016, 2, 13), false, Float.valueOf("7.75"), "terror"));
            // b. Insertar un jugador
            Jugador jugador = insertarJugador(emf, "Paco", LocalDate.of(2000, 1, 1), true, "925 14 14 14");
            System.out.println(jugador);
            // c. Crear un torneo e inscribir a un jugador
            Torneo torneo = insertarTorneo(emf, "Torneo Valorant", LocalDate.now(), 20, true, new BigDecimal("19.99"), videojuego.getId(), jugador.getCodigo());
            System.out.println(torneo);

            // d. Consultar un jugador por su código
            consultarJugadorPorCodigo(emf, jugador.getCodigo());
            // e. Actualizar los datos de un torneo
            actualizarDatosTorneo(emf, 1, "VCT VALORANT", LocalDate.of(2026, 12, 1), 10, false, new BigDecimal("29.99"));
            System.out.println(torneo);

            // f. Borrar un torneo
            borrarTorneoPorId(emf, 1);

            // g. Buscar videojuegos por título
            List<Videojuego> videojuegosBBDD = consultarVideojuegosPorTitulo(emf, "Mine");
            if(videojuegosBBDD != null){
                System.out.println(videojuegosBBDD);
            }
            
            // h. Buscar jugadores entre dos fechas
            List<Jugador> jugadoresBBDD = consultarJugadoresEntreFechas(emf, LocalDate.of(1999, 1, 1), LocalDate.of(2005, 1, 1));
            if(jugadoresBBDD != null){
                System.out.println(jugadoresBBDD);
            }
            // i. Consultar videojuegos sin valoración
            videojuegosBBDD = consultarVideojuegosSinValoracion(emf);
            if(videojuegosBBDD != null){
                System.out.println(videojuegosBBDD);
            }
            // j. Consultar los cinco videojuegos mejor valorados
            videojuegosBBDD = consultarTop5VideojuegosMejorValorados(emf);
            if(videojuegosBBDD != null){
                System.out.println(videojuegosBBDD);
            }
            // k. Consultar los datos de contacto
            List<ContactoJugadorDto> contactos = consultarAliasYTelefono(emf);
            if(contactos != null){
                System.out.println(contactos);
            }

            // l. Consultar la valoración media por género
            List<ValoracionMediaGeneroDto> valoracionMediaGenero = consultarValoracionMediaPorGenero(emf);
            if(valoracionMediaGenero != null){
                System.out.println(valoracionMediaGenero);
            }

            // m. Consultar la recaudación de todos los torneos de un año concreto
            List<RecaudacionTorneoDto> recaudacionTorneo = consultarRecaudacionTorneoPorAnyo(emf, Year.of(2026));
            if(recaudacionTorneo != null){
                System.out.println(recaudacionTorneo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(scanner != null){
                scanner.close();
            }
        }
    }
    

    public static Videojuego insertarVideojuego(EntityManagerFactory emf, String titulo, LocalDate fechaLanzamiento, boolean multijugador, Float valoracionMedia, String genero) {
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
            if(tx != null && tx.isActive()){
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
            em.persist(jugador);
            tx.commit();
        } catch (Exception e) {
            if(tx != null && tx.isActive()){
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
            torneo.setVideojuego(videojuego);
            em.persist(torneo);

            tx.commit();
        } catch (Exception e) {
            if(tx != null && tx.isActive()){
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
            torneo.setPlazas(plazas);
            torneo.setInscripcionAbierta(inscripcionAbierta);
            torneo.setImporteInscripcion(importeInscripcion);

            tx.commit();
        } catch (Exception e) {
            if(tx != null && tx.isActive()){
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
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
        } finally{
            if(em != null){
                em.close();
            }
        }
    }

    public static List<Videojuego> consultarVideojuegosPorTitulo(EntityManagerFactory emf, String texto) {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Videojuego> consulta = em.createQuery("SELECT v FROM Videojuego v WHERE LOWER(v.titulo) LIKE LOWER(:texto)", Videojuego.class).setParameter("texto", "%" + texto + "%");
            List<Videojuego> videojuegos = consulta.getResultList();
            return videojuegos;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    } 
    
    public static List<Jugador> consultarJugadoresEntreFechas(EntityManagerFactory emf, LocalDate fechaDesde, LocalDate fechaHasta) {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT j FROM Jugador j WHERE j.fechaNacimiento BETWEEN :fechaDesde AND :fechaHasta", Jugador.class).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<Videojuego> consultarVideojuegosSinValoracion(EntityManagerFactory emf) {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT v FROM Videojuego v WHERE v.valoracionMedia IS NULL", Videojuego.class).getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<Videojuego> consultarTop5VideojuegosMejorValorados(EntityManagerFactory emf) {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT v FROM Videojuego v WHERE v.valoracionMedia IS NOT NULL ORDER BY v.valoracionMedia DESC, v.id ASC", Videojuego.class).setMaxResults(5).getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<ContactoJugadorDto> consultarAliasYTelefono(EntityManagerFactory emf) {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT NEW es.ercilla.ejercicios.ejercicio1.dtos.ContactoJugadorDto(j.alias, j.telefono) FROM Jugador j", ContactoJugadorDto.class).getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<ValoracionMediaGeneroDto> consultarValoracionMediaPorGenero(EntityManagerFactory emf) {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT NEW es.ercilla.ejercicios.ejercicio1.dtos.ValoracionMediaGeneroDto(v.genero, AVG(v.valoracionMedia)) FROM Videojuego v GROUP BY v.genero", ValoracionMediaGeneroDto.class).getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<RecaudacionTorneoDto> consultarRecaudacionTorneoPorAnyo(EntityManagerFactory emf, Year anio) {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT NEW es.ercilla.ejercicios.ejercicio1.dtos.RecaudacionTorneoDto(t.nombre, count(j), t.importeInscripcion * count(j)) FROM Torneo t LEFT JOIN t.jugadores j WHERE YEAR(t.fechaCelebracion) = :anio GROUP BY t.id, t.nombre, t.importeInscripcion", RecaudacionTorneoDto.class).setParameter("anio", anio).getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
