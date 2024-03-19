package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.ClientDAO;
import br.com.cdb.java.grupo4.eightbank.model.user.client.Client;
import br.com.cdb.java.grupo4.eightbank.utils.CPFValidator;
import br.com.cdb.java.grupo4.eightbank.utils.NameValidator;

public class ClientService {

    ClientDAO clientDAO = new ClientDAO();

    public boolean addClient(Client client){

        long cpf = 0L;
        String name = "";

        if (!CPFValidator.validateCPF(cpf)) {
            return false;
        }

        if (!NameValidator.validateName(name)) {
            return false;
        }

        clientDAO.addClient(client);

        return true;
    }

}
