package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Purchase {

    @Id
    @GeneratedValue
    private int purchase_id;

    private int quantity;

    private float amount;

    private String purchase_date;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Products product;
}
