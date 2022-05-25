package jpql;

import jpql.domain.Member;
import jpql.domain.Team;
import jpql.domain.enumeration.MemberType;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.List;

public class JpqlEx9 {
    /**
     * 페치 조인 (fetch join)
     */
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

                // Fetch join X
                List<Member> resultList = em.createQuery("select m from Member m", Member.class)
                        .getResultList();

                for (Member member1 : resultList) {
                    System.out.println("team = " + member1.getTeam().getName());
                    // 회원1, 팀A(SQL)
                    // 회원2, 팀A(1차 캐시)
                    // 회원3, 팀B(SQL)
                    // N + 1 문제 발생(최악의 경우)
                }

                em.flush();
                em.clear();

                System.out.println(" ");
                System.out.println("========FETCH JOIN==========");
                System.out.println(" ");

                // Fetch join O 지연로딩 X
                List<Member> fetchJoinList = em.createQuery("select m from Member m join fetch m.team", Member.class)
                        .getResultList();

                for (Member member1 : fetchJoinList) {
                    System.out.println("member = " + member1 +", team = " + member1.getTeam().getName());
                }

                em.flush();
                em.clear();

                System.out.println(" ");
                System.out.println("========Collection FETCH JOIN==========");
                System.out.println(" ");

                // 컬렉션 Fetch join
                List<Team> teamList = em.createQuery("select distinct t from Team t join fetch t.members where t.name = 'teamA'", Team.class)
                        .getResultList();

                for (Team team : teamList) {
                    System.out.println("members = " + team.getMembers());
                }

                em.flush();
                em.clear();

                System.out.println(" ");
                System.out.println("========GENERAL JOIN==========");
                System.out.println(" ");

                // 일반 join (team t 의 멤버들만 가지고 온다.) == 실행 시 연관된 엔티티를 함께 조회하지 않음.
                List<Team> teamList1 = em.createQuery("select t from Team t join t.members", Team.class)
                        .getResultList();

                for (Team team : teamList1) {
                    System.out.println("team name = " + team.getName());
                    // Member 호출 시 SQL 조회 (지연로딩)
                    System.out.println("team member = " + team.getMembers());
                }
            }
        };

        jpaTemplate.transaction();
    }
}
