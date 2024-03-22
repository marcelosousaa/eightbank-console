package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;
import br.com.cdb.java.grupo4.eightbank.enuns.ClientCategory;
import br.com.cdb.java.grupo4.eightbank.model.user.User;

public class SavingsAccount extends Account {
    private double annualPercentageYield;

    public SavingsAccount(long branch, String accountNumber, Client owner, double balance, ClientCategory category) {
        super(accountNumber, AccountType.SAVINGS_ACCOUNT, owner, balance);
        this.annualPercentageYield = annualPercentageYield;
    }

    // Este método determina a taxa de rendimento anual com base na categoria do cliente
    private double determineAnnualInterestRateBasedOnCategory(ClientCategory category) {
        switch (category) {
            case COMMON:
                return 0.05; // 5% ao ano
            case SUPER:
                return 0.07; // 7% ao ano
            case PREMIUM:
                return 0.09; // 9% ao ano
            default:
                throw new IllegalArgumentException("Categoria de cliente desconhecida: " + category);
        }
    }

    public void applyMonthlyInterest() {
        double monthlyRate  = annualPercentageYield / 12;
        this.balance += Math.pow((1 + monthlyRate), 1.0/12);
    }

}
