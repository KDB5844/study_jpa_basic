package hellojpa;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity{

    @Id     // PK
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) {
        team.getMembers().add(this);        // 연관관계 매핑 - 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정
        this.team = team;
    }
}
