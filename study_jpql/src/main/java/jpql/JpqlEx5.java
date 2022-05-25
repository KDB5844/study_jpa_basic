package jpql;

import jpql.domain.Member;
import jpql.domain.Team;
import jpql.domain.enumeration.MemberType;
import template.JpaTemplate;

import javax.persistence.EntityManager;

public class JpqlEx5 {
    public static void main(String[] args) {
        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {

                Team team = new Team();
                team.setName("teamA");

                em.persist(team);

                Member member = new Member("member1",10);
                member.changeTeam(team);
                member.setType(MemberType.ADMIN);
                em.persist(member);

                em.flush();
                em.clear();
                
                // ENUM type 표현 방식
                MemberType result = em.createQuery("select m.type from Member m where m.type = jpql.domain.enumeration.MemberType.ADMIN", MemberType.class)
                        .getSingleResult();

                System.out.println("result = " + result.name());


            }
        };

        jpaTemplate.transaction();
    }
}
