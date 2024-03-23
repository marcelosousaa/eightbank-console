package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.CardDAO;
import br.com.cdb.java.grupo4.eightbank.model.client.Client;

public class CardService {

    CardDAO cardDAO = new CardDAO();

    public void showCardMenu(Client client) {
        System.out.println("1 - Pedir cartão novo"
        + "2 - Visualizar Meus cartões"
        + "0 - Voltar");



        }
    }
