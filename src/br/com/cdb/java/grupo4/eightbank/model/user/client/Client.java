package br.com.cdb.java.grupo4.eightbank.model.user.client;

import br.com.cdb.java.grupo4.eightbank.enuns.ClientCategory;
import br.com.cdb.java.grupo4.eightbank.enuns.UserRole;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.User;

import java.time.LocalDate;

public class Client extends User {
    private ClientCategory clientCategory;
    private long cpf;
    private LocalDate dateOfBirth;
    private Address address;
    private Account account;

    public Client(long id,
                  String email,
                  char[] password,
                  String name,
                  ClientCategory clientCategory,
                  long cpf,
                  LocalDate dateOfBirth,
                  Address address,
                  Account account) {
        super(id, email, password, name, UserRole.CLIENT);
        this.clientCategory = clientCategory;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.account = account;
    }

    public long getCpf() {
        return cpf;
    }

    public ClientCategory getClientCategory() {
        return clientCategory;
    }
    public void setClientCategory(ClientCategory clientCategory) {
        this.clientCategory = clientCategory;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
