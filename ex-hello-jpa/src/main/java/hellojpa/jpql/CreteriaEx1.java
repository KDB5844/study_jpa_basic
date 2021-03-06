package hellojpa.jpql;

import hellojpa.Member;
import hellojpa.Team;
import hellojpa.template.JpaTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CreteriaEx1 {

    public static void main(String[] args) {

        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                // Criteria 사용 준비
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Member> cq = cb.createQuery(Member.class);

                Root<Member> m = cq.from(Member.class);

                cq.select(m).where(cb.equal(m.get("userName"), "kim"));
                List<Member> result = em.createQuery(cq)
                        .getResultList();

                String username = "test";
                if (username != null) {
                    cq = cq.select(m).where(cb.equal(m.get("userName"), username));
                }
            }
        };


        jpaTemplate.transaction();

    }

}
