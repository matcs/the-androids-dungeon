package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.CreditCardRepository;
import com.matcss.androidsdungeon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService implements CRUDServices<CreditCard> {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CustomerRepository customerRepository;

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

    public CreditCard create(int customerId, CreditCard obj) {
        Customer customer = customerRepository.findCustomerByCustomerId(customerId);
        if (customer == null)
            return null;
        obj.setCustomer(customer);

        return creditCardRepository.save(obj);
    }

    @Override
    public CreditCard update(int id, CreditCard creditCardBody) {
        CreditCard creditCard = findById(id);

        creditCard.setNumber(creditCardBody.getNumber());
        creditCard.setCvv(creditCardBody.getCvv());
        creditCard.setExpirationDate(creditCard.getExpirationDate());
        creditCard.setValidated(false);

        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard delete(int id) {
        CreditCard creditCard = creditCardRepository.findCreditCardByCreditCardId(id);
        if(creditCard != null) creditCardRepository.delete(creditCard);
        return creditCard;
    }
}
