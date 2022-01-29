package me.chulgil.jpastart.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * @author cglee
 * 값 타입 매핑
 * 값 타입은 불변 객체(Immutable Object)로 설계해야한다.
 *  - 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않는다.
 * Why
 *   1. 인베디드 타입처럼 직접 정의한 값 타입은 객체 타입이다.
 *   2. 따라서 값을 복사해서 사용하면 공유참조로 인해 부작용이 발생한다.
 *   3. 객체 타입은 값을 직접 대입하는 것을 막을 방법이 없다.
 *   4. 객체의 공유 참조는 피할 수 없다.
 */
@Embeddable
public class Address {

    @Column(length = 10)
    private String city;
    @Column(length = 20)
    private String street;
    @Column(length = 5)
    private String zipcode;

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private String fullAddress() {
        return getCity() + " " + getStreet() + " " + getZipcode();
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(),
                address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
