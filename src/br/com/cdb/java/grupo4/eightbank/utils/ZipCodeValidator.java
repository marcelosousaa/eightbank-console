package br.com.cdb.java.grupo4.eightbank.utils;

public class ZipCodeValidator {

    public static boolean validateZipCode(String zipCode) {
        String zipCodePattern = "\\d{5}-\\d{3}";
        return zipCode.matches(zipCodePattern);
    }
}
