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

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private long phoneNumber;

    public Client(){}

    public Client(String email,
                  String password,
                  String name,
                  ClientCategory clientCategory,
                  String cpf,
                  LocalDate dateOfBirth,
                  Address address,
                  Account account) {
        super(email, password, name, UserRole.CLIENT);
        this.clientCategory = clientCategory;
        this.cpf = Long.parseLong(cpf);
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
