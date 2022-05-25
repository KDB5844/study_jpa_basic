package hellojpa.collection;

import hellojpa.Member;
import hellojpa.embedded.Address;
import hellojpa.template.JpaTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class CollectionExam {

    public static void main(String[] args) {

        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                Member member = new Member();
                member.setUserName("member1");
                member.setHomeAddress(new Address("homeCity", "street", "10000"));

                member.getFavoriteFoods().add("치킨");
                member.getFavoriteFoods().add("족발");
                member.getFavoriteFoods().add("피자");

                member.getAddressHistory().add(new Address("old1", "street", "10000"));
                member.getAddressHistory().add(new Address("old2", "street", "10000"));

                em.persist(member);

                em.flush();
                em.clear();

                System.out.println("START======================");
                Member findMember = em.find(Member.class, member.getId());

                // Collection 은 기본값이 LAZY Loading
                List<Address> addressHistory = findMember.getAddressHistory();
                for (Address address : addressHistory) {
                    System.out.println("address = " + address.getCity());
                }
                System.out.println("END======================");
            }
        };

        jpaTemplate.transaction();
    }

}
