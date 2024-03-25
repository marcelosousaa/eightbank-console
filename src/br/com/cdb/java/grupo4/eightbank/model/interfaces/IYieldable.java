package br.com.cdb.java.grupo4.eightbank.model.interfaces;

public interface IYieldable {

    double calculateYields(double balance, double interestRate, int time);

    double calculateYields(int time);
}
