package br.com.cdb.java.grupo4.eightbank.model.card;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CardFactory {
    private static final Set<String> issuedCardNumbers = new HashSet<>();
    private static final Random random = new Random();
    private static final int CARD_NUMBER_LENGTH = 16;

    public static Card createCard(CardType cardType, LocalDate expirationDate, int cvv, String ownerName, double limitOrDaily) {
        String uniqueCardNumber = generateUniqueCardNumber();
        switch (cardType) {
            case CREDIT:
                return new CreditCard(uniqueCardNumber, expirationDate, cvv, ownerName, limitOrDaily);
            case DEBIT:
                return new DebitCard(uniqueCardNumber, expirationDate, cvv, ownerName, limitOrDaily);
            default:
                throw new IllegalArgumentException("Tipo de cartão desconhecido.");
        }
    }

    private static String generateUniqueCardNumber() {
        while (true) {
            String cardNumber = generateRandomCardNumber();
            if (!issuedCardNumbers.contains(cardNumber)) {
                issuedCardNumbers.add(cardNumber);
                return cardNumber;
            }
        }
    }

    private static String generateRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder(CARD_NUMBER_LENGTH);
        for (int i = 0; i < CARD_NUMBER_LENGTH; i++) {
            int digit = random.nextInt(10); // Gera um dígito aleatório entre 0-9
            cardNumber.append(digit);
        }
        return cardNumber.toString();
    }
}

public enum CardType {
    CREDIT,
    DEBIT
}
