package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private int addressId;

    @Column(name = "city", length = 120)
    private String city;

    @Column(name = "street", length = 120)
    private String street;

    @Column(name = "number", length = 15)
    private String number;

    @Column(name = "zipcode", length = 20)
    private String zipcode;

    @Column(name = "refence_point", length = 120)
    private String referencePoint;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User user;

    public Address(String city, String street, String number, String zipcode, String referencePoint, User user) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.referencePoint = referencePoint;
        this.user = user;
    }

    public Address(int addressId, String city, String street, String number, String zipcode, String referencePoint) {
        this.addressId = addressId;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.referencePoint = referencePoint;
    }

    public Address(int addressId, String city, String street, String number, String zipcode, String referencePoint, User user) {
        this.addressId = addressId;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.referencePoint = referencePoint;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return street.equals(address.street) && number.equals(address.number) && zipcode.equals(address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, zipcode);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", referencePoint='" + referencePoint + '\'' +
                '}';
    }
}
