package me.chulgil.jpastart.entity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author cglee
 * 매핑정보 상속
 *  - 상속관계 매핑이 아니다.
 *  - 엔티티가 아니기 때문에 테이블과 매핑이 되지 않는다.
 *  - 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공한다.
 *  - 조회 , 검색 불가 (em.find(BaseEntity) 불가)
 *  - 직접 생성해서 사용할 일이 없으므로 추상 클래스권장
 */
@MappedSuperclass
public abstract class BaseEntity {

    private String createBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
