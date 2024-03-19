package br.com.cdb.java.grupo4.eightbank.enuns;

public enum AccountType {
    SAVINGS_ACCOUNT("Conta Poupança"),
    CURRENT_ACCOUNT("Conta Corrente");

    private final String accountTypeName;

    AccountType(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }
}
