package jpql;

import jpql.domain.Member;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpqlEx1 {

    public static void main(String[] args) {
        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                Member member = new Member();
                member.setUsername("Member1");
                member.setAge(10);
                em.persist(member);

                // 반환 Type 이 명확할 때
                TypedQuery<Member> typedQuery = em.createQuery("select m from Member m", Member.class);
                List<Member> resultList = typedQuery.getResultList();

                // 파라미터 바인딩
                Member bindingResult = em.createQuery("select m from Member m where m.username=:username", Member.class)
                    .setParameter("username", "Member1")
                    .getSingleResult();
                System.out.println("bindingResult = " + bindingResult.getUsername());

                // 결과가 정확히 하나가 보장되어야 함, 없거나(NoResultException) 많으면 Exception(NonUniqueResultException) 발생
                Member result = typedQuery.getSingleResult();
                System.out.println("result = " + result);

                // 반환 Type 이 명확하지 않을 때
                Query query = em.createQuery("select m.username, m.age from Member m where m.age > 10");
            }
        };

        jpaTemplate.transaction();
    }

}
