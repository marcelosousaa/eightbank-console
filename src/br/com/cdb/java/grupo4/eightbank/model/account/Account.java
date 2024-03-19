package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

public class Account {
    private long branch;
    private long accountNumber;
    private double balance;
    private Client owner;

    public Account(){}

    public Account(long branch, long accountNumber, double balance, Client owner) {
        this.branch = branch;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }

    public long getBranch() {
        return branch;
    }

    public long getAccountNumber() {
        return accountNumber;
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
}
