package es.ercilla.ejemplos;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.ercilla.modelos.Libro;
import es.ercilla.modelos.ResumenLibro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class EjemploCRUDMasivo {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("ad-clase")) {
            List<Libro> libros = new ArrayList<>();

            libros.add(new Libro("Don Quijote de la Mancha",
                    LocalDate.of(1605, 1, 16), true, new BigDecimal("9.50")));

            libros.add(new Libro("Cien años de soledad",
                    LocalDate.of(1967, 5, 30), true, new BigDecimal("9.25")));

            libros.add(new Libro("El principito",
                    LocalDate.of(1943, 4, 6), true, null));

            libros.add(new Libro("1984",
                    LocalDate.of(1949, 6, 8), false, new BigDecimal("9.00")));

            libros.add(new Libro("La sombra del viento",
                    LocalDate.of(2001, 4, 17), true, new BigDecimal("8.50")));

            libros.add(new Libro("Harry Potter y la piedra filosofal",
                    LocalDate.of(1997, 6, 26), true, null));

            libros.add(new Libro("El hobbit",
                    LocalDate.of(1937, 9, 21), true, new BigDecimal("8.75")));

            libros.add(new Libro("Los juegos del hambre",
                    LocalDate.of(2008, 9, 14), false, null));

            libros.add(new Libro("Crónica de una muerte anunciada",
                    LocalDate.of(1981, 4, 1), true, new BigDecimal("7.80")));

            libros.add(new Libro("La metamorfosis",
                    LocalDate.of(1915, 10, 1), false, null));

            //insertarLibros(emf, libros);
            String busqueda = "quijote";
            List<Libro> librosConsultados = consultarLibrosPorTitulo(emf, busqueda);
            System.out.println("Números de libros encontrados con la palabra [ " + busqueda +" ]: " + librosConsultados.size());
            List<ResumenLibro> resumenLibrosConsultados = consultarResumenLibros(emf);
            for (ResumenLibro resumenLibro : resumenLibrosConsultados) {
                System.out.println(resumenLibro.getTitulo() + " - " + resumenLibro.getCalificacionMedia());
            }

            List<Libro> top5Libros = consultarTop5LibrosMejorValorados(emf);
            if(top5Libros.size() > 0){
                System.out.println("========= RANKING TOP 5 LIBROS MEJOR VALORADOS ==========");
                for(int i = 0; i < top5Libros.size(); i++){
                System.out.println((i+1) + ". " + top5Libros.get(i).getTitulo());
                }
            } else{
                System.out.println("No hay libros insertados");
            }

            System.out.println("NÚMERO DE LIBROS TOTALES: " + consultarNumeroLibros(emf));

            consultarNumeroLibrosPorDisponibilidad(emf);

            actualizarCalificacionesSiDisponiblePrestamo(emf, new BigDecimal("9.5"));

            borrarSiFechaPublicacionAnterior(emf, LocalDate.of(1990, 1, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertarLibros(EntityManagerFactory emf, List<Libro> libros) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // Inicia la transacción

            for (Libro libro : libros) {
                em.persist(libro);
                System.out.println("LIBRO INSERTADO: " + libro);
            }

            tx.commit(); // Guarda cambios en la bbdd y cierra la transacción
        } catch (Exception e) {
            tx.rollback(); // en caso de error, deshacemos todos los cambios realizados en la transacción
            em.close();
            throw e;
        }
    }

    public static List<Libro> consultarLibrosPorTitulo(EntityManagerFactory emf, String titulo) {
        if (titulo.isBlank()) {
            return null;
        }
        EntityManager em = emf.createEntityManager();

        List<Libro> librosConsultados = null; 
        TypedQuery<Libro> consulta = em.createQuery("SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE LOWER(:patron)", Libro.class);
        consulta.setParameter("patron", "%" + titulo + "%");
        librosConsultados = consulta.getResultList();
        em.close();
        return librosConsultados;
    }

    public static List<ResumenLibro> consultarResumenLibros(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        TypedQuery<ResumenLibro> consulta = em.createQuery("SELECT NEW es.ercilla.modelos.ResumenLibro(l.titulo, l.calificacionMedia) FROM Libro l", ResumenLibro.class);
        List<ResumenLibro> resumenesConsultados = consulta.getResultList();

        return resumenesConsultados;
    }

    public static List<Libro> consultarTop5LibrosMejorValorados(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Libro> consulta = em.createQuery("SELECT l FROM Libro l WHERE l.calificacionMedia IS NOT NULL ORDER BY l.calificacionMedia DESC", Libro.class)
        .setMaxResults(5); // LIMIT 5
        //.setFirsResults(5); // OFFSET 5  (Me salto 5 resultados)
        List<Libro> librosConsultados = consulta.getResultList();
        return librosConsultados;
    }

    public static Long consultarNumeroLibros(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Long> consulta = em.createQuery("SELECT COUNT(l) FROM Libro l", Long.class); // Espero recibir un número entero
        Long numeroLibros = consulta.getSingleResult(); // Devuelve un único resultado
        em.close();
        return numeroLibros;
    }

    public static List<Object[]> consultarNumeroLibrosPorDisponibilidad(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Object[]> consulta = em.createQuery("""
            SELECT l.disponiblePrestamo, COUNT(l)
            FROM Libro l
            GROUP BY l.disponiblePrestamo
            """, Object[].class);

        List<Object[]> resultados = consulta.getResultList();

        for (Object[] resultado : resultados) {
            System.out.println(resultado[0] + ", " + resultado[1]);
        }

        em.close();
        return resultados;
    }

    public static void actualizarCalificacionesSiDisponiblePrestamo(EntityManagerFactory emf, BigDecimal nuevaCalificacion){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            int numLibrosActualizados = em.createQuery("""
                UPDATE Libro l
                SET l.calificacionMedia = :nuevaCalificacion
                WHERE l.disponiblePrestamo = TRUE
            """).setParameter("nuevaCalificacion", nuevaCalificacion).executeUpdate(); // sustituimos un parámetro por su valor
            System.out.println("NÚMERO DE LIBROS ACTUALIZADOS = " + numLibrosActualizados);
            tx.commit();
            em.close();
        } catch (Exception e) {
            em.close();
            throw e;
        }
    }

    public static void borrarSiFechaPublicacionAnterior(EntityManagerFactory emf, LocalDate fechaLimite) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

                int numLibrosBorrados = em.createQuery("""
                    DELETE FROM Libro l
                    WHERE l.fechaPublicacion < :fechaLimite
                """).setParameter("fechaLimite", fechaLimite).executeUpdate();

                System.out.println("NÚMEROS DE LIBROS BORRADOS: " + numLibrosBorrados);
            tx.commit();

            em.close();
        } catch (Exception e) {
            tx.rollback();
            em.close();
            throw e;

        }
    }
}
