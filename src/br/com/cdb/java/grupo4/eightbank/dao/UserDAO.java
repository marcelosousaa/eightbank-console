package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.exceptions.UserNotFoundException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.User;
import br.com.cdb.java.grupo4.eightbank.model.user.admin.Administrator;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO {

    List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        user.setId(userList.size() + 1);
        userList.add(user);
    }

    public void listUsers() {
        for (User user : this.userList) {
            if(user instanceof Client){
                System.out.println(((Client) user));
            }
        }
    }

    public User searchUserByEmail(String email) {
        for (User user : userList) {
            if (Objects.equals(user.getEmail(), email)) {
                return user;
            }
        }
        return null;
    }

    public User searchUserById(int id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }


    public boolean searchClientByCPF(String cpf) {
        for (User user : userList) {
            if(user instanceof Client){
                if (((Client) user).getCpf().equals(cpf)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public void addAccounts(Client client, List<Account> clientAccountsList) {
        for(User u : userList){
            if(u instanceof Client){
                if(((Client) u).getCpf().equals(client.getCpf())){
                    ((Client) u).setAccountList(clientAccountsList);
                }
            }
        }
    }
}
