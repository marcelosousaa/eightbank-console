package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.model.card.Card;
import br.com.cdb.java.grupo4.eightbank.model.card.CardFactory;
import br.com.cdb.java.grupo4.eightbank.model.card.CardType;
import java.time.LocalDate;
import java.util.Scanner;

public class CardService {
    private Scanner scanner = new Scanner(System.in);
    //private CartaoDAO cartaoDAO = CartaoDAO.getInstance();

    public void requestCard() {
        while (true) {
            System.out.println("\nEscolha o tipo de cartão que deseja solicitar:");
            System.out.println("1. Cartão de Débito");
            System.out.println("2. Cartão de Crédito");
            System.out.println("0. Voltar");

            int cardTypeChoice = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer do scanner

            if (cardTypeChoice == 0) {
                return; // Retorna ao menu anterior
            }

            CardType cardType = cardTypeChoice == 1 ? CardType.DEBIT : CardType.CREDIT;
            String cardPassword = requestCardPassword();

            if (cardPassword != null) {
                // Simular a criação e salvamento do cartão
                Card newCard = generateAndSaveCard(cardType, cardPassword);
                System.out.println("Seu pedido de cartão de " + cardType + " foi confirmado!");
                System.out.println("Número do Cartão: " + newCard.getNumber());
                System.out.println("Data de Validade: " + newCard.getExpirationDate());
                // Note: Por questões de segurança, não exibiremos o CVV ou a senha.
                break; // Sai do loop após a confirmação
            }
        }
    }

    private String requestCardPassword() {
        String password, confirmPassword;
        while (true) {
            System.out.print("Definir senha: ");
            password = scanner.nextLine();

            System.out.print("Confirmar senha: ");
            confirmPassword = scanner.nextLine();

            if (password.equals(confirmPassword)) {
                return password; // Senha confirmada
            } else {
                System.out.println("As senhas não coincidem. Tente novamente.");
            }

            System.out.println("\n0. Voltar");
            System.out.println("Pressione qualquer outra tecla para tentar novamente.");
            String choice = scanner.nextLine();
            if ("0".equals(choice)) {
                return null; // Usuário optou por voltar
            }
        }
    }

    private Card generateAndSaveCard(CardType cardType, String cardPassword) {
        // Supondo que a lógica de geração de número único e armazenamento esteja dentro da CardFactory e CartaoDAO
        Card newCard = CardFactory.createCard(cardType, LocalDate.now().plusYears(5), 123, "Usuário Teste", 5000.00);
        // Simulando a adição do cartão ao DAO (persistência)
        // cartaoDAO.addCard(newCard);
        return newCard;
    }
}

