package br.com.cdb.java.grupo4.eightbank.enuns;

public enum SystemMessages {
    MANDATORY_FIELD_PT_BR("SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName()"),
    INVALID_ZIP_CODE("CEP inválido!");
    private final String fieldName;
    SystemMessages(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName(){
        return this.fieldName;
    }
}
