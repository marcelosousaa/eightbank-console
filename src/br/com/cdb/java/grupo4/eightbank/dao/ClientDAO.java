package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.enuns.SystemMessages;
import br.com.cdb.java.grupo4.eightbank.exceptions.ClientNotFoundException;
import br.com.cdb.java.grupo4.eightbank.model.client.Address;
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
            if (client instanceof Client) {
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

    public void updateClientProfileName(Client client, String value)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.setName(value);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }

    }

    public void updateClientProfileEmail(Client client, String value)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.setEmail(value);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }

    }

    public void updateClientProfilePassword(Client client, String value)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.setPassword(value);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }
    }

    public void updateClientProfilePhoneNumber(Client client, String value)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.setPhoneNumber(value);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }
    }

    public void updateClientProfileGrossMonthlyIncome(Client client, double value)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.setGrossMonthlyIncome(value);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }

    }

    public void updateClientProfileAddressStreetName(Client client, String newStreetName)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.getAddress().setStreetName(newStreetName);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }

    }

    public void updateClientProfileAddressNumber(Client client, long newNumber)
            throws ClientNotFoundException {
        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.getAddress().setNumber(newNumber);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }
    }

    public void updateClientProfileAddressDistrictName(Client client, String newDistrictName)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.getAddress().setDistrict(newDistrictName);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }
    }

    public void updateClientProfileAddressCityName(Client client, String newCityName)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.getAddress().setCity(newCityName);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }
    }

    public void updateClientProfileAddressStateName(Client client, String newStateName)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.getAddress().setState(newStateName);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }
    }

    public void updateClientProfileAddressCep(Client client, String newZipCode)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.getAddress().setZipCode(newZipCode);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }
    }

    public void updateClientProfileAddressComplement(Client client, String newAddressComplement)
            throws ClientNotFoundException {

        boolean clientSearchResult = false;

        for (Client c : this.clientList) {
            if (!c.equals(client)) {
                System.out.println(SystemMessages.PROCESSING_PT_BR.getFieldName());
            } else {
                clientSearchResult = true;
                c.getAddress().setAddressComplement(newAddressComplement);
            }
        }

        if (!clientSearchResult) {
            throw new ClientNotFoundException("Não encontramos seu cadastro :/");
        }
    }
}
