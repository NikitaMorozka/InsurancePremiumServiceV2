package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class MedicalRiskLimitLevelValidation implements ValidationAgreementOptional {

    @Value("${medical.risk.limit.level.enabled}")
    private Boolean medicalRiskLimitLevelEnabled;
    private final ClassifierValueRepository classifierValueRepository;
    private final ErrorValidationFactory errorsHandler;


    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO request) {
        return (medicalRiskLimitLevelEnabled
                && containsTravelMedical(request)
                && !existInDatabase(request.getPersons()))
                ? Optional.of(errorsHandler.processing("ERROR_CODE_13"))
                : Optional.empty();
    }

    private boolean containsTravelMedical(AgreementDTO request) {
        return request.getSelectedRisks() != null
                && request.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }


    private boolean existInDatabase(List<PersonDTO> personDTO) {
        return personDTO.stream()
                .allMatch(person -> classifierValueRepository
                        .findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", person.getMedicalRiskLimitLevel())
                        .isPresent());

    }
}
