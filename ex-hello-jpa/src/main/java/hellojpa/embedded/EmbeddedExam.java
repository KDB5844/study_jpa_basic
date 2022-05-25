package hellojpa.embedded;

import hellojpa.Member;
import hellojpa.template.JpaTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class EmbeddedExam {

    public static void main(String[] args) {

        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                Member member = new Member();
                member.setUserName("hello");
                member.setHomeAddress(new Address("seoul", "2387", "1238-123"));
                member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));

                em.persist(member);
            }
        };

        jpaTemplate.transaction();

    }

}
