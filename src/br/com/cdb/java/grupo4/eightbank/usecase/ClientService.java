package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.enuns.SystemMessages;
import br.com.cdb.java.grupo4.eightbank.exceptions.AccountNotFoundException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientService {

    AccountService accountService = new AccountService();
    List<Account> clientAccountsList;
    UserService userService = new UserService();
    CardService cardService = new CardService();
    InsuranceService insuranceService = new InsuranceService();

    public void clientMenu(Client client) {
        boolean runningClientMenu = false;

        while (!runningClientMenu){
            System.out.println("Selecione uma opcao abaixo: "
                    + "\n 1 - Saldo"
                    + "\n 2 - Saque"
                    + "\n 3 - Depósito"
                    + "\n 4 - Transferencias"
                    + "\n 5 - Cartões"
                    + "\n 6 - Meu cadastro"
                    + "\n 0 - Sair"
            );

            int clientMenuOption = 0;

            try {
                clientMenuOption = new Scanner(System.in).nextInt();

                switch (clientMenuOption){
                    case 1:
                        try {
                            getClientBalance(client);
                        } catch (AccountNotFoundException e){
                            System.out.println(e.getMessage());
                        }
                    case 2:
                        try{
                            depositOnClientAccount(client);
                        } catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    case 0:
                        runningClientMenu = true;
                }

            } catch (InputMismatchException e){
                System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
            }
        }
    }

    private void depositOnClientAccount(Client client) throws AccountNotFoundException {
        clientAccountsList = accountService.findAccountsByCPF(client.getCpf());

        if(clientAccountsList.size() > 1){
            System.out.println("\nVimos aqui que você possui mais de uma conta conosco."
                    +"Por favor digite o numero da conta que deseja obter o saldo");

            System.out.println("Contas encontradas: ");
            for(Account account : clientAccountsList){
                System.out.println(" - " + account.getAccountNumber());
            }

            try{
                long accountNumber = new Scanner(System.in).nextLong();
                for(Account account : clientAccountsList){
                    if(account.getAccountNumber() == accountNumber){
                        System.out.println("Digite o valor que deseja depositar: ");
                        try{
                            double value = new Scanner(System.in).nextDouble();
                            accountService.deposit(accountNumber, value);
                        } catch (InputMismatchException e){
                            System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
                        }
                        break;
                    } else {
                        throw new AccountNotFoundException("Esta conta não está na lista.");
                    }
                }

            } catch (InputMismatchException e){
                System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
            }
        } else {
            System.out.println("Contas encontradas: ");
            System.out.println(" - " + clientAccountsList.get(0).getAccountNumber());

            System.out.println("Digite o valor que deseja depositar: ");
            try{
                double value = new Scanner(System.in).nextDouble();
                accountService.deposit(clientAccountsList.get(0).getAccountNumber(), value);
            } catch (InputMismatchException e){
                System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
            }
        }

    }

    private void getClientBalance(Client client) throws AccountNotFoundException {
        clientAccountsList = accountService.findAccountsByCPF(client.getCpf());

        if(clientAccountsList.size() > 1){

            System.out.println("\nVimos aqui que você possui mais de uma conta conosco."
                    +"Por favor digite o numero da conta que deseja obter o saldo");

            System.out.println("Contas encontradas: ");
            for(Account account : clientAccountsList){
                System.out.println(" - " + account.getAccountNumber());
            }

            try{
                long accountNumber = new Scanner(System.in).nextLong();
                for(Account account : clientAccountsList){
                    if(account.getAccountNumber() == accountNumber){
                        System.out.println(account.getBalance());
                        break;
                    } else {
                        throw new AccountNotFoundException("Esta conta não está na lista.");
                    }
                }

            } catch (InputMismatchException e){
                System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
            }
        } else {
            System.out.println("Contas encontradas: ");
            for(Account account : clientAccountsList){
                System.out.println("Numero da conta: " + account.getAccountNumber());
                System.out.println("Saldo: R$ " + account.getBalance());
            }
        }
    }
}
