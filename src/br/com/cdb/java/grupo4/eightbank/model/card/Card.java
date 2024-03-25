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

    // Método para alterar a senha do CVV (Considerando que o CVV pode ser alterado como uma forma de "senha")
    public void changeCVV(int newCVV) {
        this.cvv = newCVV;
    }

    // Getters
    public String getNumber() {
        return number;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getCvv() {
        return cvv;
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

    // Setters para propriedades que podem necessitar de atualização
    public void setNumber(String number) {
        this.number = number;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
