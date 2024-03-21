package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    List<Account> accountList = new ArrayList<>();

    public void listAccounts(){
        for(Account account : accountList){
            System.out.println(account);
            System.out.println(account.getOwner());
        }
    }

    public void addAccount(Account account){
        account.setAccountNumber(accountList.size());
        accountList.add(account);
    }

    public Account searchAccountByNumber(long accountNumber) {
        for(Account account : accountList) {
            if(account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public boolean setAccountOwner(long accountNumber, Client client){
        for(Account account : accountList) {
            if(account.getAccountNumber() == accountNumber) {
                account.setOwner(client);
                return true;
            }
        }
        return false;
    }

}
