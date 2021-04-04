package com.matcss.androidsdungeon.model;

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
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int creditCardId;

    private String number;

    private String cvv;

    private String expirationDate;

    private boolean validated;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Customer customer;

    public CreditCard(int creditCardId, String number, String cvv, String expirationDate) {
        this.creditCardId = creditCardId;
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public CreditCard(String number, String cvv, String expirationDate, boolean validated) {
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.validated = validated;
    }

    public CreditCard(String number, String cvv, String expirationDate, boolean validated, Customer customer) {
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.validated = validated;
        this.customer = customer;
    }

    public CreditCard(int creditCardId, String number, String cvv, String expirationDate, boolean validated) {
        this.creditCardId = creditCardId;
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.validated = validated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return creditCardId == that.creditCardId && Objects.equals(number, that.number) && Objects.equals(cvv, that.cvv) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditCardId, number, cvv, expirationDate);
    }
}
