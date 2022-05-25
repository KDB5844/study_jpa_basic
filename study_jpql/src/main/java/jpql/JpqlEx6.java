package jpql;

import jpql.domain.Member;
import jpql.domain.Team;
import jpql.domain.enumeration.MemberType;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.List;

public class JpqlEx6 {
    public static void main(String[] args) {
        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {

                Team team = new Team();
                team.setName("teamA");

                em.persist(team);

                Member member = new Member("김덕배",10);
                member.changeTeam(team);
                member.setType(MemberType.ADMIN);
                em.persist(member);

                Member member2 = new Member("관리자",70);
                member2.changeTeam(team);
                member2.setType(MemberType.ADMIN);
                em.persist(member2);

                em.flush();
                em.clear();

                // 기본 CASE 문
                String query =
                        "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else '일반요금' " +
                            "end " +
                        "from Member m";

                // COALESCE : 하나씩 조회해서 null 이 아니면 반환 (사용자 이름이 없으면 이름 없는 회원을 반환)
                String coalesceQuery = "select coalesce(m.username, '이름 없는 회원') from Member m ";

                // NULLIF : 두 값이 같으면 null을 반환, 다르면 첫번째 값을 반환
                String nullifQuery = "select nullif(m.username, '관리자') from Member m";

                List<String> result = em.createQuery(nullifQuery, String.class)
                        .getResultList();

                for (String s : result) {
                    System.out.println("요금 = " + s);
                }


            }
        };
        jpaTemplate.transaction();
    }
}
