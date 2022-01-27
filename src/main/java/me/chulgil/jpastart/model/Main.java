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

            // 테스트할때 영속성 컨텍스트말고 바로 디비에서 가져오고 싶을때
            em.flush(); // 영속성 컨텍스트의 변경내용을 디비에 반영
            em.clear(); // 영속성 컨텍스트를 완전히 초기화

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getName());
            }
            
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
