package br.com.cdb.java.grupo4.eightbank;

import br.com.cdb.java.grupo4.eightbank.model.client.Client;
import br.com.cdb.java.grupo4.eightbank.usecase.ClientService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EightbankConsoleApplication {
    public static void main(String[] args) throws Exception {

        Client client;
        ClientService clientService = new ClientService();

        clientService.importClientsFromFile("clients.csv");

        while (true) {
            int menuOption;
            System.out.println(
                    """

                            ######### Bem-vindo ao EightBank #########

                            Selecione uma opção abaixo:
                            \s
                            1 - Cadastre-se
                            2 - Acesso à conta
                            0 - Sair"""
            );
            try {

                menuOption = new Scanner(System.in).nextInt();
                if (menuOption < 0 || menuOption > 2) {
                    System.out.println("Opção inválida!");
                } else {
                    switch (menuOption) {
                        case 1:
                            boolean runningLoginMenu = false;

                            while (!runningLoginMenu) {
                                if (clientService.clientRegistration()) {
                                    System.out.println("Cadastro realizado com sucesso!");
                                    runningLoginMenu = true;
                                } else {
                                    System.out.println("Houve um problema no cadastro. Vamos repetir?(S/N)");
                                    try {
                                        char option = clientService.validateClientOptionYesOrNo();
                                        if (option == 'N' || option == 'n') {
                                            System.out.println("Retornando ao menu...\n");
                                            runningLoginMenu = true;
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Caracter inválido!");
                                    }
                                }
                            }
                            break;
                        case 2:
                            client = clientService.login();
                            if (client != null) {
                                clientService.clientMenu(client);
                            }
                            break;
                        case 0:
                            System.out.println("Encerrando...\n"
                                    + "Obrigado por utilizar nosso sistema.");
                            System.exit(0);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Caracter inválido!"
                        + " Retornando ao menu... \n");
            }
        }
    }
}
