package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MedicalRiskLimitLevelInDBValidation implements ValidationPersonOptional {

    private final ClassifierValueRepository classifierValueRepository;
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO agreementDTO, PersonDTO request) {
        return (isMedicalRiskLimitLevelNotBlank(request))
                && !existInDatabase(request.getMedicalRiskLimitLevel())
                && containsTravelMedical(agreementDTO)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_14"))
                : Optional.empty();
    }

    private boolean containsTravelMedical(AgreementDTO agreementDTO) {
        return agreementDTO.getSelectedRisks() != null
                && agreementDTO.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }

    private boolean isMedicalRiskLimitLevelNotBlank(PersonDTO request) {
        return request.getMedicalRiskLimitLevel() == null
                || request.getMedicalRiskLimitLevel().isBlank();
    }

    private boolean existInDatabase(String medicalRiscLimitLevelIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", medicalRiscLimitLevelIc).isPresent();
    }
}

