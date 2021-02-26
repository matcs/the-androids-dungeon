package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService implements CRUDServices<CreditCard> {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard findById(int id) {
        return creditCardRepository.findCreditCardByCreditCardId(id);
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

        return creditCardRepository.save(creditCard);
    }

    @Override
    public void delete(int id) {
        CreditCard creditCard = creditCardRepository.findCreditCardByCreditCardId(id);
        creditCardRepository.delete(creditCard);
    }
}
