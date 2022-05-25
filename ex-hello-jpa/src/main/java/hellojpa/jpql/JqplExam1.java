package hellojpa.jpql;


import hellojpa.Member;
import hellojpa.Team;
import hellojpa.template.JpaTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JqplExam1 {

    public static void main(String[] args) {

        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                Team team = new Team();
                team.setName("TeamA");

                em.persist(team);

                Member member1 = new Member();
                member1.setUserName("kimSuck");
                member1.setTeam(team);

                em.persist(member1);

                em.flush();
                em.clear();

                Member member2 = em.find(Member.class, member1.getId());

                List<Member> result = em.createQuery(
                        "select m from Member m where m.userName like '%kim%'"
                        , Member.class).getResultList();

                for (Member member : result) {
                    System.out.println("member = " + member);
                }

                String jpql = "";
            }
        };
        jpaTemplate.transaction();
    }

}
