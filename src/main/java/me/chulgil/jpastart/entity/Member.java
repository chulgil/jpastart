package me.chulgil.jpastart.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cglee
 */
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public void changeTeam(Team team) {
        this.team = team;
        // 연관관계 편의 메소드 생성 : 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.
        team.getMembers().add(this);
    }

    public Team getTeam() {
        return team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}


