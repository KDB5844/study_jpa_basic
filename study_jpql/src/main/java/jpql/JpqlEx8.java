package jpql;

import jpql.domain.Member;
import jpql.domain.Team;
import jpql.domain.enumeration.MemberType;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

public class JpqlEx8 {

    /**
     * 경로 표현식 특징
     * 상태 필드              : 경로 탐색의 끝, 탐색X
     * 단일 값 연관 경로       : 묵시적 내부 조인(inner join) 발생, 탐색O
     * 컬렉션 값 연관 경로     : 묵시적 내부 조인 발생, 탐색X
     */

    public static void main(String[] args) {
        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                Team team = new Team();
                team.setName("teamA");

                em.persist(team);

                Member member = new Member("member1", 10);
                member.changeTeam(team);
                member.setType(MemberType.ADMIN);
                em.persist(member);

                Member member2 = new Member("member2", 70);
                member2.changeTeam(team);
                member2.setType(MemberType.ADMIN);
                em.persist(member2);

                em.flush();
                em.clear();

                // 상태 필드 경로
                String query = "select m.username from Member m";
                List<String> resultList = em.createQuery(query, String.class)
                        .getResultList();

                for (String s : resultList) {
                    System.out.println("username = " + s);
                }

                // 단일 값 연관 경로 - 묵시적 내부 조인 발생 추가 탐색 O
                String query1 = "select m.team from Member m";
                List<Team> resultList1 = em.createQuery(query1, Team.class)
                        .getResultList();

                for (Team team1 : resultList1) {
                    System.out.println("team1.getName() = " + team1.getName());
                }


                // 컬렉션 값 연관 경로 - 묵시적 내부 조인 발생 추가 탐색 X
                String query2 = "select t.members from Team t";
                List<Collection> resultList3 = em.createQuery(query2, Collection.class)
                        .getResultList();
                System.out.println("members = " + resultList3);

                // -> 컬렉션 값 연관 경로 추가 탐색을 원한다면
                String query3 = "select m.username from Team t join t.members m";
                List<String> resultList2 = em.createQuery(query3, String.class)
                        .getResultList();

                System.out.println("members username = " + resultList2);

            }
        };
        jpaTemplate.transaction();
    }

}
