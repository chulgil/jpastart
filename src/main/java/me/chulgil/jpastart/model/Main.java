package me.chulgil.jpastart.model;


import me.chulgil.jpastart.entity.Member;
import me.chulgil.jpastart.entity.Team;

import javax.persistence.*;
import javax.persistence.Persistence;
import java.util.List;

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
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("Member1");
            member.setTeam(team);
            em.persist(member);

            Member findMember = em.find(Member.class, member.getId());
            String teamName = findMember.getTeam().getName();
            System.out.println("findTeam = " + teamName);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
