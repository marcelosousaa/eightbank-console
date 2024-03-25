package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.CardDAO;
import br.com.cdb.java.grupo4.eightbank.dao.ClientDAO;
import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.enuns.ClientCategory;
import br.com.cdb.java.grupo4.eightbank.enuns.SystemMessages;
import br.com.cdb.java.grupo4.eightbank.exceptions.AccountNotFoundException;
import br.com.cdb.java.grupo4.eightbank.exceptions.InvalidValueException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.client.Address;
import br.com.cdb.java.grupo4.eightbank.model.client.Client;
import br.com.cdb.java.grupo4.eightbank.utils.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientService {
    List<Account> clientAccountsList;
    private CardDAO cardDAO = new CardDAO(); // Criação de CardDAO
    private ClientDAO clientDAO = new ClientDAO(); // Já existente
    private CardService cardService;
    private InsuranceService insuranceService = new InsuranceService();
    private AccountService accountService = new AccountService();
    private ClientCategory clientCategory;

    public ClientService() {
        // Inicializando CardService com as instâncias necessárias
        this.cardService = new CardService(cardDAO, clientDAO);
    }

    public boolean clientRegistration() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidValueException {

        String cpf = inputCPF();
        double grossMonthlyIncome = inputGrossMonthlyIncome();
        String email = inputEmail();
        String name = inputName();
        LocalDate dateOfBirth = inputDateOfBirth();
        Address address = inputAddress();
        String phoneNumber = inputPhoneNumber();
        String passwordString = inputPassword();

        Client client = new Client(
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

        // Cria a(s) conta(s) do cliente e devolve para um ArrayList
        List<Account> clientAccountsList = clientAccountsRegistration(client);

        //Itera no array retornado e seta o Cliente como owner das contas
        for (Account account : clientAccountsList) {
            accountService.setAccountOwner(account, client);
        }

        // Lista os usuários cadastrados, à partir do método toString
        clientDAO.listClients();

        //Listar contas e titulares
        accountService.listAccounts();

        return true;
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
            Scanner scanner = new Scanner(System.in);
            name = new Scanner(System.in).nextLine();

            if (!NameValidator.validateName(name)) {
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
                } else if (clientDAO.searchClientByCPF(cpf) != null) {
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
                    account = accountService.createSavingsAccount(client, annualPercentageYield);
                    accountList.add(account);
                } else if (accountTypeOption == 2) {
                    account = accountService.createCurrentAccount(client, currentAccountMonthlyFee);
                    accountList.add(account);
                } else {
                    account = accountService.createSavingsAccount(client, annualPercentageYield);
                    accountList.add(account);
                    account = accountService.createCurrentAccount(client, currentAccountMonthlyFee);
                    accountList.add(account);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(SystemMessages.INVALID_CARACTER.getFieldName());
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
                    PasswordService.validatePassword(passwordString, client.getPassword());
                } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                    System.out.println("Senha inválida!");
                    client = null;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (client != null) {
                System.out.println("\nLogin realizado com sucesso!\n" + "Bem-vindo " + client.getName() + "!");
            } else {
                System.out.println(
                        "\nNao foi possivel realizar o login, verifique seus dados e tente novamente.\n");
            }
        }
        return client;
    }

    public void clientMenu(Client client) {
        boolean runningClientMenu = false;

        while (!runningClientMenu) {
            System.out.println("Selecione uma opcao abaixo: "
                    + "\n 1 - Saldo"
                    + "\n 2 - Depósito"
                    + "\n 3 - Saque"
                    + "\n 4 - Transferencias"
                    + "\n 5 - Cartões"
                    + "\n 6 - Meu cadastro"
                    + "\n 0 - Sair"
            );

            int clientMenuOption = 0;

            try {
                clientMenuOption = new Scanner(System.in).nextInt();

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
                    case 5:
                        this.cardService.requestCard(client);
                        break;
                    case 0:
                        runningClientMenu = true;
                    default:
                        System.err.println(SystemMessages.INVALID_OPTION.getFieldName());
                }
            } catch (InputMismatchException e) {
                System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
            }
        }
    }

    private void depositOnClientAccount(Client client) throws AccountNotFoundException {
        clientAccountsList = accountService.findAccountsByCPF(client.getCpf());

        if (clientAccountsList.size() > 1) {
            System.out.println("\nVimos aqui que você possui mais de uma conta conosco."
                    + "Por favor digite o numero da conta que deseja obter o saldo");

            System.out.println("Contas encontradas: ");
            for (Account account : clientAccountsList) {
                System.out.println(" - " + account.getAccountNumber());
            }

            try {
                long accountNumber = new Scanner(System.in).nextLong();
                for (Account account : clientAccountsList) {
                    if (account.getAccountNumber() == accountNumber) {
                        System.out.println("Digite o valor que deseja depositar: ");
                        try {
                            double value = new Scanner(System.in).nextDouble();
                            accountService.deposit(accountNumber, value);
                        } catch (InputMismatchException e) {
                            System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
                        }
                        break;
                    } else {
                        throw new AccountNotFoundException("Esta conta não está na lista.");
                    }
                }

            } catch (InputMismatchException e) {
                System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
            }
        } else {
            System.out.println("Contas encontradas: ");
            System.out.println(" - " + clientAccountsList.get(0).getAccountNumber());

            System.out.println("Digite o valor que deseja depositar: ");
            try {
                double value = new Scanner(System.in).nextDouble();
                accountService.deposit(clientAccountsList.get(0).getAccountNumber(), value);
            } catch (InputMismatchException e) {
                System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
            }
        }

    }

    private void getClientBalance(Client client) throws AccountNotFoundException {
        clientAccountsList = accountService.findAccountsByCPF(client.getCpf());

        boolean runningGetClientBalanceMenu = false;
        char clientOption = ' ';

        while (!runningGetClientBalanceMenu) {
            if (clientAccountsList.size() > 1) {

                System.out.println("\nVimos aqui que você possui mais de uma conta conosco.\n"
                        + "Por favor digite o numero da conta que deseja obter o saldo");

                System.out.println("Contas encontradas: ");
                for (Account account : clientAccountsList) {
                    System.out.println(" - " + account.getAccountNumber());
                }

                try {
                    long accountNumber = new Scanner(System.in).nextLong();
                    for (Account account : clientAccountsList) {
                        if (account.getAccountNumber() == accountNumber) {
                            System.out.println(
                                    "Conta selecionada: "
                                            + account.getAccountNumber()
                                            + " - " + account.getAccountType().getAccountTypeName()
                                            + "\n"
                                            + "Saldo atual: R$ " + account.getBalance()
                            );
                            System.out.println("Deseja visualizar o saldo de outra conta?(S/N)");
                            clientOption = validateClientOptionYesOrNo();
                            if (clientOption != 'S') {
                                runningGetClientBalanceMenu = true;
                                break;
                            }
                        }
                    }
                } catch (InputMismatchException e) {
                    System.err.println(SystemMessages.INVALID_CARACTER.getFieldName());
                }
            } else {
                System.out.println("Contas encontradas: ");
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
                    break;
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
                System.out.println(SystemMessages.INVALID_CARACTER.getFieldName());
            }
        }

        return clientOption;
    }
}