package org.javaguru.travel.insurance.dto.v2;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.Risks;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DtoV2Converter { // написать тесты

    private final TravelCalculatePremiumService service;

    public TravelCalculatePremiumResponseV2 processRequest(TravelCalculatePremiumRequestV2 request) {
        return buildResponse(service.calculatePremium(buildCoreCommand(request)));
    }

    public TravelCalculatePremiumCoreCommand buildCoreCommand(TravelCalculatePremiumRequestV2 request) {
        AgreementDTO agreement = buildAgreement(request);
        return new TravelCalculatePremiumCoreCommand(agreement);
    }

    public TravelCalculatePremiumResponseV2 buildResponse(TravelCalculatePremiumCoreResult coreResult) {
        return coreResult.hasErrors()
                ? buildResponseWithErrors(coreResult.getErrors())
                : buildSuccessfulResponse(coreResult);
    }

    private TravelCalculatePremiumResponseV2 buildResponseWithErrors(List<ValidationErrorDTO> coreErrors) {
        List<ValidationError> errors = transformValidationErrorsToV1(coreErrors);
        return new TravelCalculatePremiumResponseV2(errors);
    }

    private List<ValidationError> transformValidationErrorsToV1(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(error -> new ValidationError(error.errorCode(), error.description())).toList();
    }


    private TravelCalculatePremiumResponseV2 buildSuccessfulResponse(TravelCalculatePremiumCoreResult coreResult) {
        AgreementDTO agreement = coreResult.getAgreement();

        TravelCalculatePremiumResponseV2 response = new TravelCalculatePremiumResponseV2();
        response.setAgreementDateFrom(agreement.getAgreementDateFrom());
        response.setAgreementDateTo(agreement.getAgreementDateTo());
        response.setCountry(agreement.getCountry());
        response.setMedicalRiskLimitLevel(agreement.getMedicalRiskLimitLevel());
        response.setAgreementPremium(agreement.getAgreementPremium());
        response.setPersons(agreement.getPersons().stream().map(this::buildPersonFromResponse).toList());
        return response;
    }

    private PersonResponse buildPersonFromResponse(PersonDTO personDTO) {
        PersonResponse person = new PersonResponse();

        person.setPersonFirstName(personDTO.getPersonFirstName());
        person.setPersonLastName(personDTO.getPersonLastName());
        person.setDateOfBirthDate(personDTO.getPersonBirthDate());

        person.setPersonAgreementPremium(personDTO.getRisks().stream()
                .map(RiskDTO::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );


        person.setRisks(personDTO.getRisks().stream()
                .map(riskDTO -> new Risks(riskDTO.getRiskIc(), riskDTO.getPremium())).toList());

        return person;
    }




    private AgreementDTO buildAgreement(TravelCalculatePremiumRequestV2 request) {
        AgreementDTO agreement = new AgreementDTO();
        agreement.setAgreementDateFrom(request.getAgreementDateFrom());
        agreement.setAgreementDateTo(request.getAgreementDateTo());
        agreement.setCountry(request.getCountry());
        agreement.setMedicalRiskLimitLevel(request.getMedicalRiskLimitLevel());
        agreement.setSelectedRisks(request.getSelectedRisks());
        agreement.setPersons(buildPersons(request));
        return agreement;
    }
    private List<PersonDTO> buildPersons(TravelCalculatePremiumRequestV2 request) {
        List<PersonDTO> personsDTO = new ArrayList<>();
        for(PersonRequest persons: request.getPersons()){
            PersonDTO person = new PersonDTO();
            person.setPersonFirstName(persons.getPersonFirstName());
            person.setPersonLastName(persons.getPersonLastName());
            person.setPersonBirthDate(persons.getDateOfBirth());
            personsDTO.add(person);
        }
        return personsDTO;
    }

}
