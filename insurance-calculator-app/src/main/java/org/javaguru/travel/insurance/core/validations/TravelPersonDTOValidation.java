package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.person.ValidationPersonList;
import org.javaguru.travel.insurance.core.validations.person.ValidationPersonOptional;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPersonDTOValidation {

    private final List<ValidationPersonOptional> validationPersonOptionals;
    private final List<ValidationPersonList> validationPersonLists;


    List<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        return agreementDTO.getPersons().stream()
                .map(person -> collectPersonErrors(agreementDTO, person))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }


    private List<ValidationErrorDTO> collectPersonErrors(AgreementDTO agreementDTO, PersonDTO personDTO) {
        List<ValidationErrorDTO> personOptionalErrors = validatePersonOptionals(agreementDTO,personDTO);
        List<ValidationErrorDTO> personListErrors = validatePersonLists(agreementDTO, personDTO);
        return mergeValidationErrors(personOptionalErrors, personListErrors);
    }

    private List<ValidationErrorDTO> validatePersonOptionals(AgreementDTO agreementDTO, PersonDTO personDTO) {
        return validationPersonOptionals.stream()
                .map(validation -> validation.validationOptional(agreementDTO, personDTO))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<ValidationErrorDTO> validatePersonLists(AgreementDTO agreementDTO, PersonDTO personDTO) {
        return validationPersonLists.stream()
                .map(validation -> validation.validationList(agreementDTO,personDTO))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .toList();
    }

    private List<ValidationErrorDTO> mergeValidationErrors(List<ValidationErrorDTO>... errorsLists) {
        return Arrays.stream(errorsLists)
                .flatMap(Collection::stream)
                .toList();
    }
}
