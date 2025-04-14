package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCostValidator implements ValidationPersonOptional {
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO agreementDTO, PersonDTO request) {
        return hasTravelCancellationRisk(agreementDTO) && isTravelCostNull(request)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_19"))
                : Optional.empty();
    }

    private boolean isTravelCostNull(PersonDTO request){
        return request.getTravelCost() == null;
    }

    private boolean hasTravelCancellationRisk(AgreementDTO agreementDTO){
        return agreementDTO.getSelectedRisks() != null &&
        agreementDTO.getSelectedRisks().contains("TRAVEL_CANCELLATION");

    }




}
