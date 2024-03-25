package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.model.client.Client;
import br.com.cdb.java.grupo4.eightbank.usecase.PasswordService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientDAO {

    List<Client> clientList = new ArrayList<>();

    public ClientDAO() {
        this.clientList = new ArrayList<>();
        // Inicializar a lista de clientes
    }

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


    public Client searchClientByCPF(String cpf) {
        for (Client client : clientList) {
            if (client.getCpf().equals(cpf)) {
                return client;
            }
            break;
        }
        return null;
    }

    public boolean verifyClientPassword(String cpf, String inputPassword) {
        for (Client client : clientList) {
            if (client.getCpf().equals(cpf)) {
                try {
                    // Usa PasswordService para validar a senha
                    if (PasswordService.validatePassword(inputPassword, client.getPassword())) {
                        return true;
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                    // Considerar como falha de verificação em caso de exceção
                    return false;
                }
            }
        }
        return false;
    }
}
