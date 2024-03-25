package br.com.cdb.java.grupo4.eightbank.model.card;

import java.time.LocalDate;

public abstract class Card {
    protected String number;
    protected LocalDate expirationDate;
    protected String ownerName;
    protected boolean isActive;
    protected String password;
    protected String clientCPF;
    protected String cardType;
    protected int cvv;

    public Card(String number, LocalDate expirationDate, String ownerName, String clientCPF, int cvv) {
        this.number = number;
        this.expirationDate = expirationDate;
        this.ownerName = ownerName;
        this.isActive = true; // Cartões são ativados por padrão.
        this.password = ""; // Senha será definida posteriormente.
        this.clientCPF = clientCPF;
        this.cvv = cvv;
    }

    public abstract boolean makePayment(double amount);

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    // Getters e setters
    public String getNumber() {
        return number;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getPassword() {
        return password;
    }

    public String getCardType() {
        return cardType;
    }

    public String getClientCPF() {
        return clientCPF;
    }

    public void setClientCPF(String clientCPF){
        this.clientCPF = clientCPF;
    }

    public int getCvv() {
        return cvv;
    }

}
