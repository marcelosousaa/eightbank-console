package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.exceptions.InsufficientFundsException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InvalidValueException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;

public class AccountService {

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
