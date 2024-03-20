package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.exceptions.UserNotFoundException;
import br.com.cdb.java.grupo4.eightbank.model.user.User;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    List<User> usersList = new ArrayList<>();

    public User searchUserByEmail(String email) throws UserNotFoundException {
        for(User user : usersList) {
            if(user.getEmail().equals(email)) {
                return user;
            } else {
                throw new UserNotFoundException("Usuário não localizado!");
            }
        }
        return null;
    }

    public void listUsers() {
        for(User user : this.usersList) {
            System.out.println(user.getName());
        }
    }
}
