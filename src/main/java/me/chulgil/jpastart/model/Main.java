package me.chulgil.jpastart.model;

import me.chulgil.jpastart.entity.*;

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
            // 주소 추가할 때
            Address address = new Address("old1", "street1","11111");
            Member member = new Member();
            member.setName("chulgil");
            member.setAddress(address);
            em.persist(member);
            // 주소 수정할 때
            Address newAddress = new Address("new1", address.getStreet(), address.getZipcode());
            member.setAddress(newAddress);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
