package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelAgreementUuidValidatorImpl implements TravelAgreementUuidValidator{

    private final ErrorValidationFactory errorsHandler;

    private final AgreementRepository agreementRepository;

    public List<ValidationErrorDTO> validate(UUID uuid) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        if (uuid == null) {
            errors.add(errorsHandler.processing("ERROR_CODE_17"));
        } else {
            if (agreementRepository.findByUuid(uuid).isEmpty()) {
                Placeholder placeholder = new Placeholder("AGREEMENT_UUID", uuid.toString());
                errors.add(errorsHandler.processing("ERROR_CODE_18", List.of(placeholder)));
            }
        }
        return errors;
    }
}
