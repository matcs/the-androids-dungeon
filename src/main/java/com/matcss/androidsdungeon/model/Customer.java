package com.matcss.androidsdungeon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private Set<CreditCard> creditCards;

    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses;

    @OneToMany(mappedBy = "customer")
    private Set<Purchase> purchases;

    @OneToOne(mappedBy = "customer")
    private Role role;

}
