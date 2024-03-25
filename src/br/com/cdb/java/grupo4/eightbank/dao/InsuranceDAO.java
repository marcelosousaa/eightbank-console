package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.model.card.Card;
import br.com.cdb.java.grupo4.eightbank.model.insurance.Insurance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsuranceDAO {

    List<Insurance> insuranceList = new ArrayList<>();

    public void addInsurance(Insurance insurance) {
        insurance.setPolicyNumber(insuranceList.size() + 1);
        insuranceList.add(insurance);
    }

    public List<Insurance> findByClientCPF(String cpf) {
        return insuranceList.stream()
                .filter(insuranceList -> insuranceList.getClientCPF().equals(cpf))
                .collect(Collectors.toList());
    }

    public boolean removeInsurance(long policyNumber) {
        return insuranceList.removeIf(insurance -> insurance.getPolicyNumber() == policyNumber);
    }
}
