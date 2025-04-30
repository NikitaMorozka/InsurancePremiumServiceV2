package org.javaguru.blacklist.core.services.delete;

import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.command.BlackListedPersonDeleteCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonDeleteCoreResult;
import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.core.domain.BlackListedPersonEntity;
import org.javaguru.blacklist.core.repositories.BlackListedPersonEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlackListedPersonDeleteCoreServiceImpl implements BlackListedPersonDeleteCoreService {
    private final BlackListedPersonEntityRepository repository;

    @Override
    public BlackListedPersonDeleteCoreResult delete(BlackListedPersonDeleteCoreCommand command) {
        repository.deleteById(command.getId());

        List<BlackListedPersonDTO> dtos = repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();

        return new BlackListedPersonDeleteCoreResult(dtos);
    }

    private BlackListedPersonDTO toDTO(BlackListedPersonEntity blackListedPerson) {
        BlackListedPersonDTO blackListedPersonDTO = new BlackListedPersonDTO();
        blackListedPersonDTO.setId(blackListedPerson.getId());
        blackListedPersonDTO.setPersonFirstName(blackListedPerson.getFirstName());
        blackListedPersonDTO.setPersonLastName(blackListedPerson.getLastName());
        blackListedPersonDTO.setPersonCode(blackListedPerson.getPersonCode());
        blackListedPersonDTO.setBlackListed(true);
        return blackListedPersonDTO;
    }
}
