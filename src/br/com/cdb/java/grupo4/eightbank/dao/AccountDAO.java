package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.exceptions.AccountNotFoundException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InsufficientFundsException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InvalidValueException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.client.Client;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    List<Account> accountList = new ArrayList<>();

    public void listAccounts() {
        for (Account account : accountList) {
            System.out.println(account);
            System.out.println(account.getOwnerCPF());
        }
    }

    public void addAccount(Account account) {
        account.setAccountNumber(accountList.size() + 1);
        accountList.add(account);
    }

    public Account searchAccountByNumber(long accountNumber) {
        for (Account account : accountList) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public boolean setAccountOwner(Account account, String ownerCPF) {
        for (Account a : accountList) {
            if (a.equals(account)) {
                account.setOwnerCPF(ownerCPF);
                return true;
            }
        }
        return false;
    }

    public Account findAccountByNumber(long accountNumber) throws AccountNotFoundException {
        for (Account a : this.accountList) {
            if (a.getAccountNumber() == accountNumber) {
                return a;
            } else {
                throw new AccountNotFoundException("Conta não localizada!");
            }
        }
        return null;
    }

    public List<Account> findAccountByClientCPF(String cpf) throws AccountNotFoundException {
        List<Account> clientAccounts = new ArrayList<>();

        for (Account a : this.accountList) {
            if (a.getOwnerCPF().equals(cpf)) {
                clientAccounts.add(a);
            }
        }

        if (clientAccounts.isEmpty()) {
            throw new AccountNotFoundException("Não foram localizadas contas para este CPF!");
        }

        return clientAccounts;
    }

    public void withdrawValue(long accountNumber, double value)
            throws InsufficientFundsException, InvalidValueException, AccountNotFoundException {
        double balance = 0;
        if (value >= 0) {
            for (Account a : this.accountList) {
                if (a.getAccountNumber() == accountNumber) {
                    if (a.getBalance() >= value) {
                        a.setBalance(a.getBalance() - value);
                        balance = a.getBalance();
                    } else {
                        throw new InsufficientFundsException("Saldo Insuficiente!");
                    }
                } else {
                    throw new AccountNotFoundException("Conta de origem não localizada!");
                }
            }
        } else {
            throw new InvalidValueException("Valor inválido!");
        }
        System.out.println("Saque realizado com sucesso!\n"
                + "Saldo atual - R$ " + balance);
    }

    public void depositValue(long accountNumber, double value) throws InvalidValueException, AccountNotFoundException {
        double balance = 0;
        if (value > 0) {
            for (Account a : this.accountList) {
                if (a.getAccountNumber() == accountNumber) {
                    a.setBalance(a.getBalance() + value);
                    balance = a.getBalance();
                    break;
                } else {
                    throw new AccountNotFoundException("Conta de destino não localizada!");
                }
            }
        } else {
            throw new InvalidValueException("Valor do saque inválido!");
        }
        System.out.println("Depósito realizado com sucesso!\n"
                + "Saldo atual - R$ " + balance);
    }

    public void checkBalance(long accountNumber) throws AccountNotFoundException {
        double balance = 0;
        for(Account a: this.accountList){
            if(a.getAccountNumber() == accountNumber){
                balance = a.getBalance();
                break;
            } else {
                throw new AccountNotFoundException("Conta não localizada!");
            }
        }
        System.out.println("Seu saldo atual è: R$ " + balance);
    }

    public List<Account> searchAccountByCpf(String cpf) throws AccountNotFoundException {
        List<Account> clientAccountsList = new ArrayList<>();

        for(Account a: this.accountList){
            if(a.getOwnerCPF().equals(cpf)){
                clientAccountsList.add(a);
            }
        }

        if(clientAccountsList.size() == 0){
            throw new AccountNotFoundException("Não foram localizadas contas para o seu CPF!");
        } else {
            return clientAccountsList;
        }
    }
}
