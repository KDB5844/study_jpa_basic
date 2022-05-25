package jpql;

import jpql.domain.Member;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class JpqlEx3 {

    // 페이징 API
    public static void main(String[] args) {
        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {

                for (Member member : createMemberList()) {
                    em.persist(member);
                }

                List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                        .setFirstResult(1)
                        .setMaxResults(10)
                        .getResultList();

                System.out.println("resultList.size() = " + resultList.size());
                
                for (Member member : resultList) {
                    System.out.println("member = " + member);
                }
                
            }
        };
        jpaTemplate.transaction();
    }

    public static List<Member> createMemberList() {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            members.add(new Member("member" + i, i+10));
        }

        return members;
    }


}
