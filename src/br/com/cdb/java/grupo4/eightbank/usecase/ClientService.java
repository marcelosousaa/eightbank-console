package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.ClientDAO;
import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.enuns.AnsiColors;
import br.com.cdb.java.grupo4.eightbank.enuns.ClientCategory;
import br.com.cdb.java.grupo4.eightbank.enuns.SystemMessages;
import br.com.cdb.java.grupo4.eightbank.exceptions.AccountNotFoundException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InsufficientFundsException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InvalidValueException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.client.Address;
import br.com.cdb.java.grupo4.eightbank.model.client.Client;
import br.com.cdb.java.grupo4.eightbank.utils.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientService {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    List<Account> clientAccountsList;
    CardService cardService = new CardService();
    InsuranceService insuranceService = new InsuranceService();
    ClientDAO clientDAO = new ClientDAO();
    AccountService accountService = new AccountService();
    ClientCategory clientCategory;
    Client client;

    public boolean clientRegistration()
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidValueException {

        String cpf = inputCPF();
        double grossMonthlyIncome = inputGrossMonthlyIncome();
        String email = inputEmail();
        String name = inputName();
        LocalDate dateOfBirth = inputDateOfBirth();
        Address address = inputAddress();
        String phoneNumber = inputPhoneNumber();
        String passwordString = inputPassword();

        client = new Client(
                email,
                passwordString,
                name,
                cpf,
                dateOfBirth,
                address,
                clientCategory,
                phoneNumber,
                grossMonthlyIncome
        );

        //Adiciona usuario ao "banco de dados" de clientes
        clientDAO.addClient(client);

        registerClientAccounts(client);

        // Lista os usuários cadastrados, à partir do método toString
        clientDAO.listClients();

        //Listar contas e titulares
        accountService.listAccounts();

        return true;
    }

    private void registerClientAccounts(Client client) {
        // Cria a(s) conta(s) do cliente e devolve para um ArrayList
        List<Account> clientAccountsList = clientAccountsRegistration(client);

        //Itera no array retornado e seta o Cliente como owner das contas
        for (Account account : clientAccountsList) {
            accountService.setAccountOwner(account, client.getCpf());
        }
    }

    private void registerClientAccounts(Client client, int accountsTypeOtion) {
        // Cria a(s) conta(s) do cliente e devolve para um ArrayList
        List<Account> clientAccountsList = clientAccountsRegistration(client, accountsTypeOtion);

        //Itera no array retornado e seta o Cliente como owner das contas
        for (Account account : clientAccountsList) {
            accountService.setAccountOwner(account, client.getCpf());
        }
    }

    private String inputPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String passwordString;

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

        return passwordString;
    }

    private String inputPhoneNumber() {
        String phoneNumber;

        while (true) {
            System.out.println("Informa um telefone pra contato, no formato (XX) XXXX(X)-XXXX, por favor:  ");
            phoneNumber = new Scanner(System.in).nextLine();
            if (!PhoneNumberValidator.validatePhoneNumber(phoneNumber)) {
                System.out.println(SystemMessages.INVALID_FORMAT.getFieldName());
            } else {
                break;
            }
        }

        return phoneNumber;
    }

    private Address inputAddress() {
        System.out.println("Certo! Agora vamos cadastrar seu endereço.");

        Address address;
        String streetName;
        long number;
        String district;
        String city;
        String state;
        String zipCode;

        while (true) {
            System.out.println("Por favor digite o nome da rua: ");
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
                zipCodeString = zipCodeString.replace("-", "");
                zipCode = zipCodeString;
                break;
            }
        }

        return new Address(streetName, number, district, city, state, zipCode);
    }

    private LocalDate inputDateOfBirth() {
        LocalDate dateOfBirth;
        String dob;

        while (true) {
            System.out.println("Digite sua data de nascimento, no formato(dd/mm/aaaa):");
            dob = new Scanner(System.in).nextLine();
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
                                dateOfBirth = LocalDate.of(year, month, day);
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
                        dateOfBirth = LocalDate.of(year, month, day);
                        break;
                    } catch (Exception e) {
                        System.out.println("Data inválida!");
                        //System.out.println(e.getMessage());
                    }
                }
            }
        }
        return dateOfBirth;
    }

    private String inputName() {
        String name;
        while (true) {
            System.out.println("Digite seu nome: ");
            name = new Scanner(System.in).nextLine();
            if (name.isEmpty()) {
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
            } else {
                break;
            }
        }
        return name;
    }

    private String inputEmail() {
        String email;

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

        return email;
    }

    private double inputGrossMonthlyIncome() throws InvalidValueException {
        double grossMonthlyIncome;

        while (true) {
            System.out.println("Informe sua renda mensal bruta: ");
            try {
                grossMonthlyIncome = new Scanner(System.in).nextDouble();
                if (grossMonthlyIncome <= 0) {
                    throw new InvalidValueException("Valor inválido!");
                } else {
                    clientCategory = checkClientCategory(grossMonthlyIncome);
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Formato inválido!");
            }
        }

        return grossMonthlyIncome;
    }

    private String inputCPF() {
        String cpf;

        while (true) {
            System.out.println("Digite o numero do seu CPF, sem pontos ou traços: ");
            try {
                cpf = new Scanner(System.in).nextLine();
                if (!CPFValidator.validateCPF(cpf)) {
                    System.out.println("CPF inválido!");
                } else if (clientDAO.searchClientByCPF(cpf)) {
                    System.err.println("Usuário já existente na base de dados!");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Caracter(es) inválido(s)!");
            }
        }

        return cpf;
    }

    private List<Account> clientAccountsRegistration(Client client) {
        Account account;
        List<Account> accountList = new ArrayList<>();
        double annualPercentageYield = checkAnnualPercentageYield(client);
        double currentAccountMonthlyFee = checkCurrentAccountMonthlyFee(client);

        while (true) {
            System.out.println("Que tipo de conta você deseja abrir: ");
            int iterator = 1;
            for (AccountType type : AccountType.values()) {
                System.out.println(iterator + " - " + type.getAccountTypeName());
                iterator++;
            }
            System.out.println((iterator++) + " - Ambas");
            try {
                int accountTypeOption = new Scanner(System.in).nextInt();
                if (accountTypeOption < 1 || accountTypeOption > 3) {
                    System.out.println(SystemMessages.INVALID_OPTION.getFieldName());
                } else if (accountTypeOption == 1) {
                    account = accountService.createSavingsAccount(client.getCpf(), annualPercentageYield);
                    accountList.add(account);
                } else if (accountTypeOption == 2) {
                    account = accountService.createCurrentAccount(client.getCpf(), currentAccountMonthlyFee);
                    accountList.add(account);
                } else {
                    account = accountService.createSavingsAccount(client.getCpf(), annualPercentageYield);
                    accountList.add(account);
                    account = accountService.createCurrentAccount(client.getCpf(), currentAccountMonthlyFee);
                    accountList.add(account);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(SystemMessages.INVALID_CHARACTER.getFieldName());
            }
        }
        return accountList;
    }

    private List<Account> clientAccountsRegistration(Client client, int accountsType) {
        Account account;
        List<Account> accountList = new ArrayList<>();
        double annualPercentageYield = checkAnnualPercentageYield(client);
        double currentAccountMonthlyFee = checkCurrentAccountMonthlyFee(client);

        while (true) {
            try {
                if (accountsType < 1 || accountsType > 3) {
                    System.out.println(SystemMessages.INVALID_OPTION.getFieldName());
                } else if (accountsType == 1) {
                    account = accountService.createSavingsAccount(client.getCpf(), annualPercentageYield);
                    accountList.add(account);
                } else if (accountsType == 2) {
                    account = accountService.createCurrentAccount(client.getCpf(), currentAccountMonthlyFee);
                    accountList.add(account);
                } else {
                    account = accountService.createSavingsAccount(client.getCpf(), annualPercentageYield);
                    accountList.add(account);
                    account = accountService.createCurrentAccount(client.getCpf(), currentAccountMonthlyFee);
                    accountList.add(account);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(SystemMessages.INVALID_CHARACTER.getFieldName());
            }
        }
        return accountList;
    }

    private double checkCurrentAccountMonthlyFee(Client client) {
        double currentAccountMonthlyFee;

        if (client.getClientCategory().equals(ClientCategory.COMMOM)) {
            currentAccountMonthlyFee = 12;
        } else if (client.getClientCategory().equals(ClientCategory.SUPER)) {
            currentAccountMonthlyFee = 8;
        } else {
            currentAccountMonthlyFee = 0;
        }
        return currentAccountMonthlyFee;
    }

    private double checkAnnualPercentageYield(Client client) {
        double annualPercentageYield;

        if (client.getClientCategory().equals(ClientCategory.COMMOM)) {
            annualPercentageYield = 0.05;
        } else if (client.getClientCategory().equals(ClientCategory.SUPER)) {
            annualPercentageYield = 0.07;
        } else {
            annualPercentageYield = 0.09;
        }

        return annualPercentageYield;
    }

    private ClientCategory checkClientCategory(double grossMonthlyIncome) {
        ClientCategory clientCategory;

        if (grossMonthlyIncome <= 5000) {
            clientCategory = ClientCategory.COMMOM;
        } else if (grossMonthlyIncome > 5000 && grossMonthlyIncome <= 15000) {
            clientCategory = ClientCategory.SUPER;
        } else {
            clientCategory = ClientCategory.PREMIUM;
        }
        return clientCategory;
    }

    public Client login() {
        Client client = null;
        String email;
        String passwordString;

        while (client == null) {
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
                client = clientDAO.searchClientByEmail(email);
                try {
                    if (!PasswordService.validatePassword(passwordString, client.getPassword())) {
                        client = null;
                    }
                } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                    client = null;
                }
            } catch (Exception e) {
                client = null;
            }

            if (client != null) {
                System.out.println("\nLogin realizado com sucesso!\n\n" + "Bem-vindo, " + client.getName() + "!");
            } else {
                System.out.println(
                        "\nNao foi possivel realizar o login, verifique seus dados e tente novamente.\n");
            }
        }
        return client;
    }

    private int validateClientOptionNumber() {
        int clientOption;

        while (true) {
            try {
                clientOption = new Scanner(System.in).nextInt();
                return clientOption;
            } catch (InputMismatchException e) {
                System.out.println(SystemMessages.INVALID_CHARACTER.getFieldName());
            }
        }
    }

    public void clientMenu(Client client) {
        boolean runningClientMenu = false;

        while (!runningClientMenu) {
            System.out.println(
                    "Selecione uma opção abaixo: "
                            + "\n 1 - Saldo"
                            + "\n 2 - Depósito"
                            + "\n 3 - Saque"
                            + "\n 4 - Transferencias"
                            + "\n 5 - Cartões" // SUB-MENU SEGUROS
                            + "\n 6 - Meu cadastro"
                            + "\n 0 - Sair"
            );

            int clientMenuOption = 0;

            try {
                clientMenuOption = new Scanner(System.in).nextInt();

                if (clientMenuOption < 0 || clientMenuOption > 6) {
                    System.out.println(SystemMessages.INVALID_OPTION.getFieldName());
                }

                switch (clientMenuOption) {
                    case 1:
                        try {
                            getClientBalance(client);
                        } catch (AccountNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            depositOnClientAccount(client);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            withdrawFromAccount(client);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            showTransferMenu(client);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        cardService.showCardMenu(client);
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        runningClientMenu = true;
                        break;
                    default:
                        System.err.println(SystemMessages.INVALID_OPTION.getFieldName());
                }
            } catch (InputMismatchException e) {
                System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
            }
        }
    }

    private void showTransferMenu(Client client) throws AccountNotFoundException, InvalidValueException, InsufficientFundsException {
        boolean runningTransferMenu = false;
        int transferMenuOption = 0;

        while (!runningTransferMenu) {
            System.out.println("Bem vindo ao menu de transferencias."
                    + "Selecione uma opção no menu abaixo"
                    + "1 - Transferencia para outro cliente Eightbank"
                    + "2 - Pix"
                    + "0 - Voltar");

            try {
                transferMenuOption = new Scanner(System.in).nextInt();

                if (transferMenuOption < 0 || transferMenuOption > 2) {
                    System.out.println(SystemMessages.INVALID_OPTION.getFieldName());
                } else {
                    switch (transferMenuOption) {
                        case 1:
                            transferToSameBank(client);
                            break;
                        case 2:
                            transferPix(client);
                            break;
                        case 0:
                            System.out.println("Voltando...");
                            runningTransferMenu = true;
                            break;
                        default:
                            System.err.println(SystemMessages.INVALID_OPTION.getFieldName());
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(SystemMessages.INVALID_OPTION.getFieldName());
            }
        }
    }

    private void transferPix(Client client)
            throws AccountNotFoundException, InvalidValueException, InsufficientFundsException {
        clientAccountsList = accountService.findAccountsByCPF(client.getCpf());

        boolean runningTransferMenu = false;
        char clientOption = ' ';
        String pixKey;

        while (!runningTransferMenu) {
            if (clientAccountsList.size() > 1) {
                System.out.println("\nVimos aqui que você possui mais de uma conta conosco.\n");

                System.out.println("Número da Conta - Tipo da Conta");
                for (Account account : clientAccountsList) {
                    System.out.println(" - " + account.getAccountNumber()
                            + " - " + account.getAccountType().getAccountTypeName());
                }

                System.out.println("\nPor favor digite o numero da conta que deseja utlizar: ");

                try {
                    long senderAccountNumber = new Scanner(System.in).nextLong();
                    Account senderAccount = null;
                    for (Account account : clientAccountsList) {
                        if (account.getAccountNumber() != senderAccountNumber) {
                            System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
                        } else {
                            senderAccount = account;

                            //System.out.println("Digite a chave PIX: ");

                            pixKey = inputPixKey();
                            try {
                                pixKey = new Scanner(System.in).nextLine();

                                System.out.println("Digite o valor que deseja transferir: ");
                                try {
                                    double value = new Scanner(System.in).nextDouble();
                                    accountService.withdraw(senderAccount.getAccountNumber(), value);
                                    System.out.println(
                                            "Transferencia realizada com sucesso!\n"
                                                    + "O valor de R$ " + value
                                                    + "foi transferido para o PIX: " + pixKey
                                    );
                                    break;
                                } catch (InputMismatchException e) {
                                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                                }
                            } catch (InputMismatchException e) {
                                System.out.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                            }
                        }
                    }

                    if (senderAccount == null) {
                        throw new AccountNotFoundException(
                                "\n"
                                        + AnsiColors.ANSI_RED.getAnsiColorCode()
                                        + "Conta não está na lista!"
                                        + AnsiColors.ANSI_RESET.getAnsiColorCode()
                                        + "\n"
                        );
                    }


                } catch (InputMismatchException e) {
                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                }
            } else {
                System.out.println("Localizamos sua conta. Confira os detalhes abaixo: ");
                System.out.println(" - " + clientAccountsList.get(0).getAccountNumber());

                while (true){
                    System.out.println("Digite a chave PIX para qual deseja efetuar a transferencia: ");
                    pixKey = new Scanner(System.in).nextLine();

                    if(pixKey.isEmpty()){
                        System.err.println("Valor vazio!");
                    } else {
                        break;
                    }
                }

                System.out.println("Digite o valor que deseja transferir: ");
                try {
                    double value = new Scanner(System.in).nextDouble();
                    accountService.withdraw(clientAccountsList.get(0).getAccountNumber(), value);
                    System.out.println(
                            "Transferencia realizada com sucesso!\n"
                                    + "O valor de R$ " + value
                                    + "foi transferido para o PIX: " + pixKey
                    );
                    break;
                } catch (InputMismatchException e) {
                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                }
            }
            System.out.println("\nDeseja efetuar outra transferencia PIX?(S/N)");
            clientOption = validateClientOptionYesOrNo();
            if (clientOption != 'S') {
                runningTransferMenu = true;
            }
        }
    }

    private String inputPixKey() {
        String pixKey = null;
        boolean inputPixKeyMenu = false;
        int inputPixKeyMenuOption = 0;

        while (!inputPixKeyMenu){
            System.out.println("""
                    Selecione uma opção abaixo:
                    1 - CPF
                    2 - CNPJ
                    3 - E-mail
                    4 - Celular
                    5 - Chave Aleatória
                    0 - Voltar"""
            );

            inputPixKeyMenuOption = new Scanner(System.in).nextInt();
            if(inputPixKeyMenuOption < 0 || inputPixKeyMenuOption > 5){
                System.err.println(SystemMessages.INVALID_OPTION.getFieldName());
            } else {
                System.out.println("Digite a chave PIX: ");
                pixKey = new Scanner(System.in).nextLine();
                switch (inputPixKeyMenuOption){
                    case 1:
                        CPFValidator.validateCPF(pixKey);
                        break;
                    case 2:
                        //CNPJValidator.validateCNPJ(pixKey);
                        break;
                    case 3:
                        EmailValidator.validateEmail(pixKey);
                        break;
                    case 4:
                        PhoneNumberValidator.validatePhoneNumber(pixKey);
                        break;
                    case 5:
                        break;
                    case 0:
                        pixKey = null;
                        inputPixKeyMenu = true;
                        break;
                }
            }
        }
        return pixKey;
    }

    private void transferToSameBank(Client client) throws
            InvalidValueException, InsufficientFundsException, AccountNotFoundException {
        clientAccountsList = accountService.findAccountsByCPF(client.getCpf());

        boolean runningTransferMenu = false;
        char clientOption = ' ';

        while (!runningTransferMenu) {
            if (clientAccountsList.size() > 1) {
                System.out.println("\nVimos aqui que você possui mais de uma conta conosco.\n");

                System.out.println("Número da Conta - Tipo da Conta");
                for (Account account : clientAccountsList) {
                    System.out.println(" - " + account.getAccountNumber()
                            + " - " + account.getAccountType().getAccountTypeName());
                }

                System.out.println("\nPor favor digite o numero da conta que deseja utlizar: ");

                try {
                    long senderAccountNumber = new Scanner(System.in).nextLong();
                    Account senderAccount = null;
                    for (Account account : clientAccountsList) {
                        if (account.getAccountNumber() != senderAccountNumber) {
                            System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
                        } else {
                            senderAccount = account;

                            System.out.println("Digite o número da conta 'Eightbank' que deseja transferir: ");
                            try {
                                long receiverAccountNumber = new Scanner(System.in).nextLong();
                                Account receiverAccount = accountService.findAccountByNumber(receiverAccountNumber);

                                System.out.println("Digite o valor que deseja transferir: ");
                                try {
                                    double value = new Scanner(System.in).nextDouble();
                                    accountService.withdraw(senderAccount.getAccountNumber(), value);
                                    accountService.deposit(receiverAccount.getAccountNumber(), value);
                                    System.out.println(
                                            "Transferencia realizada com sucesso!\n"
                                                    + "O valor de R$ " + value
                                                    + "foi transferido para a conta: " + receiverAccount.getAccountNumber()
                                    );
                                    break;
                                } catch (InputMismatchException e) {
                                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                                }
                            } catch (InputMismatchException e) {
                                System.out.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                            }
                        }
                    }

                    if (senderAccount == null) {
                        throw new AccountNotFoundException(
                                "\n"
                                        + AnsiColors.ANSI_RED.getAnsiColorCode()
                                        + "Conta não está na lista!"
                                        + AnsiColors.ANSI_RESET.getAnsiColorCode()
                                        + "\n"
                        );
                    }


                } catch (InputMismatchException e) {
                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                }
            } else {
                long clientAccountNumber = clientAccountsList.get(0).getAccountNumber();
                System.out.println("Localizamos sua conta. Confira os detalhes abaixo: ");
                System.out.println(" - " + clientAccountNumber);

                System.out.println("Digite o número da conta 'Eightbank' que deseja transferir: ");
                try {
                    long receiverAccountNumber = new Scanner(System.in).nextLong();

                    System.out.println("Digite o valor que deseja transferir: ");
                    try {
                        double value = new Scanner(System.in).nextDouble();
                        accountService.withdraw(clientAccountNumber, value);
                        accountService.deposit(receiverAccountNumber, value);
                        System.out.println(
                                "Transferencia realizada com sucesso!\n"
                                        + "O valor de R$ " + value
                                        + "foi transferido para a conta: " + receiverAccountNumber
                        );
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                    }
                } catch (InputMismatchException e) {
                    System.out.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                }
            }

            System.out.println("\nDeseja efetuar outra transferencia?(S/N)");
            clientOption = validateClientOptionYesOrNo();
            if (clientOption != 'S') {
                runningTransferMenu = true;
            }
        }
    }

    private void withdrawFromAccount(Client client)
            throws AccountNotFoundException, InvalidValueException, InsufficientFundsException {
        clientAccountsList = accountService.findAccountsByCPF(client.getCpf());

        boolean runningWithdrawFromAccount = false;
        char clientOption = ' ';

        while (!runningWithdrawFromAccount) {
            if (clientAccountsList.size() > 1) {
                System.out.println("\nVimos aqui que você possui mais de uma conta conosco.\n");

                System.out.println("Número da Conta - Tipo da Conta");
                for (Account account : clientAccountsList) {
                    System.out.println(" - " + account.getAccountNumber()
                            + " - " + account.getAccountType().getAccountTypeName());
                }

                System.out.println("\nPor favor digite o numero da conta que deseja efetuar o saque: ");

                try {
                    long accountNumber = new Scanner(System.in).nextLong();

                    Account accountToCheck = null;

                    for (Account account : clientAccountsList) {
                        if (account.getAccountNumber() != accountNumber) {
                            System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
                        } else {
                            accountToCheck = accountService.findAccountByNumber(accountNumber);

                            System.out.println("Digite o valor que deseja sacar: ");
                            try {
                                double value = new Scanner(System.in).nextDouble();
                                accountService.withdraw(accountToCheck.getAccountNumber(), value);
                                break;
                            } catch (InputMismatchException e) {
                                System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                            }
                        }
                    }

                    if (accountToCheck == null) {
                        throw new AccountNotFoundException(
                                "\n"
                                        + AnsiColors.ANSI_RED.getAnsiColorCode()
                                        + "Conta não está na lista!"
                                        + AnsiColors.ANSI_RESET.getAnsiColorCode()
                                        + "\n"
                        );
                    }
                } catch (InputMismatchException e) {
                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                }
            } else {
                System.out.println("Localizamos sua conta. Confira os detalhes abaixo: ");
                System.out.println(" - " + clientAccountsList.get(0).getAccountNumber());

                System.out.println("Digite o valor que deseja sacar: ");
                try {
                    double value = new Scanner(System.in).nextDouble();
                    accountService.withdraw(clientAccountsList.get(0).getAccountNumber(), value);
                } catch (InputMismatchException e) {
                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                }
            }

            System.out.println("\nDeseja efetuar outro saque?(S/N)");
            clientOption = validateClientOptionYesOrNo();
            if (clientOption != 'S') {
                runningWithdrawFromAccount = true;
            }
        }
    }

    private void depositOnClientAccount(Client client) throws AccountNotFoundException {
        clientAccountsList = accountService.findAccountsByCPF(client.getCpf());

        boolean runningDepositMenu = false;
        char clientOption = ' ';

        while (!runningDepositMenu) {
            if (clientAccountsList.size() > 1) {
                System.out.println("\nVimos aqui que você possui mais de uma conta conosco.\n");

                System.out.println("Número da Conta - Tipo da Conta");
                for (Account account : clientAccountsList) {
                    System.out.println(" - " + account.getAccountNumber()
                            + " - " + account.getAccountType().getAccountTypeName());
                }

                System.out.println("\nPor favor digite o numero da conta que deseja efetuar o depósito: ");

                try {
                    long accountNumber = new Scanner(System.in).nextLong();

                    Account accountToCheck = null;

                    for (Account account : clientAccountsList) {
                        if (account.getAccountNumber() != accountNumber) {
                            System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
                        } else {
                            accountToCheck = accountService.findAccountByNumber(accountNumber);

                            System.out.println("Digite o valor que deseja depositar: ");
                            try {
                                double value = new Scanner(System.in).nextDouble();
                                accountService.deposit(accountToCheck.getAccountNumber(), value);
                                break;
                            } catch (InputMismatchException e) {
                                System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                            }
                        }
                    }

                    if (accountToCheck == null) {
                        throw new AccountNotFoundException(
                                "\n"
                                        + AnsiColors.ANSI_RED.getAnsiColorCode()
                                        + "Conta não está na lista!"
                                        + AnsiColors.ANSI_RESET.getAnsiColorCode()
                                        + "\n"
                        );
                    }

                    System.err.println();

                    System.out.println("\nDeseja efetuar outro depósito?(S/N)");
                    clientOption = validateClientOptionYesOrNo();
                    if (clientOption != 'S') {
                        runningDepositMenu = true;
                    }

                } catch (InputMismatchException e) {
                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                }
            } else {
                System.out.println("Contas encontradas: ");
                System.out.println("Número da Conta - Tipo da Conta");
                System.out.println(" - " + clientAccountsList.get(0).getAccountNumber());

                System.out.println("Digite o valor que deseja depositar: ");
                try {
                    double value = new Scanner(System.in).nextDouble();
                    accountService.deposit(clientAccountsList.get(0).getAccountNumber(), value);
                } catch (InputMismatchException e) {
                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                }
            }
        }
    }

    private void getClientBalance(Client client) throws AccountNotFoundException {
        clientAccountsList = accountService.findAccountsByCPF(client.getCpf());

        boolean runningGetClientBalanceMenu = false;
        char clientOption = ' ';

        while (!runningGetClientBalanceMenu) {
            if (clientAccountsList.size() > 1) {

                System.out.println("\nVimos aqui que você possui mais de uma conta conosco.\n");

                System.out.println("Número da Conta - Tipo da Conta");
                for (Account account : clientAccountsList) {
                    System.out.println(" - " + account.getAccountNumber()
                            + " - " + account.getAccountType().getAccountTypeName());
                }

                System.out.println("\nPor favor digite o numero da conta que deseja visualizar o saldo: ");

                try {
                    long accountNumber = new Scanner(System.in).nextLong();

                    Account accountToCheck = null;

                    for (Account account : clientAccountsList) {
                        if (account.getAccountNumber() != accountNumber) {
                            System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
                        } else {
                            accountToCheck = accountService.findAccountByNumber(accountNumber);

                            System.out.println(
                                    "\nConta selecionada: "
                                            + accountToCheck.getAccountNumber()
                                            + " - " + accountToCheck.getAccountType().getAccountTypeName()
                                            + "\n"
                                            + "Saldo atual: R$ " + accountToCheck.getBalance()
                            );
                            break;
                        }
                    }

                    if (accountToCheck == null) {
                        throw new AccountNotFoundException("Conta não está na lista!");
                    }

                    System.out.println("\nDeseja visualizar o saldo de outra conta?(S/N)");
                    clientOption = validateClientOptionYesOrNo();
                    if (clientOption != 'S') {
                        runningGetClientBalanceMenu = true;
                    }
                } catch (InputMismatchException e) {
                    System.err.println(SystemMessages.INVALID_CHARACTER.getFieldName());
                }
            } else {
                System.out.println("Número da Conta - Tipo da Conta");

                for (Account account : clientAccountsList) {
                    System.out.println(" - " + account.getAccountNumber()
                            + " - " + account.getAccountType().getAccountTypeName());
                }
                for (Account account : clientAccountsList) {
                    System.out.println(
                            "Conta selecionada: "
                                    + account.getAccountNumber()
                                    + " - " + account.getAccountType().getAccountTypeName()
                                    + "\n"
                                    + "Saldo atual: R$ " + account.getBalance()
                    );
                }
                System.out.println("Deseja continuar visualizando saldos?(S/N)");
                clientOption = validateClientOptionYesOrNo();
                if (clientOption != 'S') {
                    runningGetClientBalanceMenu = true;
                }

            }
        }
    }

    private char validateClientOptionYesOrNo() {
        char clientOption = ' ';

        while (true) {
            System.out.println("Digite uma opção válida, por favor: ");
            try {
                clientOption = new Scanner(System.in).next().charAt(0);
                if (clientOption == 's' || clientOption == 'S') {
                    clientOption = 'S';
                    break;
                } else if (clientOption == 'n' || clientOption == 'N') {
                    clientOption = 'N';
                    break;
                } else {
                    System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println(SystemMessages.INVALID_CHARACTER.getFieldName());
            }
        }

        return clientOption;
    }

    public void importClientsFromFile(String fileName) throws NoSuchAlgorithmException, InvalidKeySpecException {
        while (true) {
            //System.out.println("Digite o nome do arquivo:");
            //String fileName = new Scanner(System.in).nextLine();
            if (!fileName.isEmpty()) {
                if (!FileNameValidator.validateFileName(fileName)) {
                    System.err.println("Formato invalido!");
                } else {
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(fileName));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] fields = line.split(",");
                            String cpf = fields[0];
                            double grossMonthlyIncome = Double.parseDouble(fields[1]);
                            String email = fields[2];
                            String name = fields[3];

                            String dateOfBirth = fields[4];
                            String[] dateOfBirthFields = dateOfBirth.split("/");
                            int dayOfMonth = Integer.parseInt(dateOfBirthFields[0]);
                            int month = Integer.parseInt(dateOfBirthFields[1]);
                            int year = Integer.parseInt(dateOfBirthFields[2]);
                            LocalDate dateOfBirthConverted = LocalDate.of(year, month, dayOfMonth);

                            Address address = new Address(
                                    fields[5],
                                    Long.parseLong(fields[6]),
                                    fields[7],
                                    fields[8],
                                    fields[9],
                                    fields[10]
                            );

                            String phoneNumber = fields[11];
                            String passwordString = PasswordService.generateStrongPassword(fields[12]);

                            clientCategory = checkClientCategory(grossMonthlyIncome);

                            client = new Client(
                                    email,
                                    passwordString,
                                    name,
                                    cpf,
                                    dateOfBirthConverted,
                                    address,
                                    clientCategory,
                                    phoneNumber,
                                    grossMonthlyIncome
                            );

                            clientDAO.addClient(client);

                            registerClientAccounts(client, 3);

                            // Lista os usuários cadastrados, à partir do método toString
                            //System.out.println("Clientes\n");
                            //clientDAO.listClients();

                            //Listar contas e titulares
                            //System.out.println("\n Clientes e contas");
                            //accountService.listAccounts();
                        }
                        reader.close();
                    } catch (IOException e) {
                        System.err.println("Erro ao carregar o arquivo: " + fileName);
                    }
                    System.out.println("Usuários importados com sucesso!");
                    break;
                }
            } else {
                System.err.println("Digite o nome do arquivo!");
            }
        }
    }
}