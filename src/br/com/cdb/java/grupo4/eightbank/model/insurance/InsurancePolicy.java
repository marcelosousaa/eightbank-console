package br.com.cdb.java.grupo4.eightbank.model.insurance;

import br.com.cdb.java.grupo4.eightbank.model.card.CreditCard;

import java.time.LocalDate;

public class InsurancePolicy {
    private CreditCard creditCard;
    private long policyNumber;
    private LocalDate issueDate;
    private double policyValue;
    private String policyConditions;
}
