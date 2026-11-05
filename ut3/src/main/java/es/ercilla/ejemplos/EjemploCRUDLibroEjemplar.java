package es.ercilla.ejemplos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import es.ercilla.modelos.Ejemplar;
import es.ercilla.modelos.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class EjemploCRUDLibroEjemplar {
  public static void main(String[] args) {
    EntityManagerFactory emf = null;
    try {
      emf = Persistence.createEntityManagerFactory("ad-clase");

      // 1. Insertar nuevo libro
        Libro nuevoLibro = EjemploCRUDLibro.insertarNuevoLibro(emf, new Libro("Ejemplo2", LocalDate.of(2000, 1, 1), true, new BigDecimal("9.5")));
        System.out.println(nuevoLibro);
      // 2. Insertar un ejemplar del libro existente
      Ejemplar ejemplar = new Ejemplar(false, LocalDate.of(2026, 12, 1));
      
        ejemplar = insertarEjemplarDeLibro(emf, 120, ejemplar);
        System.out.println(ejemplar);
      // 3. Consultar libro con sus ejemplares
        consultarLibroConEjemplaresLAZY(emf, 120);
      // 4. Consultar ejemplares por título de libro
      System.out.println("EJEMPLARES CON TITULO SIMILAR A \"sombr\"");
        System.err.println(consultarEjemplaresPorTituloLibroLIKE(emf, "sombr"));
      // 5. Resetear prestamos de un libro

      // 6. Borrar ejemplar

      // 7. Insertar un nuevo libro + sus ejemplares mediante CascadeType.PERSIST

      // 8. Borrar ese libro + sus cuatro ejemplares mediante CascadeType.REMOVE
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    if (emf != null && emf.isOpen()) {
      emf.close();
    }
  }

  public static Ejemplar insertarEjemplarDeLibro(EntityManagerFactory emf, Integer libroId,
      Ejemplar ejemplar) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
        tx.begin();
        Libro libro = EjemploCRUDLibro.buscarLibroPorId(emf, libroId);
        if(libro != null){
            ejemplar.setLibro(libro);
            libro.getEjemplares().add(ejemplar);
            em.persist(ejemplar);
            tx.commit();
        }else{
            System.out.println("Libro no encontrado");
        }
        
    } catch (Exception e) {
        tx.rollback();
        em.close();
        throw e;
    }
    return ejemplar;
  }
  // LAZY - Es política vaga de consulta
  // si consulto un libro por su id con find(), por defecto se hace una consulta "vaga"
  // es decir, hibernate se limita a consultar el libro, pero no sus ejemplares

  public static Libro consultarLibroConEjemplaresLAZY(EntityManagerFactory emf, Integer libroId) {
    EntityManager em = emf.createEntityManager();
    Libro libro = em.find(Libro.class, libroId);
    System.out.println(libro);
    // como aquí necesito los ejemplares, hibernate se pone a consultarlos 1 a 1
    // LAZY -> consulta bajo demanda
    for (Ejemplar ejemplar : libro.getEjemplares()) {
        System.out.println(ejemplar);
    }
    return libro;
  }
  // MEGA CONSULTA, CONSULTAMOS DE UNA SOLA VEZ EL LIBRO Y TODOS SUS EJEMPLARES
  public static Libro consultarLibroConEjemplaresJOINFETCH(EntityManagerFactory emf, Integer libroId) {
    EntityManager em = emf.createEntityManager();
    Libro libro = em.createQuery("""
        SELECT l FROM Libro l
        LEFT JOIN FETCH l.ejemplares
        WHERE l.id = :id
        """, Libro.class).setParameter("id", libroId).getSingleResult();

    for (Ejemplar ejemplar : libro.getEjemplares()) {
        System.out.println(ejemplar);
    }
    return libro;
  }

  public static List<Ejemplar> consultarEjemplaresPorTituloLibroLIKE(EntityManagerFactory emf,
      String titulo) {
    EntityManager em = emf.createEntityManager();
    List<Ejemplar> resultados = em.createQuery(
      """
      SELECT e FROM Ejemplar e
      JOIN e.libro l
      WHERE LOWER (l.titulo) LIKE :titulo
      """, Ejemplar.class).setParameter("titulo", "%" + titulo + "%").getResultList();

    return resultados;
  }

  public static void resetearPrestamoEjemplaresLibro(EntityManagerFactory emf, Integer libroId) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    // buscamos el libro cuyo ID=libroId
    Libro l = em.find(Libro.class, libroId);
    if(l == null){
      return;
    }
    try {
      tx.begin();
      l.setDisponiblePrestamo(true);
      for (Ejemplar e : l.getEjemplares()) {
        // reseteamos el estado de prestamo de cada uno e sus ejemplares
        e.setFechaFinPrestamo(null);
        e.setPrestado(false);
        tx.commit();
      }
    } catch (Exception e) {
      tx.rollback();
    }
    em.close();
  }

  public static void borrarEjemplar(EntityManagerFactory emf, UUID codigo) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    // buscamos el libro cuyo ID=libroId
    Ejemplar ejemplar = em.find(Ejemplar.class, codigo);
    if(ejemplar == null){
      return;
    }
    try {
      tx.begin();
      // buscamos el libro al que pertenece ese ejemplar
      Libro libro = ejemplar.getLibro();
      // borramos la conexion del libro con el ejemplar
      libro.getEjemplares().remove(ejemplar);
      // borramos el ejemplar de la persistencia
      em.remove(ejemplar);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    }
    em.close();
  }

  public static Libro insertarLibroConEjemplares(EntityManagerFactory emf, Libro libro,
    List<Ejemplar> ejemplares) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    try {
      tx.begin();
      em.persist(libro);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    }

    return libro;
  }

  public static void borrarLibroConEjemplares(EntityManagerFactory emf, Integer libroId) {
            EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Libro libro = em.find(Libro.class, libroId);
        if(libro != null){
            em.remove(libro);
            et.commit();
        }else{
            et.rollback();
        }
  }

}