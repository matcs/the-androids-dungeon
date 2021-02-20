package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;

    private String email;

    private String password;

    private String first_name;

    private String last_name;

    private String update_at;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private Set<CreditCard> creditCards;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private Set<Address> addresses;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private Set<Purchase> purchases;

    @OneToOne(mappedBy = "customer")
    @JsonManagedReference
    private Role role;

    public Customer(int customerId, String email, String password, String first_name, String last_name, String update_at) {
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.update_at = update_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId && email.equals(customer.email) && password.equals(customer.password) && first_name.equals(customer.first_name) && last_name.equals(customer.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, email, password, first_name, last_name);
    }
}
