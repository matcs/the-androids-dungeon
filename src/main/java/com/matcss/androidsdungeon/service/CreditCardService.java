package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.repository.CreditCardRepository;
import com.matcss.androidsdungeon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CreditCard> findAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public CreditCard findCreditCardById(int id) {
        return creditCardRepository.findCreditCardByCreditCardId(id);
    }

    public CreditCard saveCreditCard(int customerId, CreditCard obj) {
        User user = userRepository.findUserByUserId(customerId);
        if (user == null)
            return null;
        obj.setUser(user);

        return creditCardRepository.save(obj);
    }

    public CreditCard updateCreditCard(int id, CreditCard creditCardBody) {
        CreditCard creditCard = findCreditCardById(id);

        creditCard.setNumber(creditCardBody.getNumber());
        creditCard.setCcv(creditCardBody.getCcv());
        creditCard.setExpirationDate(creditCard.getExpirationDate());
        creditCard.setValidated(false);

        return creditCardRepository.save(creditCard);
    }

    public CreditCard deleteCreditCard(int id) {
        CreditCard creditCard = creditCardRepository.findCreditCardByCreditCardId(id);
        if (creditCard != null) creditCardRepository.delete(creditCard);
        return creditCard;
    }
}
