package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.AccountDAO;
import br.com.cdb.java.grupo4.eightbank.dao.UserDAO;
import br.com.cdb.java.grupo4.eightbank.enuns.AccountType;
import br.com.cdb.java.grupo4.eightbank.enuns.ClientCategory;
import br.com.cdb.java.grupo4.eightbank.enuns.SystemMessages;
import br.com.cdb.java.grupo4.eightbank.exceptions.UserNotFoundException;
import br.com.cdb.java.grupo4.eightbank.model.account.Account;
import br.com.cdb.java.grupo4.eightbank.model.user.User;
import br.com.cdb.java.grupo4.eightbank.model.user.admin.Administrator;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Address;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;
import br.com.cdb.java.grupo4.eightbank.utils.CPFValidator;
import br.com.cdb.java.grupo4.eightbank.utils.DateOfBirthValidator;
import br.com.cdb.java.grupo4.eightbank.utils.EmailValidator;
import br.com.cdb.java.grupo4.eightbank.utils.ZipCodeValidator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserService {
    Scanner scanner = new Scanner(System.in);
    User user = null;
    UserDAO userDAO = new UserDAO();
    String email = null;
    String passwordString = null;
    AccountService accountService = new AccountService();
    AccountDAO accountDAO = new AccountDAO();
    CardService cardService = new CardService();
    String cpf;
    String name;
    LocalDate localDate;
    private String streetName;
    private long number;
    private String district;
    private String city;
    private String state;
    private String zipCode;
    private AccountType accountType;
    public boolean adminRegistration() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String passwordString = "senha";
        String strongPassword = PasswordService.generateStrongPassword(passwordString);

        Administrator administrator = new Administrator("admin@teste.com", strongPassword, "Administrador");

        userDAO.addUser(administrator);

        return true;
    }

    public boolean clientRegistration() throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserDAO userDAO = new UserDAO();
        Client client;
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
            System.out.println("Digite o numero do seu CPF: ");
            try {
                cpf = scanner.nextLine();
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
            System.out.println("Digite sua data de nascimento, no formato(DD/MM/AAAA):");
            String dob = scanner.nextLine();
            if (!DateOfBirthValidator.validateDateOfBirth(dob)) {
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
                scanner.nextLine();
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
                System.out.println(SystemMessages.MANDATORY_FIELD_PT_BR.getFieldName());
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
            System.out.println("OK! Quase finalizando... Agora informa seu CEP, com hífen: ");
            String zipCodeString = scanner.nextLine();
            if (!ZipCodeValidator.validateZipCode(zipCodeString)) {
                System.out.println(SystemMessages.INVALID_ZIP_CODE.getFieldName());
                scanner.nextLine();
            } else {
                String cleanZipCode = zipCodeString.replace("-", "");
                zipCode = cleanZipCode;
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
        //scanner.close();

        Address address = new Address(streetName, number, district, city, state, zipCode);
        accountType = AccountType.CURRENT_ACCOUNT;

        Account account = accountService.createAccount(accountType);

        client = new Client(email, passwordString, name, ClientCategory.COMMOM, cpf, localDate, address, account);
        userDAO.addUser(client);

        accountDAO.setAccountOwner(account.getAccountNumber(), client);

        return true;
    }

    public User login() {
        Scanner scanner = new Scanner(System.in);
        User user = null;
        while (true) {
            System.out.println("Digite seu email: ");
            String email = scanner.nextLine();
            if (email.isEmpty()) {
                System.err.println("Campo obrigatório!");
                continue;
            } else if (!EmailValidator.validateEmail(email)) {
                System.err.println("Formato inválido!");
                continue;
            }

            System.out.println("Digite sua senha: ");
            String passwordString = scanner.nextLine();
            if (passwordString.isEmpty()){
                System.out.println("Campo obrigatório");
                continue;
            }

            try {
                user = userDAO.searchUserByEmail(email);
                if (PasswordService.validatePassword(passwordString, user.getPassword())) {
                    System.out.println("\nLogin realizado com sucesso!\n" + "Bem-vindo " + user.getName() + "!");
                    menuPrincipal(user);
                    break; // Sucesso no login, sai do loop
                } else {
                    System.out.println("Senha inválida!");
                    // A senha está incorreta. Este bloco só será alcançado se modificar o validatePassword para não lançar exceção em caso de senha incorreta.
                }
            }catch (UserNotFoundException e){
                System.out.println("Usuário não encontrado.");
            }catch (InvalidKeySpecException | NoSuchAlgorithmException e){
                System.out.println("Erro ao validar a senha.");

            }

            System.out.println("\n1. Tentar fazer login novamente");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer do scanner

            switch (option){
                case 1:
                    System.out.println("Tente novamente.");
                    break;
                case 2:
                    System.out.println("Encerrando...\n"
                            + "Obrigado por utilizar nosso sistema.");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
        return user;
    }

    public void menuPrincipal(User user){
        System.out.println("\nBem-vindo ao Menu, " + user.getName());
        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Pedir cartão;");
            System.out.println("2. Informações da conta;");
            System.out.println("3. Operações do cartão;");
            System.out.println("4. Ativação/Desativação de seguros;");
            System.out.println("5. Editar perfil;");
            System.out.println("0. Sair.");
            System.out.print("Sua escolha: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    cardService.requestCard();
                    break;
                case 2:


                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 0:
                    System.out.println("Encerrando...\n"
                            + "Obrigado por utilizar nosso sistema.");
                    System.exit(0);
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        }
    }
}