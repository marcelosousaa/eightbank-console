package br.com.cdb.java.grupo4.eightbank.utils;

public class FileNameValidator {

    public static boolean validateFileName(String fileName) {
        String fileNamePattern = ".+\\.csv";
        return fileName.matches(fileNamePattern);
    }
}
