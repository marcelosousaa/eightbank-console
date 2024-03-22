package br.com.cdb.java.grupo4.eightbank.usecase;

import java.util.Scanner;

public class InsuranceService {
    private Scanner scanner = new Scanner(System.in);

    public void insurance() {
        while (true) {
            System.out.println("\n→ Ativação/Desativação seguros: ");
            System.out.println("[1] Seguro Viagem");
            System.out.println("[2] Seguro de Fraude");
            System.out.println("[0] Voltar");
            System.out.print("Sua escolha: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    //
                    break;
                case 2:
                    //
                    break;
                case 0:
                    System.out.println("Voltando...");
                    return; // Retorna ao menu anterior
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        }
    }
}
