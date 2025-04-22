package org.javaguru.travel.insurance.dto.GetAgreementDTO;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.Risks;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.v2.PersonResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetAgreementDtoConverter { // написать тесты

    public TravelGetAgreementCoreCommand processRequest(UUID uuid) {
        return new TravelGetAgreementCoreCommand(uuid);
    }

    public GetAgreementResponse buildResponse(TravelGetAgreementCoreResult coreResult) {
        return coreResult.hasErrors()
                ? buildResponseWithErrors(coreResult.getErrors())
                : buildSuccessfulResponse(coreResult);
    }

    private GetAgreementResponse buildResponseWithErrors(List<ValidationErrorDTO> coreErrors) {
        List<ValidationError> errors = transformValidationErrors(coreErrors);
        return new GetAgreementResponse(errors);
    }

    private List<ValidationError> transformValidationErrors(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(error -> new ValidationError(error.errorCode(), error.description()))
                .toList();
    }

    private GetAgreementResponse buildSuccessfulResponse(TravelGetAgreementCoreResult coreResult) {
        AgreementDTO agreement = coreResult.getAgreement();

        GetAgreementResponse response = new GetAgreementResponse();

        response.setAgreementDateFrom(agreement.getAgreementDateFrom());
        response.setAgreementDateTo(agreement.getAgreementDateTo());
        response.setUuid(agreement.getUuid());
        response.setCountry(agreement.getCountry());
        response.setAgreementPremium(agreement.getAgreementPremium());

        response.setPersons(agreement
                .getPersons()
                .stream()
                .map(this::buildPersonFromResponse)
                .toList());

        return response;
    }

    private PersonResponse buildPersonFromResponse(PersonDTO personDTO) {
        PersonResponse person = new PersonResponse();
        person.setPersonFirstName(personDTO.getPersonFirstName());
        person.setPersonLastName(personDTO.getPersonLastName());
        person.setPersonCode(personDTO.getPersonCode());
        person.setDateOfBirthDate(personDTO.getPersonBirthDate());
        person.setMedicalRiskLimitLevel(personDTO.getMedicalRiskLimitLevel());
        person.setTravelCost(personDTO.getTravelCost());

        person.setPersonAgreementPremium(personDTO
                .getRisks()
                .stream()
                .map(RiskDTO::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        person.setRisks(personDTO
                .getRisks()
                .stream()
                .map(riskDTO -> new Risks(riskDTO.getRiskIc(), riskDTO.getPremium()))
                .toList());

        return person;
    }


}
