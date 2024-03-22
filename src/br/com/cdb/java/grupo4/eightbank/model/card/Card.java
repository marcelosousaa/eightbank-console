package br.com.cdb.java.grupo4.eightbank.model.card;

import java.time.LocalDate;
import br.com.cdb.java.grupo4.eightbank.model.user.User;
import br.com.cdb.java.grupo4.eightbank.enuns.CardStatus;

import static br.com.cdb.java.grupo4.eightbank.enuns.CardStatus.ACTIVE;

public abstract class Card {
    protected String number;
    protected String ownerName;
    protected User user;
    protected LocalDate expirationDate;
    protected int cvv;
    protected CardStatus status;
    protected boolean isActive;

    // Construtor
    public Card(String number, LocalDate expirationDate, int cvv, String ownerName) {
        this.number = number;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.status = ACTIVE; // Cartões são ativados por padrão ao serem criados
    }

    // Método para verificar a validade do cartão
    public boolean isValid() {
        return LocalDate.now().isBefore(this.expirationDate) && this.isActive;
    }

    // Métodos abstratos que serão implementados pelas subclasses
    public abstract void makePayment(double amount);
    public abstract void updateLimit(double newLimit);

    // Método para ativar ou desativar o cartão
    public void setActive(boolean active) {
        this.isActive = active;
    }

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
