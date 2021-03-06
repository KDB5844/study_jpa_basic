package hellojpa;

import hellojpa.template.JpaTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                Team team = new Team();
                team.setName("teamA");
                em.persist(team);

                Member member = new Member();
                member.setUserName("kim");
                member.changeTeam(team);
                em.persist(member);

                em.flush();
                em.clear();

//            Member findMember = em.find(Member.class, member.getId());
//            System.out.println("member id = " + findMember.getId());
//            System.out.println("member name = " + findMember.getUserName());

                Member findMember = em.find(Member.class, member.getId());
                Team findTeam = findMember.getTeam();
                System.out.println("team = " + findTeam);
//            printMember(findMember);
                // printMemberAndTeam(findMember);

            }
        }.transaction();


    }

    private static void printMember(Member findMember) {
        System.out.println("member id= " + findMember.getId());
        System.out.println("member name= " + findMember.getUserName());
    }

    private static void printMemberAndTeam(Member member) {
        String userName = member.getUserName();
        System.out.println("userName = " + userName);
        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}
