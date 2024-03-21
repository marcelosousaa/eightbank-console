package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

public class CurrentAccount extends Account{
    private double accountFee;

    public CurrentAccount(double balance, Client owner, double accountFee) {
        super(balance, AccountType.CURRENT_ACCOUNT, owner);
        this.accountFee = accountFee;
    }

    @Override
    public String toString() {
        return "CurrentAccount{" +
                "accountFee=" + accountFee +
                "} " + super.toString();
    }
}
