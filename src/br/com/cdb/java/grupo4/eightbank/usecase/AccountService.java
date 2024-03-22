package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.AccountDAO;
import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.exceptions.InsufficientFundsException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InvalidValueException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.User;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class AccountService {
    AccountDAO accountDAO = new AccountDAO();
    private Scanner scanner = new Scanner(System.in);

    public void information(){

        while (true) {
            System.out.println("\n→ Informações da conta: ");
            System.out.println("[1] Transferência");
            System.out.println("[2] Ver saldo");
            System.out.println("[3] Deposito");
            System.out.println("[4] Tipo de conta");
            System.out.println("[5] Meus Cartões");
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
                case 3:
                    //
                    break;
                case 4:
                    //
                    break;
                case 5:
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

    public void editAccount() {
        User user = null;

        while (true) {
            System.out.println("\n→ Editar perfil: ");
            System.out.println("[1] Editar Usuário");
            System.out.println("[2] Editar Endereço");
            System.out.println("[0] Voltar");
            System.out.print("Sua escolha: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Digite sua senha");
                    String passwordString = scanner.nextLine();
                    if (passwordString.isEmpty()){
                        System.out.println("Campo obrigatório");
                        continue;
                    }
                    try {
                        if (PasswordService.validatePassword(passwordString, user.getPassword())) {
                            System.out.println("\nSenha confirmada com sucesso!\n");
                            //(user);
                            break;
                        }
                    }catch (InvalidKeySpecException | NoSuchAlgorithmException e){
                        System.out.println("Erro ao validar a senha.");
                    }
                    break;
                case 2:

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

    public Account createAccount(AccountType accountType) {
        Account account = new Account(accountType);
        accountDAO.addAccount(account);
        return account;
    }

    public void checkBalance(Account account){
        System.out.println(account.getBalance());
    }

    public void withdraw(Account account, double value) throws InsufficientFundsException, InvalidValueException {
        if (value >= 0) {
            if (account.getBalance() >= value) {
                account.setBalance(account.getBalance() - value);
            } else {
                throw new InsufficientFundsException("Saldo Insuficiente!");
            }
        } else {
            throw new InvalidValueException("Valor inválido!");
        }
    }

    public void deposit(Account account, double value) throws InvalidValueException {
        if (value > 0) {
            account.setBalance(account.getBalance() + value);
            System.out.println("Saldo atual - R$ " + account.getBalance());
        } else {
            throw new InvalidValueException("Valor inválido!");
        }
    }

    public void transfer(Account originAccount, Account targetAccount, double value){
        try {
            withdraw(originAccount, value);
            try{
                deposit(targetAccount, value);
            } catch (InvalidValueException e){
                e.getMessage();
            }
        } catch (InsufficientFundsException | InvalidValueException e) {
            e.getMessage();
        }
    }
}
