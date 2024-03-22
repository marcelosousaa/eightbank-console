package br.com.cdb.java.grupo4.eightbank.model.account;

import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;
import br.com.cdb.java.grupo4.eightbank.enuns.ClientCategory;

public class CurrentAccount extends Account{
    private double accountFee;

    public CurrentAccount(String accountNumber, Client owner, double balance, ClientCategory category) {
        super(accountNumber, AccountType. CURRENT_ACCOUNT, owner, balance);
        this.accountFee = determineMonthlyFeeBasedOnCategory(category);
    }

    private double determineMonthlyFeeBasedOnCategory(ClientCategory category) {
        switch (category) {
            case COMMON:
                return 12.00;
            case SUPER:
                return 8.00;
            case PREMIUM:
                return 0.00; // Isento de taxa para clientes Premium
            default:
                throw new IllegalArgumentException("Categoria de cliente desconhecida: " + category);
        }
    }

    public void accountFee() {
        this.balance -= accountFee;
    }

}
