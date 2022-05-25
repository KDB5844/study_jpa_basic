package jpql;

import jpql.domain.Member;
import jpql.domain.Team;
import jpql.domain.enumeration.MemberType;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.List;

public class JpqlEx7 {
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

                // CONCAT FUNCTION == ('a' || 'b')
                String concatQuery = "select concat('a', 'b') from Member m";

                // SUBSTRING FUNCTION
                String substringQuery = "select substring(m.username, 0, 2) from Member m";

                // LOCATE FUNCTION (return type = INTEGER CLASS)
                String locateQuery = "select locate('de', 'abcdef') from Member m";

                // SIZE FUNCTION (JPA 용도) 컬랙션의 크기를 반환 함.
                String sizeQuery = "select size(t.members) from Team t";
                
                // INDEX FUNCTION (JPA 용도) @OrderColumn 사용 할 경우 (컬랙션 값타입) 위치 값 구할 때 사용

                List<Integer> result = em.createQuery(sizeQuery, Integer.class)
                        .getResultList();

                for (Integer s : result) {
                    System.out.println("s = " + s);
                }

            }
        };
        jpaTemplate.transaction();
    }
}
