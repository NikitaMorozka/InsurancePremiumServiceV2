package org.javaguru.travel.insurance.core.services.loader;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.domain.entities.Agreement;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPerson;
import org.javaguru.travel.insurance.core.domain.entities.Person;
import org.javaguru.travel.insurance.core.domain.entities.SelectedRisks;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementPersonsRepository;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementRepository;
import org.javaguru.travel.insurance.core.repositories.entities.PersonRisksRepository;
import org.javaguru.travel.insurance.core.repositories.entities.SelectRisksRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementLoader {

    private final AgreementRepository agreementRepository;
    private final SelectRisksRepository selectRisksRepository;
    private final AgreementPersonsRepository agreementPersonsRepository;
    private final PersonRisksRepository personRisksRepository;

    AgreementDTO load(UUID uuid) {
        AgreementDTO agreementDTO = new AgreementDTO();
        Agreement agreement = agreementRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("UUID" + uuid + "not found")
        );
        loadAgreements(agreementDTO, agreement);
        loadPersons(agreementDTO,agreement);
        loadSelectRisks(agreementDTO,agreement);
        return agreementDTO;
    }

    private void loadPersons(AgreementDTO agreementDTO, Agreement agreement) {
        List<AgreementPerson> personsRepositories = agreementPersonsRepository.findByAgreement(agreement);
        List<PersonDTO> personDTOS = personsRepositories.stream().map(agreementPerson -> {
            Person person = agreementPerson.getPerson();
            PersonDTO personDTO = new PersonDTO();

            personDTO.setPersonFirstName(person.getFirstName());
            personDTO.setPersonLastName(person.getLastName());
            personDTO.setPersonBirthDate(person.getBirthDate());
            personDTO.setPersonCode(person.getPersonCode());
            personDTO.setMedicalRiskLimitLevel(agreementPerson.getMedicalRiskLimitLevel());
            personDTO.setTravelCost(agreementPerson.getTravelCost());
            personDTO.setRisks(personRisksRepository.findByAgreementPerson(agreementPerson)
                    .stream().map(
                            personRisk -> {
                                RiskDTO riskDTO = new RiskDTO();
                                riskDTO.setRiskIc(personRisk.getRiskIc());
                                riskDTO.setPremium(personRisk.getPremium());
                                return riskDTO;
                            }).toList()
            );
            return personDTO;
        }).toList();
        agreementDTO.setPersons(personDTOS);
    }

    private void loadSelectRisks(AgreementDTO agreementDTO, Agreement agreement){
        agreementDTO.setSelectedRisks(selectRisksRepository
                        .findByAgreement(agreement)
                        .stream()
                        .map(SelectedRisks::getRiskIc)
                        .toList());
    }

    private void loadAgreements(AgreementDTO agreementDTO, Agreement agreement) {
        agreementDTO.setAgreementDateFrom(agreement.getDateFrom());
        agreementDTO.setAgreementDateTo(agreement.getDateTo());
        agreementDTO.setAgreementPremium(agreement.getPremium());
        agreementDTO.setCountry(agreement.getCountry());
        agreementDTO.setUuid(agreement.getUuid());
    }


}
