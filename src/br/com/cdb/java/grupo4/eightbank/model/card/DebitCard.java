package br.com.cdb.java.grupo4.eightbank.model.card;

import java.time.LocalDate;

public class DebitCard extends Card {
    private double dailyLimit;
    private double dailySpend;

    // Construtor
    public DebitCard(String number, LocalDate expirationDate, int cvv, String ownerName, double dailyLimit) {
        super(number, expirationDate, cvv, ownerName);
        this.dailyLimit = dailyLimit;
        this.dailySpend = 0.0;
    }

    // Implementação do método abstrato makePayment da classe Card
    @Override
    public void makePayment(double amount) {
        if (!isValid()) {
            throw new IllegalStateException("Cartão inválido ou desativado.");
        }

        if (amount > (dailyLimit - dailySpend)) {
            throw new IllegalArgumentException("Limite diário insuficiente para realizar a transação.");
        }

        // Simula a realização do pagamento, atualizando o gasto diário
        dailySpend += amount;
        System.out.println("Pagamento de R$ " + amount + " realizado com sucesso.");
    }

    // Implementação do método abstrato updateLimit da classe Card
    @Override
    public void updateLimit(double newDailyLimit) {
        this.dailyLimit = newDailyLimit;
        System.out.println("Limite diário do cartão de débito atualizado para R$ " + newDailyLimit);
    }

    // Método para resetar o gasto diário (a ser chamado no início de cada dia)
    public void resetDailySpend() {
        this.dailySpend = 0.0;
        System.out.println("Gasto diário no cartão de débito foi zerado.");
    }

    // Getters e Setters específicos
    public double getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(double dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public double getDailySpend() {
        return dailySpend;
    }
}
