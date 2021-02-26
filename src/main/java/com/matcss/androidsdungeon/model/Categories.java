package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int category_id;

    private String category_name;

    public String getCategory_name() {
        return category_name;
    }

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private Set<ProductCategory> productCategories;

    @Override
    public String toString() {
        return "Categories{" +
                "category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                ", productCategories=" + productCategories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return category_id == that.category_id && Objects.equals(category_name, that.category_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, category_name);
    }
}
