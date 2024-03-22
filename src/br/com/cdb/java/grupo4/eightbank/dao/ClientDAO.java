package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.model.client.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientDAO {

    List<Client> clientList = new ArrayList<>();

    public void addClient(Client client) {
        client.setId(clientList.size() + 1);
        clientList.add(client);
    }

    public void listClients() {
        for (Client client : this.clientList) {
            if(client instanceof Client){
                System.out.println(((Client) client));
            }
        }
    }

    public Client searchClientByEmail(String email) {
        for (Client client : clientList) {
            if (Objects.equals(client.getEmail(), email)) {
                return client;
            }
        }
        return null;
    }

    public Client searchClientById(int id) {
        for (Client client : clientList) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }


    public boolean searchClientByCPF(String cpf) {
        for (Client client : clientList) {
                if (client.getCpf().equals(cpf)) {
                    return true;
                }
                break;
        }
        return false;
    }
}
