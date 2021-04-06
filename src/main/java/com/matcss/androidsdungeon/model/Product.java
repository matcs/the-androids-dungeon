package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "name")
    private String name;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "stars")
    private float stars;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Purchase> purchases;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProductCategory> productCategories;

    public Product(int productId) {
        this.productId = productId;
    }

    public Product(boolean availability, String name, byte[] photo, String provider, float stars) {
        this.availability = availability;
        this.name = name;
        this.photo = photo;
        this.stars = stars;
    }

    public Product(int productId, boolean availability, String name, byte[] photo, String provider, float stars) {
        this.productId = productId;
        this.availability = availability;
        this.name = name;
        this.photo = photo;
        this.stars = stars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", availability=" + availability +
                ", name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", stars=" + stars +
                ", purchases=" + purchases +
                ", productCategories=" + productCategories +
                '}';
    }
}
