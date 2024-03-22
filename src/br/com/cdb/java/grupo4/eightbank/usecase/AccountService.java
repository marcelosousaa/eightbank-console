package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.AccountDAO;
import br.com.cdb.java.grupo4.eightbank.exceptions.AccountNotFoundException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InsufficientFundsException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InvalidValueException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.account.CurrentAccount;
import br.com.cdb.java.grupo4.eightbank.model.account.SavingsAccount;
import br.com.cdb.java.grupo4.eightbank.model.client.Client;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    AccountDAO accountDAO = new AccountDAO();

    public Account createCurrentAccount(Client client, double accountFee) {
        double balance = 0;
        Account currentAccount = new CurrentAccount(balance, client, accountFee);
        accountDAO.addAccount(currentAccount);
        return currentAccount;
    }

    public Account createSavingsAccount(Client client, double annualPercentageYield) {
        double balance = 0;
        Account savingsAccount = new SavingsAccount(balance, client, annualPercentageYield);
        accountDAO.addAccount(savingsAccount);
        return savingsAccount;
    }

    public void setAccountOwner(Account account, Client client) {
        accountDAO.setAccountOwner(account, client);
    }

    public void checkBalance(long accountNumber)   {
        try{
            accountDAO.checkBalance(accountNumber);
        } catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void withdraw(long accountNumber, double value) throws InsufficientFundsException, InvalidValueException, AccountNotFoundException {
        accountDAO.withdrawValue(accountNumber, value);
        checkBalance(accountNumber);
    }

    public void deposit(long accountNumber, double value)  {
        try{
            accountDAO.depositValue(accountNumber, value);
        } catch (AccountNotFoundException | InvalidValueException e){
            System.out.println(e.getMessage());
        }


    }

    public void transfer(long originAccountNumber, long targetAccountNumber, double value) {
        try {
            accountDAO.withdrawValue(originAccountNumber, value);
            try {
                accountDAO.depositValue(targetAccountNumber, value);
            } catch (InvalidValueException e) {
                System.out.println(e.getMessage());
            }
        } catch (InsufficientFundsException | InvalidValueException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferPix(long accountNumber, String pixKey, double value) {
        try {
            accountDAO.withdrawValue(accountNumber, value);
        } catch (InsufficientFundsException | InvalidValueException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listAccounts() {
        accountDAO.listAccounts();
    }

    public List<Account> findAccountsByCPF(String cpf) throws AccountNotFoundException {
        return accountDAO.searchAccountByCpf(cpf);
    }
}
