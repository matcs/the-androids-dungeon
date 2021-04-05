package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.CreditCardRepository;
import com.matcss.androidsdungeon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<CreditCard> findAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public CreditCard findCreditCardById(int id) {
        return creditCardRepository.findCreditCardByCreditCardId(id);
    }

    public CreditCard saveCreditCard(int customerId, CreditCard obj) {
        Customer customer = customerRepository.findCustomerByCustomerId(customerId);
        if (customer == null)
            return null;
        obj.setCustomer(customer);

        return creditCardRepository.save(obj);
    }

    public CreditCard updateCreditCard(int id, CreditCard creditCardBody) {
        CreditCard creditCard = findCreditCardById(id);

        creditCard.setNumber(creditCardBody.getNumber());
        creditCard.setCvv(creditCardBody.getCvv());
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
