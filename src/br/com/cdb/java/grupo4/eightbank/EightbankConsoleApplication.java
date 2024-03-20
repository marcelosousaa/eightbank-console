package br.com.cdb.java.grupo4.eightbank;

import br.com.cdb.java.grupo4.eightbank.enuns.UserRole;
import br.com.cdb.java.grupo4.eightbank.model.user.User;
import br.com.cdb.java.grupo4.eightbank.usecase.ClientService;
import br.com.cdb.java.grupo4.eightbank.usecase.UserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EightbankConsoleApplication {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {

        ClientService clientService = new ClientService();
        UserService userService = new UserService();
        User user = null;

        int initialOption = 0;
        while (true) {
            System.out.println("\n######### Bem vindo ao EightBank #########\n");
            System.out.println("\nSelecione uma opção abaixo:\n "
                    + "\n1 - Conheça nossos benefícios e cadastre-se!"
                    + "\n2 - Acesso à conta"
                    + "\n0 - Sair");
            try {
                initialOption = new Scanner(System.in).nextInt();

                if (initialOption < 0 || initialOption > 3) {
                    System.out.println("Opção inválida!");
                } else {
                    switch (initialOption){
                        case 1:
                            if(!clientService.clientRegistration()){
                                System.out.println("Houve um problema no cadastro. Vamos repetir?");
                            } else {
                                System.out.println("Cadastro realizado com sucesso!");
                            }
                            break;
                        case 2:
                            user = userService.login();
                            if(user.getUserRole().equals(UserRole.ADMINISTRATOR)){
                                //Acesso Admin
                            } else {
                                //Acesso Client
                            }
                            break;
                        case 3:
                            System.out.println("Encerrando...\n"
                            +"Obrigado por utilizar nosso sistema.");
                            System.exit(0);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Caracter inválido!");
            }
        }

    }
}
