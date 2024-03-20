package br.com.cdb.java.grupo4.eightbank.utils;

public class DateOfBirthValidator {
    public static boolean validateDateOfBirthFormat(String dob){
        String dobPattern = "\\d{1,2}\\/\\d{1,2}\\/\\d{4}";
        return dob.matches(dobPattern);
    }
}
