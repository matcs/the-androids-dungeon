package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Products {

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
    @JsonManagedReference
    private Set<ProductCategory> productCategories;

    @Override
    public String toString() {
        return "Products{" +
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
        Products products = (Products) o;
        return productId == products.productId && availability == products.availability && Float.compare(products.stars, stars) == 0 && Objects.equals(name, products.name) && Arrays.equals(photo, products.photo) && Objects.equals(provider, products.provider);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(productId, availability, name, provider, stars);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
