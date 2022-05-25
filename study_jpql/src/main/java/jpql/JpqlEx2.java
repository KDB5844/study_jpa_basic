package jpql;

import jpql.domain.Member;
import jpql.dto.MemberDTO;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.List;

public class JpqlEx2 {

    public static void main(String[] args) {
        JpaTemplate jpaTemplate = new JpaTemplate(){
            @Override
            public void execute(EntityManager em) {
                Member member = new Member();
                member.setUsername("member1");
                member.setAge(10);
                em.persist(member);

                em.flush();
                em.clear();

                // 엔티티 프로젝션 시 모든 결과가 영속성 컨텍스에 관리 됨
                List<Member> memberList = em.createQuery("select m from Member m", Member.class)
                        .getResultList();

                // 스칼라 프로젝션
                List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m")
                        .getResultList();

                Object[] result = resultList.get(0);
                System.out.println("username = " + result[0]);
                System.out.println("age = " + result[1]);

                // DTO 로 스칼라 프로젝션 뽑기
                List<MemberDTO> dtoList = em.createQuery("select new jpql.dto.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                        .getResultList();

                for (MemberDTO memberDTO : dtoList) {
                    System.out.println("memberDTO username = " + memberDTO.getUsername());
                    System.out.println("memberDTO age = " + memberDTO.getAge());
                }

                Member findMember = memberList.get(0);
                findMember.setAge(20);

            }
        };

        jpaTemplate.transaction();
    }

}
