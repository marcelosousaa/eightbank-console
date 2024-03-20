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
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ClientService {
    AccountService accountService = new AccountService();
    ClientDAO clientDAO = new ClientDAO();
    AccountDAO accountDAO = new AccountDAO();
    String email;
    long cpf;
    String name;
    LocalDate localDate;
    private String streetName;
    private long number;
    private String district;
    private String city;
    private String state;
    private long zipCode;

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
            System.out.println("Digite o numero do seu CPF, sem pontos ou traços: ");
            try {
                cpf = scanner.nextLong();
                if (!CPFValidator.validateCPF(cpf)) {
                    System.out.println("CPF inválido!");
                    scanner.nextLine(); //Limpar scanner
                } else {
                    scanner.nextLine(); //Limpar scanner
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Caracter(es) inválido(s)!");
                scanner.nextLine(); //Limpar scanner
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
            if (!DateOfBirthValidator.validateDateOfBirthFormat(dob)) {
                System.out.println("Formato inválido!");
                scanner.nextLine(); //Limpar scanner
            } else {
                String[] fields = dob.split("/");
                int day = Integer.parseInt(fields[0]);
                int month = Integer.parseInt(fields[1]);
                int year = Integer.parseInt(fields[2]);

                if (year > LocalDate.now().getYear()) {
                    System.out.println("Ano inválido!");
                } else if (year == LocalDate.now().getYear() - 18) {
                    if (month <= LocalDate.now().getMonthValue()) {
                        if (day <= LocalDate.now().getDayOfMonth()) {
                            try {
                                localDate = LocalDate.of(year, month, day);
                                break;
                            } catch (Exception e) {
                                System.out.println("Data inválida!");
                                //System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println("Cadastro permitido somente para maiores de 18 anos.");
                        }
                    } else {
                        System.out.println("Cadastro permitido somente para maiores de 18 anos.");
                    }
                } else {
                    try {
                        localDate = LocalDate.of(year, month, day);
                        break;
                    } catch (Exception e) {
                        System.out.println("Data inválida!");
                        //System.out.println(e.getMessage());
                    }
                }
            }
        }

        while (true) {
            System.out.println("Certo! Agora vamos cadastrar seu endereço.\n"
                    + "Por favor digite o nome da rua: ");
            streetName = scanner.nextLine();
            if (streetName.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
                scanner.nextLine();
            } else {
                break;
            }
        }
        while (true) {
            System.out.println("Digite o número do endereço: ");
            try {
                number = scanner.nextLong();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Formato inválido!");
                scanner.nextLine();
            }
        }

        while (true) {
            System.out.println("Certo, e qual o seu bairro: ");
            district = scanner.nextLine();
            if (district.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR);
                scanner.nextLine();
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Beleza! E a cidade: ");
            city = scanner.nextLine();
            if (city.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
                scanner.nextLine();
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Digita pra gente o seu estado, por favor: ");
            state = scanner.nextLine();
            if (state.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
                scanner.nextLine();
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("OK! Quase finalizando... Agora informa seu CEP, mas somente os números, sem hífen: ");
            String zipCodeString = scanner.nextLine();
            if (!ZipCodeValidator.validateZipCode(zipCodeString)) {
                System.out.println(SystemMessages.INVALID_ZIP_CODE.getFieldName());
                scanner.nextLine();
            } else {
                String[] fields = zipCodeString.split("-");
                long zipCodeBeforeHifen = Long.parseLong(fields[0]);
                long zipCodeAfterHifen = Long.parseLong(fields[1]);
                zipCode = zipCodeBeforeHifen + zipCodeAfterHifen;
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
