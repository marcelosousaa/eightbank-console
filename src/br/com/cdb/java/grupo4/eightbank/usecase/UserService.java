package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.AccountDAO;
import br.com.cdb.java.grupo4.eightbank.dao.UserDAO;
import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.enuns.ClientCategory;
import br.com.cdb.java.grupo4.eightbank.enuns.SystemMessages;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.User;
import br.com.cdb.java.grupo4.eightbank.model.user.admin.Administrator;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Address;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;
import br.com.cdb.java.grupo4.eightbank.utils.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserService {
    User user = null;
    UserDAO userDAO = new UserDAO();
    String email = null;
    String passwordString = null;
    AccountService accountService = new AccountService();
    String cpf;
    String name;
    LocalDate localDate;
    String streetName;
    long number;
    String district;
    String city;
    String state;
    String zipCode;
    AccountType accountType;
    double grossMonthlyIncome;
    String phoneNumber;

    ClientCategory clientCategory;
    List<Account> clientAccountsList;

    public boolean adminRegistration() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String passwordString = "senha";
        String strongPassword = PasswordService.generateStrongPassword(passwordString);

        Administrator administrator = new Administrator("admin@teste.com", strongPassword, "Administrador");

        userDAO.addUser(administrator);

        return true;
    }

    public boolean clientRegistration() throws NoSuchAlgorithmException, InvalidKeySpecException {

        Client client;
        LocalDate localDate;

        while (true) {
            System.out.println("Digite o numero do seu CPF, sem pontos ou traços: ");
            try {
                cpf = new Scanner(System.in).nextLine();
                if (!CPFValidator.validateCPF(cpf)) {
                    System.out.println("CPF inválido!");
                } else if(userDAO.searchClientByCPF(cpf)){
                    System.err.println("Usuário já existente na base de dados!");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Caracter(es) inválido(s)!");
            }
        }

        while (true) {
            System.out.println("Informe sua renda mensal bruta: ");
            try {
                grossMonthlyIncome = new Scanner(System.in).nextDouble();
                clientCategory = checkClientCategory(grossMonthlyIncome);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Formato inválido!");
            }
        }

        while (true) {
            System.out.println("Certo, vamos começar o cadastro pelo seu email\n"
                    + "Digita ele pra gente aqui em baixo: ");
            email = new Scanner(System.in).nextLine();
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
            System.out.println("Digite seu nome: ");
            name = new Scanner(System.in).nextLine();
            if (name.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Digite sua data de nascimento, no formato(dd/mm/aaaa):");
            String dob = new Scanner(System.in).nextLine();
            if (!DateOfBirthValidator.validateDateOfBirth(dob)) {
                System.out.println("Formato inválido!");
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
            streetName = new Scanner(System.in).nextLine();
            if (streetName.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                break;
            }
        }
        while (true) {
            System.out.println("Digite o número do endereço: ");
            try {
                number = new Scanner(System.in).nextLong();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Formato inválido!");
            }
        }

        while (true) {
            System.out.println("Certo, e qual o seu bairro: ");
            district = new Scanner(System.in).nextLine();
            if (district.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Beleza! E a cidade: ");
            city = new Scanner(System.in).nextLine();
            if (city.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Digita pra gente o seu estado, por favor: ");
            state = new Scanner(System.in).nextLine();
            if (state.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("OK! Quase finalizando... Agora informa seu CEP, com hífen: ");
            String zipCodeString = new Scanner(System.in).nextLine();
            if (!ZipCodeValidator.validateZipCode(zipCodeString)) {
                System.out.println(SystemMessages.INVALID_ZIP_CODE.getFieldName());
            } else {
                String cleanZipCode = zipCodeString.replace("-", "");
                zipCode = cleanZipCode;
                break;
            }
        }

        while(true){
            System.out.println("Informa um telefone pra contato: ");
            phoneNumber = new Scanner(System.in).nextLine();
            if(!PhoneNumberValidator.validatePhoneNumber(phoneNumber)){
                System.out.println(SystemMessages.INVALID_FORMAT.getFieldName());
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Certo, para finalizar vamos definir uma senha de acesso: ");
            passwordString = new Scanner(System.in).nextLine();
            if (passwordString.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                passwordString = PasswordService.generateStrongPassword(passwordString);
                break;
            }
        }

        Address address = new Address(streetName, number, district, city, state, zipCode);

        client = new Client(
                grossMonthlyIncome,
                email,
                passwordString,
                name,
                clientCategory,
                cpf,
                localDate,
                address,
                phoneNumber
        );

        //Adiciona usuario ao "banco de dados" de clientes
        userDAO.addUser(client);

        // Cria a(s) conta(s) do cliente e devolve para um ArrayList
        clientAccountsList = clientAccountsRegistration(client);

        //Itera no array retornado e seta o Cliente como owner das contas
        for(Account account : clientAccountsList){
            accountService.setAccountOwner(account, client);
        }

        // Lista os usuários cadastrados, à partir do método toString
        userDAO.listUsers();

        //Listar contas e titulares
        accountService.listAccounts();

        return true;
    }

    private List<Account> clientAccountsRegistration(Client client){
        Account account;
        List<Account> accountList = new ArrayList<>();
        double annualPercentageYield = checkAnnualPercentageYield(client);
        double currentAccountMonthlyFee = checkCurrentAccountMonthlyFee(client);

        while (true){
            System.out.println("Que tipo de conta você deseja abrir: ");
            int iterator = 1;
            for(AccountType type : AccountType.values()){
                System.out.println(iterator + " - " + type.getAccountTypeName());
                iterator++;
            }
            System.out.println((iterator++) + " - Ambas");
            try{
                int accountTypeOption = new Scanner(System.in).nextInt();
                if(accountTypeOption < 1 || accountTypeOption > 3){
                    System.out.println(SystemMessages.INVALID_OPTION.getFieldName());
                } else if(accountTypeOption == 1){
                    account = accountService.createSavingsAccount(client, annualPercentageYield);
                    accountList.add(account);
                } else if(accountTypeOption == 2){
                    account = accountService.createCurrentAccount(client, currentAccountMonthlyFee);
                    accountList.add(account);
                } else {
                    account = accountService.createSavingsAccount(client, annualPercentageYield);
                    accountList.add(account);
                    account = accountService.createCurrentAccount(client, currentAccountMonthlyFee);
                    accountList.add(account);
                }
                break;
            } catch (InputMismatchException e){
                System.out.println(SystemMessages.INVALID_CARACTER.getFieldName());
            }
        }
        return accountList;
    }

    private double checkCurrentAccountMonthlyFee(Client client) {
        double currentAccountMonthlyFee;

        if(client.getClientCategory().equals(ClientCategory.COMMOM)){
            currentAccountMonthlyFee = 12;
        } else if(client.getClientCategory().equals(ClientCategory.SUPER)){
            currentAccountMonthlyFee = 8;
        } else{
            currentAccountMonthlyFee = 0;
        }
        return currentAccountMonthlyFee;
    }

    private double checkAnnualPercentageYield(Client client){
        double annualPercentageYield;

        if(client.getClientCategory().equals(ClientCategory.COMMOM)){
            annualPercentageYield = 0.05;
        } else if(client.getClientCategory().equals(ClientCategory.SUPER)){
            annualPercentageYield = 0.07;
        } else{
            annualPercentageYield = 0.09;
        }

        return annualPercentageYield;
    }

    private ClientCategory checkClientCategory(double grossMonthlyIncome) {
        ClientCategory clientCategory;

        if (grossMonthlyIncome <= 5000) {
            clientCategory = ClientCategory.COMMOM;
        } else if(grossMonthlyIncome > 5000 && grossMonthlyIncome <= 15000){
            clientCategory = ClientCategory.SUPER;
        } else {
            clientCategory = ClientCategory.PREMIUM;
        }
        return clientCategory;
    }


    public User login() {
        while (user == null) {
            while (true) {
                System.out.println("Digite seu email: ");
                email = new Scanner(System.in).nextLine();
                if (email.isEmpty()) {
                    System.err.println("Campo obrigatório!");
                } else if (!EmailValidator.validateEmail(email)) {
                    System.err.println("Formato inválido!");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.println("Digite sua senha: ");
                passwordString = new Scanner(System.in).nextLine();
                if (passwordString.isEmpty()) {
                    System.err.println("Campo obrigatório!");
                } else {
                    break;
                }
            }

            try {
                user = userDAO.searchUserByEmail(email);
                System.out.println(user);
                try {
                    PasswordService.validatePassword(passwordString, user.getPassword());
                } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                    System.out.println("Senha inválida!");
                    user = null;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (user != null) {
                System.out.println("\nLogin realizado com sucesso!\n" + "Bem-vindo " + user.getName() + "!");
            } else {
                System.out.println(
                        "\nNao foi possivel realizar o login, verifique seus dados e tente novamente.\n");
            }
        }
        return user;
    }
}