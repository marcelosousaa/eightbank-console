package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.exceptions.UserNotFoundException;
import br.com.cdb.java.grupo4.eightbank.model.user.admin.Administrator;

import java.util.ArrayList;
import java.util.List;

public class AdministratorDAO {
    List<Administrator> administratorsList = new ArrayList<>();

    public void addClient(Administrator administrator){
        administrator.setId(administratorsList.size());
        administratorsList.add(administrator);
    }

    public Administrator searchClientById(int id) {
        for(Administrator administrator : administratorsList) {
            if(administrator.getId() == id) {
                return administrator;
            }
        }
        return null;
    }

    public Administrator searchClientByEmail(String email) throws UserNotFoundException {
        for(Administrator administrator : administratorsList) {
            if(administrator.getEmail().equals(email)) {
                return administrator;
            } else {
                throw new UserNotFoundException("Administrador não localizado!");
            }
        }
        return null;
    }

    public void listAdministrators() {
        for(Administrator administrator : this.administratorsList) {
            System.out.println(administrator.getName());
        }
    }
}
