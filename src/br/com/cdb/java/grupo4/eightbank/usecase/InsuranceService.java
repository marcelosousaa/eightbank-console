package br.com.cdb.java.grupo4.eightbank.usecase;

import br.com.cdb.java.grupo4.eightbank.dao.InsuranceDAO;
import br.com.cdb.java.grupo4.eightbank.enuns.InsuranceType;
import br.com.cdb.java.grupo4.eightbank.enuns.PolicyConditions;
import br.com.cdb.java.grupo4.eightbank.enuns.SystemMessages;
import br.com.cdb.java.grupo4.eightbank.model.card.Card;
import br.com.cdb.java.grupo4.eightbank.model.client.Client;
import br.com.cdb.java.grupo4.eightbank.model.insurance.Insurance;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InsuranceService {

    InsuranceDAO insuranceDAO = new InsuranceDAO();

    public void showInsuranceMenu(Client client) {
        while (true){
            System.out.println("Bem vindo ao menu de seguros do seu cartão de crédito."
                    + "\nQue tipo de seguro deseja adquirir hoje: "
                    + "\n1- Consultar meus seguros"
                    + "\n2 - Seguro viagem"
                    + "\n3 - Seguro contra fraude"
                    + "\n\nPression 0 para voltar."
            );

            try{
                int menuOption = new Scanner(System.in).nextInt();

                if(menuOption < 0 || menuOption > 3){
                    System.out.println(SystemMessages.INVALID_OPTION.getFieldName());
                } else if(menuOption == 0){
                    break;
                } else {
                    switch (menuOption){
                        case 1:
                            break;
                        case 2:
                            break;
                        default:
                            System.out.println(SystemMessages.INVALID_OPTION.getFieldName());
                    }
                }

            }catch (InputMismatchException e){
                System.out.println(SystemMessages.INVALID_CHARACTER.getFieldName());
            }


            break;
        }


    }

    public void generateTravelInsurance(Card card){

    }

    public void generateFraudInsurance(Card card){

        Insurance insurance = new Insurance(
                LocalDate.now(),
                5000,
                PolicyConditions.FRAUD_INSURANCE_CONDITIONS.getInsuranceConditions(),
                InsuranceType.INSURANCE_FRAUD
        );


        insuranceDAO.addInsurance(insurance);
    }

}
