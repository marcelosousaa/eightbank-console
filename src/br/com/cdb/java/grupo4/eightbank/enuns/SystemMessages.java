package br.com.cdb.java.grupo4.eightbank.enuns;

public enum SystemMessages {
    MANDATORY_FIELD_PT_BR("Campo obrigatório :)"),
    INVALID_ZIP_CODE("CEP inválido!"),
    INVALID_CARACTER("Caracter inválido!"),
    INVALID_OPTION("Opção inválida!"),
    INVALID_FORMAT("Formato inválido!");
    private final String fieldName;
    SystemMessages(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName(){
        return this.fieldName;
    }
}
