package br.com.cdb.java.grupo4.eightbank.model.insurance;

import br.com.cdb.java.grupo4.eightbank.enuns.InsuranceType;

import java.time.LocalDate;

public class Insurance {
    private long policyNumber;
    private LocalDate issueDate;
    private double policyValue;
    private String policyConditions;

    private InsuranceType insuranceType;

    public Insurance(LocalDate issueDate, double policyValue, String policyConditions, InsuranceType insuranceType) {
        this.issueDate = issueDate;
        this.policyValue = policyValue;
        this.policyConditions = policyConditions;
        this.insuranceType = insuranceType;
    }

    public long getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(long policyNumber) {
        this.policyNumber = policyNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public double getPolicyValue() {
        return policyValue;
    }

    public String getPolicyConditions() {
        return policyConditions;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "policyNumber=" + policyNumber +
                ", issueDate=" + issueDate +
                ", policyValue=" + policyValue +
                ", policyConditions='" + policyConditions + '\'' +
                ", insuranceType=" + insuranceType +
                '}';
    }
}
