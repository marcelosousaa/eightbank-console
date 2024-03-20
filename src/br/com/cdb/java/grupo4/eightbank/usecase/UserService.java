package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.UserDAO;
import br.com.cdb.java.grupo4.eightbank.exceptions.UserNotFoundException;
import br.com.cdb.java.grupo4.eightbank.model.user.User;
import br.com.cdb.java.grupo4.eightbank.utils.EmailValidator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class UserService {

    public User login() {

        User user = null;
        UserDAO userDAO = new UserDAO();
        String email = null;
        char[] passwordChar;
        String passwordString = null;

        while (user == null) {

            while (true) {
                System.out.println("Digite seu email: ");
                email = new Scanner(System.in).nextLine();
                if (email.isEmpty()) {
                    System.err.println("Campo obrigatório!");
                } else if (EmailValidator.validateEmail(email)) {
                    System.err.println("Formato inválido!");
                } else {
                    break;
                }
            }

            while (true) {
                passwordChar = new Scanner(System.in).next().toCharArray();

                if (passwordChar.length == 0) {
                    System.err.println("Campo obrigatorio!");
                } else {
                    passwordString = new String(passwordChar);
                    break;
                }
            }

            try{
                user = userDAO.searchUserByEmail(email);
                try {
                    PasswordService.validatePassword(passwordString, user.getPassword().toString());
                } catch (InvalidKeySpecException | NoSuchAlgorithmException e){
                    System.out.println("Senha inválida!");
                    user = null;
                }
            } catch (UserNotFoundException e){
                e.getMessage();
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