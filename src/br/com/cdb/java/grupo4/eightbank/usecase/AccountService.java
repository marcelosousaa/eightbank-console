package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.AccountDAO;
import br.com.cdb.java.grupo4.eightbank.exceptions.InsufficientFundsException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InvalidValueException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

public class AccountService {
    AccountDAO accountDAO = new AccountDAO();

    public Account createAccount() {
        Account account = new Account();
        accountDAO.addAccount(account);
        return account;
    }

    public void checkBalance(Account account){
        System.out.println(account.getBalance());
    }
    public void withdraw(Account account, double value) throws InsufficientFundsException, InvalidValueException {
        if (value >= 0) {
            if (account.getBalance() >= value) {
                account.setBalance(account.getBalance() - value);
            } else {
                throw new InsufficientFundsException("Saldo Insuficiente!");
            }
        } else {
            throw new InvalidValueException("Valor inválido!");
        }
    }

    public void deposit(Account account, double value) throws InvalidValueException {
        if (value > 0) {
            account.setBalance(account.getBalance() + value);
            System.out.println("Saldo atual - R$ " + account.getBalance());
        } else {
            throw new InvalidValueException("Valor inválido!");
        }
    }

    public void transfer(Account originAccount, Account targetAccount, double value){
        try {
            withdraw(originAccount, value);
            try{
                deposit(targetAccount, value);
            } catch (InvalidValueException e){
                e.getMessage();
            }
        } catch (InsufficientFundsException | InvalidValueException e) {
            e.getMessage();
        }
    }
}
