package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.exceptions.UserNotFoundException;
import br.com.cdb.java.grupo4.eightbank.model.user.User;
import br.com.cdb.java.grupo4.eightbank.model.user.admin.Administrator;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    List<User> userList = new ArrayList<>();

    public void addUser(User user){
        user.setId(userList.size() + 1);
        userList.add(user);
    }

    public void listUsers() {
        for(User user : this.userList) {
            System.out.println(user.getName());
        }
    }

    public User searchUserByEmail(String email) throws UserNotFoundException {
        for(User user : userList) {
            if(user.getEmail().equals(email)) {
                return user;
            } else {
                throw new UserNotFoundException("Usuário não localizado!");
            }
        }
        return null;
    }

    public User searchUserById(int id) throws UserNotFoundException{
        for(User user : userList) {
            if(user.getId() == id) {
                return user;
            } else {
                throw new UserNotFoundException("Usuário não localizado!");
            }
        }
        return null;
    }


}
