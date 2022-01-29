package me.chulgil.jpastart.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cglee
 * 실무에서는 모든 연관관계를 지연 로딩으로 설정한다.
 *  - @ManyToOne, @OneToOne은 기본이 즉시로딩이므로 Lazy로 설정
 * Why :
 *  1. 즉시로딩으로 적용하면 예상치 못한 SQL이 발생
 *  2. 즉시로딩은 JPQL에서 N+1문제를 일으킨다.
 *  3. @OneToMany, @ManyToMany는 기본이 지연로딩
 */
@Entity
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="CATEGORY_ID")
    private Long Id;

    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
        joinColumns = @JoinColumn(name = "CATEGORY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Item> items = new ArrayList<>();

}
