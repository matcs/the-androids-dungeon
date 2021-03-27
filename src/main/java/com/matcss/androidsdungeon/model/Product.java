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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int productId;

    private boolean availability;

    private String name;

    private byte[] photo;

    private String provider;

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
        this.provider = provider;
        this.stars = stars;
    }

    public Product(int productId, boolean availability, String name, byte[] photo, String provider, float stars) {
        this.productId = productId;
        this.availability = availability;
        this.name = name;
        this.photo = photo;
        this.provider = provider;
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", availability=" + availability +
                ", name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", provider='" + provider + '\'' +
                ", stars=" + stars +
                ", purchases=" + purchases +
                ", productCategories=" + productCategories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && availability == product.availability && Float.compare(product.stars, stars) == 0 && Objects.equals(name, product.name) && Arrays.equals(photo, product.photo) && Objects.equals(provider, product.provider);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(productId, availability, name, provider, stars);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
