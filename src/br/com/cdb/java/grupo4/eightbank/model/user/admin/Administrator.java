package br.com.cdb.java.grupo4.eightbank.model.user.admin;

import br.com.cdb.java.grupo4.eightbank.enuns.UserRole;
import br.com.cdb.java.grupo4.eightbank.model.user.User;

public class Administrator extends User {
    public Administrator(String email, String password, String name) {
        super(email, password, name, UserRole.ADMINISTRATOR);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
