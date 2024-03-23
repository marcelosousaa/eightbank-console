package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.model.client.Client;

public class SavingsAccount extends Account {
    private double annualPercentageYield;

    public SavingsAccount(double balance, String ownerCpf, double annualPercentageYield) {
        super(balance, AccountType.SAVINGS_ACCOUNT, ownerCpf);
        this.annualPercentageYield = annualPercentageYield;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "annualPercentageYield=" + annualPercentageYield +
                "} " + super.toString();
    }
}
