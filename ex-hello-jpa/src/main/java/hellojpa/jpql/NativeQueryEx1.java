package hellojpa.jpql;

import hellojpa.Member;
import hellojpa.template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.List;

public class NativeQueryEx1 {

    public static void main(String[] args) {

        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {

                Member member = new Member();
                member.setUserName("member1");
                em.persist(member);

                // flush -> commit, query , JPA 기술이 아닌 DB connection을 얻어와서 쿼리조회 시 flush가 안됨(수동 Flush필요)

                System.out.println("=========START======== ");
                List<Member> resultList = em.createNativeQuery("select MEMBER_ID, city, street, zipcode, USERNAME from MEMBER ", Member.class)
                        .getResultList();
                System.out.println("=========END======== ");

                System.out.println("resultList size = " + resultList.size());
            }
        };

        jpaTemplate.transaction();

    }




}
