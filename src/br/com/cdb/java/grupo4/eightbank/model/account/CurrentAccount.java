package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;

public class CurrentAccount extends Account {
    private double accountFee;

    public CurrentAccount(double balance, String ownerCpf, double accountFee) {
        super(balance, AccountType.CURRENT_ACCOUNT, ownerCpf);
        this.accountFee = accountFee;
    }

    public double getAccountFee() {
        return accountFee;
    }

    public void setAccountFee(double accountFee) {
        this.accountFee = accountFee;
    }

    @Override
    public String toString() {
        return "CurrentAccount{" +
                "accountFee=" + accountFee +
                "} " + super.toString();
    }
}
