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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;

    private boolean availability;

    private String name;

    private byte[] photo;

    private String provider;

    private float stars;

    @OneToMany
    @JoinColumn(nullable = false)
    private Set<Purchase> purchases;

    @OneToMany(mappedBy = "product")
    private Set<ProductCategory> productCategories;
}
