package br.com.cdb.java.grupo4.eightbank.model.user.admin;

import br.com.cdb.java.grupo4.eightbank.model.user.User;

public class Administrator extends User {
    public Administrator(long id, String email, char[] password, String name) {
        super(id, email, password, name, 'A');
    }
}
