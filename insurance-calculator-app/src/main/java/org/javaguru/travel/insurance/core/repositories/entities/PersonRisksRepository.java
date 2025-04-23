package org.javaguru.travel.insurance.core.repositories.entities;

import org.javaguru.travel.insurance.core.domain.entities.AgreementPerson;
import org.javaguru.travel.insurance.core.domain.entities.PersonRisks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRisksRepository extends JpaRepository<PersonRisks, Long> {
    List<PersonRisks> findByAgreementPerson(AgreementPerson agreementPerson);
}
