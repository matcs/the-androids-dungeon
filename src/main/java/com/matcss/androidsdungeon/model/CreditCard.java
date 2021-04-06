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
    @Column(name = "credit_card_id")
    private int creditCardId;

    @Column(name = "number", unique = true, length = 20)
    private String number;

    @Column(name = "ccv", length = 5)
    private String ccv;

    @Column(name = "expiration_date", length = 6)
    private String expirationDate;

    @Column(name = "validated")
    private boolean validated;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User user;

    public CreditCard(int creditCardId, String number, String ccv, String expirationDate) {
        this.creditCardId = creditCardId;
        this.number = number;
        this.ccv = ccv;
        this.expirationDate = expirationDate;
    }

    public CreditCard(String number, String ccv, String expirationDate, boolean validated) {
        this.number = number;
        this.ccv = ccv;
        this.expirationDate = expirationDate;
        this.validated = validated;
    }

    public CreditCard(String number, String ccv, String expirationDate, boolean validated, User user) {
        this.number = number;
        this.ccv = ccv;
        this.expirationDate = expirationDate;
        this.validated = validated;
        this.user = user;
    }

    public CreditCard(int creditCardId, String number, String ccv, String expirationDate, boolean validated) {
        this.creditCardId = creditCardId;
        this.number = number;
        this.ccv = ccv;
        this.expirationDate = expirationDate;
        this.validated = validated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return creditCardId == that.creditCardId && Objects.equals(number, that.number) && Objects.equals(ccv, that.ccv) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditCardId, number, ccv, expirationDate);
    }
}
