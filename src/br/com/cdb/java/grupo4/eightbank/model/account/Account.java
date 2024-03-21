package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

public abstract class Account {
    private long accountNumber;
    private double balance;
    private AccountType accountType;
    private Client owner;

    public Account(double balance, AccountType accountType, Client owner) {
        this.balance = balance;
        this.accountType = accountType;
        this.owner = owner;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner){
        this.owner = owner;
    }
}
