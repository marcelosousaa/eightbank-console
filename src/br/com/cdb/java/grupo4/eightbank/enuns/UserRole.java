package br.com.cdb.java.grupo4.eightbank.enuns;

public enum UserRole {
    CLIENT('C', "Client"),
    ADMINISTRATOR('A', "Administrador");

    private final char userRoleCode;
    private final String userRoleName;


    UserRole(char userRoleCode, String userRoleName) {
        this.userRoleCode = userRoleCode;
        this.userRoleName = userRoleName;
    }

    public char getUserRoleCode() {
        return userRoleCode;
    }

    public String getUserRoleName() {
        return userRoleName;
    }
}
