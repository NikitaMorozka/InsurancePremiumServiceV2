package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class EmptyRisksValidator implements ValidationAgreementOptional {
    private final ErrorValidationFactory errorsHandler;


    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO request) {
        List<String> risks = request.getSelectedRisks();

        boolean hasEmptyRisk = risks.stream().anyMatch(risk -> risk == null || risk.trim().isEmpty());

        return (risks.isEmpty() || hasEmptyRisk)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_7"))
                : Optional.empty();
    }
}

