package br.com.cdb.java.grupo4.eightbank.model.user.client;

import br.com.cdb.java.grupo4.eightbank.enuns.ClientCategory;
import br.com.cdb.java.grupo4.eightbank.enuns.UserRole;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.User;

import java.time.LocalDate;
import java.util.List;

public class Client extends User {
    private String cpf;
    private LocalDate dateOfBirth;
    private Address address;
    private ClientCategory clientCategory;
    private List<Account> accountList;
    private String phoneNumber;
    private double grossMonthlyIncome;

    public Client(
            double grossMonthlyIncome,
            String email,
            String password,
            String name,
            ClientCategory clientCategory,
            String cpf,
            LocalDate dateOfBirth,
            Address address,
            String phoneNumber) {
        super(email, password, name, UserRole.CLIENT);
        this.clientCategory = clientCategory;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.grossMonthlyIncome = grossMonthlyIncome;
        this.phoneNumber = phoneNumber;
    }

    public String getCpf() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public double getGrossMonthlyIncome() {
        return grossMonthlyIncome;
    }

    public void setGrossMonthlyIncome(double grossMonthlyIncome) {
        this.grossMonthlyIncome = grossMonthlyIncome;
    }

    @Override
    public String toString() {
        return "Client{" +
                "cpf='" + cpf + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                ", clientCategory=" + clientCategory +
                ", accountList=" + accountList +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", grossMonthlyIncome=" + grossMonthlyIncome +
                "} " + super.toString();
    }
}
