package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

public class SavingsAccount extends Account {
    private double annualPercentageYield;

    public SavingsAccount(double balance, Client owner, double annualPercentageYield) {
        super(balance, AccountType.SAVINGS_ACCOUNT, owner);
        this.annualPercentageYield = annualPercentageYield;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "annualPercentageYield=" + annualPercentageYield +
                "} " + super.toString();
    }
}
