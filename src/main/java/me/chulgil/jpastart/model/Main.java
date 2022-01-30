package me.chulgil.jpastart.model;

import me.chulgil.jpastart.entity.*;

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

            // 주소 추가할 때
            Member m = new Member();
            m.setName("member1");
            m.setAddress(new Address("old1", "street1","11111"));
            em.persist(m);
            // 주소 수정할 때
            m.setAddress(new Address("old1", "street1","11111"));

            Member m2 = new Member();
            m2.setName("member2");
            m2.setAddress(new Address("old1", "street1","11111"));
            em.persist(m2);

            Member m3 = new Member();
            m3.setName("member3");
            m3.setAddress(new Address("old1", "street1","11111"));
            em.persist(m3);

            Order o = new Order();
            o.setMember(m);
            o.setStatus(OrderStatus.ORDER);
            em.persist(o);
            Order o2 = new Order();
            o2.setMember(m);
            o2.setStatus(OrderStatus.CANCEL);
            em.persist(o2);
            Order o3 = new Order();
            o3.setMember(m2);
            o3.setStatus(OrderStatus.CANCEL);
            em.persist(o3);
            Order o4 = new Order();
            o4.setStatus(OrderStatus.ORDER);
            em.persist(o4);

            em.flush();
            em.clear();

            // System.out.println("프로젝션(SELECT)=========================");
            // 1. 쿼리타입 조회
            // getSingleResult는 결과값이 하나가 아닌경우 exception을 발생시키므로 주의
            // List<Member> queryResult = em.createQuery("select sistinct m.username, m.age from Member m")
            //       .getResultList();
            // 2. Object[]타입 조회
            // List<Object[]> queryTypeResult = em.createQuery("select sistinct m.username, m.age from Member m")
            //        .getResultList();
            // 3. new 명령어로 조회
            // MemberDTO memberDTO = result.get(0);
            // System.out.println("MemberDTO = " + memberDTO.getName());
            // List<Object[]> objectResult = em.createQuery("select new jpql.MemberDTO(m.name, m.age) m from member m", MemberDTO.class)
            //        .getResultList();

            // System.out.println("페이징 쿼리 =========================");
            // String jpql = "select m from Member m order by m.name desc";
            // List<Member> resultList = em.createQuery(jpql, Member.class)
            //        .setFirstResult(10)
            //        .setMaxResults(20)
            //        .getResultList();

            // 조인쿼리
            String innerQuery = "select m from Member m inner join m.team t";
            String outerQuery = "select m from Member m left join m.team t";
            String thetaQuery = "select count(m) from Member m, Team t where m.name = t.name ";
            String joinOnQuery = "select m,t from Member m left join m.team t on t.name='A'";
            String joinExternQuery = "select m,t from Member m left join m.team t on m.name = t.name";
            String subQuery = "select m from Member m where m.age > (select avg(m2.age) from Member m2";
            String subQuery2 = "select m from Member m where (select count(o) from Order o where m = o.member) <0 )";
            String subQuery3 = "select m from Member m where exists(select t from m.team t where t.name = '팀A'";
            String subQuery4 = "select o from Order o where o.orderAmount > ALL(select p.stockAmount from Product p)'";
            String subQuery5 = "select m from Member m where m.team = ANY(select t from Team t)";
            // 조건식 CASE
            String caseQuery = "select " +
                    "case when m.age <= 10 then '학생요금' " +
                    "     when m.age >= 60 then '경로요금' " +
                    "     else '일반요금' " +
                    "from Member m";
            // 사용자 이름이 없으면 이름 없는 회원을 반환
            // "select coalesce(m.name, '이름 없는 회원') from Member m"
            // 사용자 이름이 관리자면 null을 반환하고 나머지는 원래 이름을 반환
            // "select NULLIF(m.name, '관리자') from Member m"

            // 경로 표현식
            String pathQuery = "select o.items from Order o"; // 묵시적 조인발생으로 위험
            String pathQuert2 = "select i.name from Order o join o.orderitems i"; // 명시적 조인으로 사용

            // Fetch 조인 : JPA 전용으로 조회 하면서 연관된 테이블도 함께 조회 SELECT o.*, m.*
            String fetchQuery = "select o from Order o join fetch o.member";
            String fetchDsQuery = "distinct o select o from Order o join fetch o.member";
            // 객체그래프를 통으로 가져오기때문에 성능한계상 페치조인은 가급적 사용하면 안됨



            // 상속 관계의 타입 쿼리 : TYPE(m) = Member (상속관계에서 사용)
            em.createQuery("select i from Item i where type(i) = Book", Item.class)
                    .getResultList();

            // JPA 서브 쿼리 한계
            // WHERE, HAVING 절에서만 서브쿼리 사용가능
            // FROM 절의 서브쿼리는 불가능 -> 조인으로 풀어서 해결
            String errorSubQuery = "select e.age, e.name from (select m.age, m.name from Member m) as e";
            String createQuery = "select o.status, 'HELLO', true from Order o where o.status = :orderType";
            List<Object[]> createResult = em.createQuery(createQuery).
                    setParameter("orderType", OrderStatus.CANCEL)
                    .getResultList();
            for (Object[] objects : createResult) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
            }


            System.out.println("N + 1 ===============================");
            // N + 1 문제 구현
//            String query = "select o From Order o";
            // N + 1 문제 해결
//            String query = "select o From o join fetch o.member";
            // 데이터조회시 데이터가 뻥튀기 되는상황 방지
            String query = "select distinct o From Order o join fetch o.member";

            List<Order> result = em.createQuery(query, Order.class)
                    .getResultList();
            System.out.println("result = " + result.size());


            // 다형성 조회 예제
            // String query = "select i from Item i where type(i) IN (Book, Movie)";
            // SQL -> select i from Item i where i.DTYPE in ('B', 'M')
            // String query = "select i from Item i where treat(i as Book).author = 'kim";
            // SQL -> select i.* from Item i where i.DTYPE = 'B' and i.author='kim'

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
