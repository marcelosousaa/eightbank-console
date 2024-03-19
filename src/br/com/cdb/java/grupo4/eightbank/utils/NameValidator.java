package br.com.cdb.java.grupo4.eightbank.utils;

public class NameValidator {

    public static boolean validateName(String name){
        if(name.isEmpty()){
            return false;
        } else {
            return true;
        }
    }
}
