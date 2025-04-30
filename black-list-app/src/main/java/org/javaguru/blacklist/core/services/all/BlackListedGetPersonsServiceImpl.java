package org.javaguru.blacklist.core.services.all;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.command.BlackListedPersonsListCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonsListCoreResult;
import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.core.domain.BlackListedPersonEntity;
import org.javaguru.blacklist.core.repositories.BlackListedPersonEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class BlackListedGetPersonsServiceImpl implements BlackListedGetPersonsService {

    private final BlackListedPersonEntityRepository repository;

    //добить валидацию на пустой список персонов
    @Override
    public BlackListedPersonsListCoreResult getAllBlackListedPersons(BlackListedPersonsListCoreCommand command) {
        List<BlackListedPersonEntity> entities = repository.findAllBlackListedPersons();

        List<BlackListedPersonDTO> personDTOs = entities.stream()
                .map(this::loadBlackListedPersonDTO)
                .collect(Collectors.toList());

        return new BlackListedPersonsListCoreResult(personDTOs);
    }


    private BlackListedPersonDTO loadBlackListedPersonDTO(BlackListedPersonEntity blackListedPerson) {
        BlackListedPersonDTO blackListedPersonDTO = new BlackListedPersonDTO();
        blackListedPersonDTO.setId(blackListedPerson.getId());
        blackListedPersonDTO.setPersonFirstName(blackListedPerson.getFirstName());
        blackListedPersonDTO.setPersonLastName(blackListedPerson.getLastName());
        blackListedPersonDTO.setPersonCode(blackListedPerson.getPersonCode());
        blackListedPersonDTO.setBlackListed(true);
        return blackListedPersonDTO;
    }

}
