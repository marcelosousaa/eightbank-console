package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    List<Client> clientList = new ArrayList<>();

    public void addClient(Client client){
        client.setId(clientList.size());
        clientList.add(client);
    }

    public Client searchClient(int id) {
        for(Client c : clientList) {
            if(c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public void listClients() {
        for(Client c : this.clientList) {
            System.out.println(c.getName());
        }
    }
}
