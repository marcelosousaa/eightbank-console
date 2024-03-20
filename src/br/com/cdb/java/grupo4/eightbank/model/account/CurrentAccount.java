package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

public class CurrentAccount extends Account{
    private double accountFee;

    public CurrentAccount(long branch, long accountNumber, double balance, Client owner, double accountFee) {
        super(AccountType.CURRENT_ACCOUNT);
        this.accountFee = accountFee;
    }
}
