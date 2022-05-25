package hellojpa.collection;

import hellojpa.Member;
import hellojpa.embedded.Address;
import hellojpa.template.JpaTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class UpdateExam {

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

                // homeCity -> newCity
                Address newAddress = new Address("newCity", findMember.getHomeAddress().getStreet(), findMember.getHomeAddress().getZipcode());
                findMember.setHomeAddress(newAddress);

                // 치킨 -> 한식
                Set<String> favoriteFoods = findMember.getFavoriteFoods();
                favoriteFoods.remove("치킨");
                favoriteFoods.add("한식");

                // old1 -> new1
                List<Address> addressList = findMember.getAddressHistory();

                addressList.remove(new Address("old1", "street", "10000"));
                addressList.add(new Address("new1", "street", "10000"));

                System.out.println("END======================");
            }
        };

        jpaTemplate.transaction();

    }

}
