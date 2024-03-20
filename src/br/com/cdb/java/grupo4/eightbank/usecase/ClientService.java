package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.AccountDAO;
import br.com.cdb.java.grupo4.eightbank.dao.ClientDAO;
import br.com.cdb.java.grupo4.eightbank.enuns.ClientCategory;
import br.com.cdb.java.grupo4.eightbank.enuns.SystemMessages;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Address;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;
import br.com.cdb.java.grupo4.eightbank.utils.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ClientService {
    AccountService accountService = new AccountService();
    ClientDAO clientDAO = new ClientDAO();
    AccountDAO accountDAO = new AccountDAO();
    String email;
    String cpf;
    String name;
    LocalDate localDate;
    private String streetName;
    private long number;
    private String district;
    private String city;
    private String state;
    private String zipCode;

    private String passwordString;

    public boolean clientRegistration() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Scanner scanner = new Scanner(System.in);
        Client client = null;
        LocalDate localDate;

        while (true) {
            System.out.println("Certo, vamos começar o cadastro pelo seu email\n"
                    + "Digita ele pra gente aqui em baixo: ");
            email = scanner.nextLine();
            if (email.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                if (!EmailValidator.validateEmail(email)) {
                    System.err.println("Parece que seu e-mail está em um formato inválido...");
                } else {
                    break;
                }
            }
        }

        while (true) {
            System.out.println("Digite o seu CPF:");
            cpf = scanner.nextLine();

            if (cpf.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                if (!CPFValidator.validateCPF(cpf)) {
                    System.out.println("Parece que o seu CPF está em um formato inválido...");
                } else {
                    break;
                }
            }
        }

        while (true) {
            System.out.println("Digite seu nome: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
                scanner.nextLine(); //Limpar scanner
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Digite sua data de nascimento, no formato(dd/mm/aaaa):");
            String dob = scanner.nextLine();
            if (!DateOfBirthValidator.validateDateOfBirth(dob)) {
                System.out.println("Formato da data inválido!");
            } else if (!DateOfBirthValidator.isOfLegalAge(dob)) {
                System.out.println("Você precisa ter pelo menos 18 anos para se registrar.");
                return false;
            } else {
                // Data de nascimento válida e usuário é maior de idade
                localDate = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            }
        }

        while (true) {
            System.out.println("Certo! Agora vamos cadastrar seu endereço.\n"
                    + "Por favor digite o nome da rua: ");
            streetName = scanner.nextLine();
            if (streetName.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Digite o número do endereço: ");
            if (!scanner.hasNextLong()) {
                System.out.println("Formato inválido.");
                scanner.next();
                continue;
            }
            number = scanner.nextLong();
            scanner.nextLine();
            break;
        }

        while (true) {
            System.out.println("Certo, e qual o seu bairro: ");
            district = scanner.nextLine();
            if (district.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR);
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Beleza! E a cidade: ");
            city = scanner.nextLine();
            if (city.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Digita pra gente o seu estado, por favor: ");
            state = scanner.nextLine();
            if (state.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("OK! Quase finalizando... Agora informa seu CEP: ");
            String zipCodeString = scanner.nextLine().trim();
            if (!ZipCodeValidator.validateZipCode(zipCodeString)) {
                System.out.println(SystemMessages.INVALID_ZIP_CODE.getFieldName());
            } else {
                zipCode = zipCodeString.replace("-", "");
                break;
            }
        }

        while(true){
            System.out.println("Certo, para finalizar vamos definir uma senha de acesso: ");
            passwordString = scanner.nextLine();
            if(passwordString.isEmpty()){
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
                scanner.nextLine();
            } else {
                passwordString = PasswordService.generateStrongPassword(passwordString);
                break;
            }
        }
        scanner.close();

        Address address = new Address(streetName, number, district, city, state, zipCode);
        Account account = accountService.createAccount();

        client = new Client(email, passwordString, name, ClientCategory.COMMOM, cpf, localDate, address, account);

        accountDAO.setAccountOwner(account.getAccountNumber(), client);

        return true;

    }

    public boolean addClient(Client client) {

        clientDAO.addClient(client);

        return true;
    }

}
