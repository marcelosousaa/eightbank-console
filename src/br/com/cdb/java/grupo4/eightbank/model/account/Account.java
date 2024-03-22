package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

public class Account {
    private long branch;
    private long accountNumber;
    double balance;
    private AccountType accountType;
    private Client owner;

    public Account(AccountType accountType) {
        this.branch = 1;
        this.balance = 0;
        this.accountType = accountType;
    }

    public Account(String accountNumber, AccountType accountType, Client owner, double balance) {
    }

    public long getBranch() {
        return branch;
    }
    public void setBranch(long branch) {
        this.branch = branch;
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