package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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


    List<ValidationErrorDTO> validate(List<PersonDTO> persons) {
        return persons.stream()
                .map(this::collectPersonErrors)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<ValidationErrorDTO> collectPersonErrors(PersonDTO personDTO) {
        List<ValidationErrorDTO> personOptionalErrors = validatePersonOptionals(personDTO);
        List<ValidationErrorDTO> personListErrors = validatePersonLists(personDTO);
        return mergeValidationErrors(personOptionalErrors, personListErrors);
    }

    private List<ValidationErrorDTO> validatePersonOptionals(PersonDTO request) {
        return validationPersonOptionals.stream()
                .map(validation -> validation.validationOptional(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<ValidationErrorDTO> validatePersonLists(PersonDTO request) {
        return validationPersonLists.stream()
                .map(validation -> validation.validationList(request))
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
