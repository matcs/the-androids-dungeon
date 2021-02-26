package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    CreditCard findCreditCardByCreditCardId(int id);
}
