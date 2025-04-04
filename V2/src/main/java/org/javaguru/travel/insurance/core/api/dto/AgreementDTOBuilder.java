package org.javaguru.travel.insurance.core.api.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgreementDTOBuilder {

    private LocalDate agreementDateFrom;
    private LocalDate agreementDateTo;
    private String country;
    private List<String> selectedRisks = new ArrayList<>();
    private List<PersonDTO> persons = new ArrayList<>();
    private BigDecimal agreementPremium;

    public static AgreementDTOBuilder createAgreement() {
        return new AgreementDTOBuilder();
    }

    public AgreementDTO build() {
        AgreementDTO agreementDTO = new AgreementDTO();
        agreementDTO.setAgreementDateFrom(agreementDateFrom);
        agreementDTO.setAgreementDateTo(agreementDateTo);
        agreementDTO.setCountry(country);
        agreementDTO.setSelectedRisks(selectedRisks);
        agreementDTO.setPersons(persons);
        agreementDTO.setAgreementPremium(agreementPremium);
        return agreementDTO;
    }

    public AgreementDTOBuilder withDateFrom(LocalDate agreementDateFrom) {
        this.agreementDateFrom = agreementDateFrom;
        return this;
    }

    public AgreementDTOBuilder withDateTo(LocalDate agreementDateTo) {
        this.agreementDateTo = agreementDateTo;
        return this;
    }

    public AgreementDTOBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public AgreementDTOBuilder withPremium(BigDecimal agreementPremium) {
        this.agreementPremium = agreementPremium;
        return this;
    }

    public AgreementDTOBuilder withSelectedRisk(String selectedRisk) {
        this.selectedRisks.add(selectedRisk);
        return this;
    }

    public AgreementDTOBuilder withSelectedRisk(List<String> selectedRisks) {
        this.selectedRisks.addAll(selectedRisks);
        return this;
    }

    public AgreementDTOBuilder withPerson(PersonDTO person) {
        this.persons.add(person);
        return this;
    }

    public AgreementDTOBuilder withPersons(List<PersonDTO> persons) {
        this.persons = persons;
        return this;
    }

}
