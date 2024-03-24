package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.enuns.AnsiColors;
import br.com.cdb.java.grupo4.eightbank.enuns.SystemMessages;
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
        Account account = null;

        for (Account a : this.accountList) {
            if (a.getAccountNumber() == accountNumber) {
                account = a;
                break;
            }
        }

        if (account == null) {
            throw new AccountNotFoundException("\n"
                    + AnsiColors.ANSI_RED.getAnsiColorCode()
                    + "Conta não está na lista!"
                    + AnsiColors.ANSI_RESET.getAnsiColorCode()
                    + "\n");
        } else {
            return account;
        }
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
        boolean validateAccountSearch = false;
        double balance = 0;
        if (value >= 0) {
            for (Account a : this.accountList) {
                if (a.getAccountNumber() != accountNumber) {
                    System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
                } else if (a.getBalance() >= value) {
                    validateAccountSearch = true;
                    a.setBalance(a.getBalance() - value);
                    balance = a.getBalance();
                    break;
                } else {
                    throw new InsufficientFundsException("Saldo Insuficiente!");
                }
            }

            if (!validateAccountSearch) {
                throw new AccountNotFoundException("Conta de origem não localizada!");
            }

        } else {
            throw new InvalidValueException("Valor inválido!");
        }
        System.out.println("Saque realizado com sucesso!\n"
                + "Saldo atual - R$ " + balance);
    }

    public void depositValue(long accountNumber, double value) throws InvalidValueException, AccountNotFoundException {
        boolean validateAccountSearch = false;
        double balance = 0;
        if (value > 0) {
            for (Account a : this.accountList) {
                if (a.getAccountNumber() != accountNumber) {
                    System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
                } else {
                    validateAccountSearch = true;
                    a.setBalance(a.getBalance() + value);
                    balance = a.getBalance();
                    break;
                }
            }
        } else {
            throw new InvalidValueException(SystemMessages.INVALID_VALUE.getFieldName());
        }

        if (!validateAccountSearch) {
            throw new AccountNotFoundException("Conta não localizada!");
        } else {
            System.out.println("Depósito realizado com sucesso!\n"
                    + "Saldo atual - R$ " + balance);
        }
    }

    public double checkBalance(long accountNumber) throws AccountNotFoundException {
        boolean validateAccountSearch = false;
        double balance = 0;
        for (Account a : this.accountList) {
            if (a.getAccountNumber() != accountNumber) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                validateAccountSearch = true;
                balance = a.getBalance();
                break;
            }
        }
        if(!validateAccountSearch){
            throw new AccountNotFoundException("Conta não localizada!");
        } else {
            return balance;
        }
    }

    public List<Account> searchAccountByCpf(String cpf) throws AccountNotFoundException {
        List<Account> clientAccountsList = new ArrayList<>();

        for (Account a : this.accountList) {
            if (a.getOwnerCPF().equals(cpf)) {
                clientAccountsList.add(a);
            }
        }

        if (clientAccountsList.isEmpty()) {
            throw new AccountNotFoundException("Não foram localizadas contas para o seu CPF!");
        } else {
            return clientAccountsList;
        }
    }
}
