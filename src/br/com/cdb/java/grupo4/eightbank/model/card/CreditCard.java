package br.com.cdb.java.grupo4.eightbank.model.card;

import java.time.LocalDate;

public class CreditCard extends Card {
    private double limit;
    private double currentSpend;

    // Construtor
    public CreditCard(String number, LocalDate expirationDate, int cvv, String ownerName, double limit) {
        super(number, expirationDate, cvv, ownerName);
        this.limit = limit;
        this.currentSpend = 0.0;
    }

    // Implementação do método abstrato makePayment da classe Card
    @Override
    public void makePayment(double amount) {
        if (!isValid()) {
            throw new IllegalStateException("Cartão inválido ou desativado.");
        }

        if (amount > (limit - currentSpend)) {
            throw new IllegalArgumentException("Limite insuficiente para realizar a transação.");
        }

        // Simula a realização do pagamento, atualizando o valor já gasto no cartão
        currentSpend += amount;
        System.out.println("Pagamento de R$ " + amount + " realizado com sucesso.");
    }

    // Implementação do método abstrato updateLimit da classe Card
    @Override
    public void updateLimit(double newLimit) {
        if (newLimit < currentSpend) {
            throw new IllegalArgumentException("O novo limite não pode ser inferior ao valor já utilizado.");
        }

        this.limit = newLimit;
        System.out.println("Limite do cartão atualizado para R$ " + newLimit);
    }

    // Método para zerar o saldo utilizado (pode ser chamado após o pagamento da fatura, por exemplo)
    public void resetCurrentSpend() {
        this.currentSpend = 0.0;
        System.out.println("Saldo utilizado no cartão de crédito foi zerado.");
    }

    // Getters adicionais específicos do cartão de crédito
    public double getLimit() {
        return limit;
    }

    public double getCurrentSpend() {
        return currentSpend;
    }
}
