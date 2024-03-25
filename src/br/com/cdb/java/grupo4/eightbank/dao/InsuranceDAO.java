package br.com.cdb.java.grupo4.eightbank.dao;

import br.com.cdb.java.grupo4.eightbank.model.insurance.Insurance;

import java.util.ArrayList;
import java.util.List;

public class InsuranceDAO {

    List<Insurance> insuranceList = new ArrayList<>();

    public void addInsurance(Insurance insurance) {
        insurance.setPolicyNumber(insuranceList.size() + 1);
        insuranceList.add(insurance);
    }
}
