package br.com.cdb.java.grupo4.eightbank.model.card;

import br.com.cdb.java.grupo4.eightbank.enuns.CreditCardBrand;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Card {
    private long number;
    private LocalDateTime expirationDate;
    private long cardVerificationValue;
    private CreditCardBrand creditCardBrand;
    private LocalDate bestDateToBuy;
    private Client owner;
}
