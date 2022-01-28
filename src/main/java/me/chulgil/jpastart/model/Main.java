package me.chulgil.jpastart.model;

import me.chulgil.jpastart.entity.Book;
import me.chulgil.jpastart.entity.Order;
import me.chulgil.jpastart.entity.OrderItem;

import javax.persistence.*;
import javax.persistence.Persistence;

/**
 * @author cglee
 */
public class Main {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpastart");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Book book = new Book();
            book.setAuthor("chulgil");
            book.setIsbn("sdf");
            em.persist(book);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
