package jpabook.jpashop;

import jpabook.jpashop.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Book book = new Book();
            book.setId(1L);
            book.setName("JPA");
            book.setAuthor("영하니형");

            em.persist(book);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.close();
        } finally {
            tx.rollback();
        }
        emf.close();

    }
}
