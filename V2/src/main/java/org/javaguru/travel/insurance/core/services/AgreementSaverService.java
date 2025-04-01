package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.entities.*;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementPersonsRepository;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementRepository;
import org.javaguru.travel.insurance.core.repositories.entities.PersonRisksRepository;
import org.javaguru.travel.insurance.core.repositories.entities.SelectRisksRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AgreementSaverService {

    private final AgreementRepository agreementRepository;
    private final PersonSaverService personSaverService;
    private final SelectRisksRepository selectRisksRepository;
    private final AgreementPersonsRepository agreementPersonsRepository;

    private final PersonRisksRepository personRisksRepository;

    Agreement save(AgreementDTO agreementDTO) {
        Agreement agreement = createAgreement(agreementDTO);
        saveRisks(agreementDTO, agreement);
        savePersonPolies(agreementDTO, agreement);
        return agreement;
    }

    private Agreement createAgreement(AgreementDTO agreementDTO) {
        Agreement agreement = new Agreement();
        agreement.setDateFrom(agreementDTO.getAgreementDateFrom());
        agreement.setDateTo(agreementDTO.getAgreementDateTo());
        agreement.setCountry(agreementDTO.getCountry());
        agreement.setPremium(agreementDTO.getAgreementPremium());
        return agreementRepository.save(agreement);
    }

    private void saveRisks(AgreementDTO agreementDTO, Agreement agreement) {
        agreementDTO.getSelectedRisks().forEach(risk -> {
            SelectedRisks selectedRisks = new SelectedRisks();
            selectedRisks.setAgreement(agreement);
            selectedRisks.setRiskIc(risk);
            selectRisksRepository.save(selectedRisks);
        });
    }

    private void savePersonPolies(AgreementDTO agreementDTO, Agreement agreement) {
        agreementDTO.getPersons().forEach(personDTO -> {
            Person person = personSaverService.createAndSavePerson(personDTO);
            AgreementPerson agreementPerson = saveAgreement(person, agreement, personDTO);
            savePersonRisk(personDTO, agreementPerson);
        });
    }
    private AgreementPerson saveAgreement(Person person, Agreement agreement,PersonDTO personDTO){
        AgreementPerson agreementPerson = new AgreementPerson();
        agreementPerson.setAgreement(agreement);
        agreementPerson.setPerson(person);
        agreementPerson.setMedicalRiskLimitLevel(personDTO.getMedicalRiskLimitLevel());
        return agreementPersonsRepository.save(agreementPerson);
    }

    private void savePersonRisk(PersonDTO personDTO, AgreementPerson agreement) {
        personDTO.getRisks().forEach(riskDTO -> {
            PersonRisks personRisks = new PersonRisks();
            personRisks.setAgreementPerson(agreement);
            personRisks.setRiskIc(riskDTO.getRiskIc());
            personRisks.setPremium(riskDTO.getPremium());
            personRisksRepository.save(personRisks);
        });
    }
}
