package com.matcss.androidsdungeon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue
    private int address_id;

    private String street;

    private String number;

    private String cep;

    private String neighborhood;

    private String reference;

    private int selected_address;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

}
