package jpql;

import jpql.domain.Member;
import jpql.domain.Team;
import template.JpaTemplate;

import javax.persistence.EntityManager;
import java.util.List;

public class JpqlEx12 {
    public static void main(String[] args) {
        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                Team teamA = new Team();
                teamA.setName("teamA");

                em.persist(teamA);

                Team teamB = new Team();
                teamB.setName("teamB");

                em.persist(teamB);

                Member member = new Member("회원1", 10);
                member.changeTeam(teamA);
                em.persist(member);

                Member member2 = new Member("회원2", 70);
                member2.changeTeam(teamA);
                em.persist(member2);

                Member member3 = new Member("회원3", 40);
                member3.changeTeam(teamB);
                em.persist(member3);

                // FLUSH 자동 호출 commit, query, flush
                // 벌크 연산 주의사항 -> 벌크 연산 부터 실행 or 벌크 연산 후 영속성 컨텍스트 초기화
                // 벌크 연산은 DB에만 반영, 영속성 컨텍스 변동 없음
                int resultCount = em.createQuery("update Member m set m.age = 20")
                        .executeUpdate();

                System.out.println("resultCount = " + resultCount);

                // 영속성 컨텍스트에는 예전 값을 들고 있음. (== 벌크 연산 후 영속성 컨텍스트 초기화)
                System.out.println("member.getAge() = " + member.getAge());
                System.out.println("member2.getAge() = " + member2.getAge());
                System.out.println("member3.getAge() = " + member3.getAge());

                // 대처법 ↓
                em.clear();

                List<Member> members = em.createQuery("select m from Member m", Member.class)
                        .getResultList();

                // 변경 된 값을 DB에서 재조회 하기 떄문에 정합성이 맞음
                System.out.println("members = " + members);


            }
        };
        jpaTemplate.transaction();
    }
}
