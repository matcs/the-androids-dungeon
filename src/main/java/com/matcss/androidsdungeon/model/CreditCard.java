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
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int credit_card_id;

    private String number;

    private String ccv;

    private String expiration_date;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

}
