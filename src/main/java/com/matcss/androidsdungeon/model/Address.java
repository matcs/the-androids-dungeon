package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue
    private int addressId;

    private String street;

    private String number;

    private String cep;

    private String neighborhood;

    private String reference;

    private int selected_address;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId == address.addressId && selected_address == address.selected_address && Objects.equals(street, address.street) && Objects.equals(number, address.number) && Objects.equals(cep, address.cep) && Objects.equals(neighborhood, address.neighborhood) && Objects.equals(reference, address.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, street, number, cep, neighborhood, reference, selected_address);
    }

    public Address(int addressId, String street, String number, String cep, String neighborhood, String reference, int selected_address) {
        this.addressId = addressId;
        this.street = street;
        this.number = number;
        this.cep = cep;
        this.neighborhood = neighborhood;
        this.reference = reference;
        this.selected_address = selected_address;
    }

    public Address(int addressId, String street, String number, String cep, String neighborhood, String reference, int selected_address, Customer customer) {
        this.addressId = addressId;
        this.street = street;
        this.number = number;
        this.cep = cep;
        this.neighborhood = neighborhood;
        this.reference = reference;
        this.selected_address = selected_address;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", cep='" + cep + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", reference='" + reference + '\'' +
                ", selected_address=" + selected_address +
                ", customer_id=" + customer +
                '}';
    }
}
