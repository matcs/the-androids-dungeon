package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id", updatable = false)
    private int purchaseId;

    @Column(name = "quantity", updatable = false)
    private int quantity;

    @Column(name = "amount", updatable = false)
    private float amount;

    @Column(name = "purchase_date", updatable = false)
    private Date purchaseDate;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_Id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    @JsonIgnore
    private Product product;

    public Purchase(int purchaseId, int quantity, float amount, Date purchaseDate) {
        this.purchaseId = purchaseId;
        this.quantity = quantity;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
    }

    public Purchase(int quantity, float amount, Date purchaseDate, User user, Product product) {
        this.quantity = quantity;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
        this.user = user;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return purchaseId == purchase.purchaseId && quantity == purchase.quantity && Float.compare(purchase.amount, amount) == 0 && Objects.equals(purchaseDate, purchase.purchaseDate) && Objects.equals(user, purchase.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, quantity, amount, purchaseDate);
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
}
