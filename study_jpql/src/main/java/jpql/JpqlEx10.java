package jpql;

import jpql.domain.Member;
import jpql.domain.Team;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.List;

public class JpqlEx10 {
    public static void main(String[] args) {
        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
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

                em.flush();
                em.clear();

                // 원칙적으로 fetch join 대상에 별칭을 줄 수 없음(스펙 상) 하이버네이트는 가능(안쓰는게 맞음 == 객체 그래프를 다 탐색 가능하게 설계하는게 맞음).
                List<Member> resultList = em.createQuery("select m from Member m join fetch m.team as t", Member.class)
                        .getResultList();

                System.out.println("members = " + resultList + ", team = " + member.getTeam().getName());
            }
        };
        jpaTemplate.transaction();
    }
}
