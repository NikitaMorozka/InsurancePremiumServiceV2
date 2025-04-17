package org.javaguru.travel.insurance.core.validations.person;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PersonFieldAnnotationValidation implements ValidationPersonList {

    private final ErrorValidationFactory errorsHandler;

    @Override
    public List<ValidationErrorDTO> validationList(AgreementDTO agreement, PersonDTO person) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        if (violations.isEmpty()) {
            return List.of();
        } else {
            List<ValidationErrorDTO> errors = new ArrayList<>();
            for (ConstraintViolation<PersonDTO> violation : violations) {
                String fieldName = violation.getPropertyPath().toString();
                String fieldMessage = violation.getMessage();
                Placeholder placeholder1 = new Placeholder("FIELD_NAME", fieldName);
                Placeholder placeholder2 = new Placeholder("FIELD_MESSAGE", fieldMessage);
                errors.add(errorsHandler.processing("ERROR_CODE_24", List.of(placeholder1, placeholder2)));
            }
            return errors;
        }
    }

}
