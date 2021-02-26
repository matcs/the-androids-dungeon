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
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int product_category_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Products product;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Categories category;

    @Override
    public String toString() {
        return "ProductCategory{" +
                "product_category_id=" + product_category_id +
                ", product=" + product.getProductId() +
                ", category=" + category.getCategory_name() +
                '}';
    }

}
