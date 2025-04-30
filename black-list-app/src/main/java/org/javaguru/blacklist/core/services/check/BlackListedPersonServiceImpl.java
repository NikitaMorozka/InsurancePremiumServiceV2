package org.javaguru.blacklist.core.services.check;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreResult;
import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.core.api.dto.ValidationErrorDTO;
import org.javaguru.blacklist.core.repositories.BlackListedPersonEntityRepository;
import org.javaguru.blacklist.core.validations.BlackListedPersonValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class BlackListedPersonServiceImpl implements BlackListedPersonService {

    private final BlackListedPersonValidator personValidator;
    private final BlackListedPersonEntityRepository repository;

    @Override
    public BlackListedPersonCoreResult check(BlackListedPersonCoreCommand command) {
        List<ValidationErrorDTO> errors = personValidator.validate(command.getPersonDTO());
        if (errors.isEmpty()) {
            BlackListedPersonDTO personDTO = command.getPersonDTO();
            boolean isBlacklisted = repository.findBy(
                    personDTO.getPersonFirstName(),
                    personDTO.getPersonLastName(),
                    personDTO.getPersonCode()
            ).isPresent();
            personDTO.setBlackListed(isBlacklisted);
            return new BlackListedPersonCoreResult(personDTO);
        } else {
            return new BlackListedPersonCoreResult(errors);
        }
    }

}
