package me.chulgil.jpastart.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cglee
 */
@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    // 중요 : 외래키가 있는 곳에 연관관계 주인을 설정 (Member.team)
    // mappedBy : 읽기만 가능
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
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

    public void addMember(Member member) {
        // 연관관계 편의 메소드 생성 : 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.
        member.setTeam(this);
        members.add(member);
    }
}