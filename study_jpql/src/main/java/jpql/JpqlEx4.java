package jpql;

import jpql.domain.Member;
import jpql.domain.Team;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class JpqlEx4 {
    public static void main(String[] args) {

        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {

                Team team = new Team();
                team.setName("teamA");
                em.persist(team);

                for (Member member : createMemberList()) {
                    member.setTeam(team);
                    em.persist(member);
                }

                em.flush();
                em.clear();

                List<Member> resultList = em.createQuery("select m from Member m left outer join m.team t where t.name=:teamName", Member.class)
                        .setParameter("teamName", "teamA")
                        .getResultList();

                for (Member member : resultList) {
                    System.out.println("member = " + member);
                }
            }
        };

        jpaTemplate.transaction();
    }

    public static List<Member> createMemberList() {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            members.add(new Member("member" + i, i+10));
        }

        return members;
    }
}
