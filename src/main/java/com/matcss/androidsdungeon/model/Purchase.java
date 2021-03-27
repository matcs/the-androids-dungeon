package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue
    private int purchaseId;

    private int quantity;

    private float amount;

    private String purchaseDate;

    @ManyToOne
    @JoinColumn(nullable = false, name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    @JsonIgnore
    private Product product;

    public Purchase(int purchaseId, int quantity, float amount, String purchaseDate) {
        this.purchaseId = purchaseId;
        this.quantity = quantity;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
    }

    public Purchase(int quantity, float amount, String purchaseDate, Customer customer, Product product) {
        this.quantity = quantity;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
        this.customer = customer;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "purchaseId=" + purchaseId +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", purchase date=" + purchaseDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return purchaseId == purchase.purchaseId && quantity == purchase.quantity && Float.compare(purchase.amount, amount) == 0 && Objects.equals(purchaseDate, purchase.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, quantity, amount, purchaseDate);
    }
}
