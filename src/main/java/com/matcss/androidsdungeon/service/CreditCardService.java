package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CreditCardService implements CRUDServices<CreditCard> {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard findById(int id) {
        return creditCardRepository.findCreditCardByCredit_card_id(id);
    }

    @Override
    public CreditCard create(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard update(int id, CreditCard creditCardBody) {
        CreditCard creditCard = findById(id);

        creditCard.setNumber(creditCardBody.getNumber());
        creditCard.setCcv(creditCardBody.getCcv());
        creditCard.setExpiration_date(creditCard.getExpiration_date());
        creditCard.setValidated(false);
        CreditCard updatedCreditCard = creditCardRepository.save(creditCard);

        return updatedCreditCard;
    }

    @Override
    public void delete(int id) {
        CreditCard creditCard = creditCardRepository.findCreditCardByCredit_card_id(id);
        creditCardRepository.delete(creditCard);
    }
}
