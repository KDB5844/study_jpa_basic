package jpql;

import jpql.domain.Member;
import jpql.domain.Team;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.List;

public class JpqlEx11 {
    public static void main(String[] args) {
        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                insertMemberAndTeam(em);

                List<Member> resultList = em.createNamedQuery("Member.findByUserName", Member.class)
                        .setParameter("username", "회원1")
                        .getResultList();

                System.out.println("resultList = " + resultList);

            }
        };
        jpaTemplate.transaction();
    }


    private static void insertMemberAndTeam(EntityManager em) {
        Team teamA = new Team();
        teamA.setName("teamA");

        em.persist(teamA);

        Team teamB = new Team();
        teamB.setName("teamB");

        em.persist(teamB);

        Member member = new Member("회원1", 10);
        member.changeTeam(teamA);
        em.persist(member);

        Member member2 = new Member("회원2", 70);
        member2.changeTeam(teamA);
        em.persist(member2);

        Member member3 = new Member("회원3", 40);
        member3.changeTeam(teamB);
        em.persist(member3);
    }
}
