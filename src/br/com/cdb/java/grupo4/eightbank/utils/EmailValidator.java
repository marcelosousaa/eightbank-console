package br.com.cdb.java.grupo4.eightbank.utils;

public class EmailValidator {
    public static boolean validateEmail(String email){
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }
}
