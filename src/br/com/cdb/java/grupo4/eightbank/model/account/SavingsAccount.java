package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

public class SavingsAccount extends Account {
    private double annualPercentageYield;

    public SavingsAccount(long branch, long accountNumber, double balance, Client owner, double annualPercentageYield) {
        this.annualPercentageYield = annualPercentageYield;
    }
}
