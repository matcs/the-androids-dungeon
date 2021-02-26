package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private String ccv;

    private String expiration_date;

    private boolean validated;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Customer customer;

    public CreditCard(int creditCardId, String number, String ccv, String expiration_date) {
        this.creditCardId = creditCardId;
        this.number = number;
        this.ccv = ccv;
        this.expiration_date = expiration_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return creditCardId == that.creditCardId && validated == that.validated && Objects.equals(number, that.number) && Objects.equals(ccv, that.ccv) && Objects.equals(expiration_date, that.expiration_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditCardId, number, ccv, expiration_date, validated);
    }
}
